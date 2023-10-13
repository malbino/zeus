/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.facades.CarreraFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Cliente;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Dosificacion;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.PagoExterno;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.ClienteFacade;
import edu.infocalcbba.zeus.facades.DosificacionFacade;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facades.PagoExternoFacade;
import edu.infocalcbba.zeus.facades.TurnoCajaFacade;
import edu.infocalcbba.zeus.facades.negocio.NuevaFacturaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tincho
 */
@Named("FacturaExternaController")
@SessionScoped
public class FacturaExternaController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaExternaController.class);

    private static final int CANTIDAD_DETALLE = 1;
    private static final String CONDICION_VALIDA = "VALIDA";

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    PagoExternoFacade pagoExternoFacade;
    @EJB
    ClienteFacade clienteFacade;
    @EJB
    DosificacionFacade dosificacionFacade;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    NuevaFacturaFacade nuevaFacturaFacade;
    @EJB
    CarreraFacade carreraFacade;
    @Inject
    LoginController loginController;

    private List<Detalle> nuevosDetalles = new ArrayList();
    private List<PagoExterno> seleccionPagosExternos;
    private Detalle seleccionDetalle;
    private Cliente seleccionCliente;
    private String nitCi;
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Double efectivo = 0.0;
    private Double cambio = 0.0;

    public List<PagoExterno> listaPagosExternos() {
        return pagoExternoFacade.listaPagosExternos();
    }

    public void añadirPagos() throws IOException {
        for (PagoExterno pagoexterno : seleccionPagosExternos) {

            Detalle detalle = new Detalle();
            detalle.setCantidad(CANTIDAD_DETALLE);
            detalle.setCodigo(pagoexterno.getCodigo());
            detalle.setConcepto(pagoexterno.getConcepto());
            detalle.setUnitario(pagoexterno.getMonto());
            Double total = detalle.getCantidad() * detalle.getUnitario();
            detalle.setTotal(total);

            if (!nuevosDetalles.contains(detalle)) {
                nuevosDetalles.add(detalle);
            }
        }

        this.actualizarCambio();
        this.toFacturaExterna();
    }

    public void onRowEdit(RowEditEvent event) {
        Detalle detalle = (Detalle) event.getObject();
        detalle.setTotal(detalle.getCantidad() * detalle.getUnitario());
    }

    public void eliminarDetalle() {
        nuevosDetalles.remove(seleccionDetalle);
    }

    public void limpiarDetalle() {
        nuevosDetalles.clear();
    }

    public Double monto() {
        Double monto = 0.0;

        for (Detalle detalle : nuevosDetalles) {
            monto += detalle.getTotal();
        }

        return monto;
    }

    public List<Cliente> completarCliente(String consulta) {
        List<Cliente> clientes = clienteFacade.findAll();
        List<Cliente> clientesFiltrados = new ArrayList();

        for (Cliente c : clientes) {
            if (c.toString().startsWith(consulta.toUpperCase())) {
                clientesFiltrados.add(c);
            }
        }

        return clientesFiltrados;
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            nitCi = String.valueOf(nuevoCliente.getNit_ci());
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();

            this.toFacturaExterna();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void limpiarCliente() {
        this.seleccionCliente = null;
    }

    public void limpiar() {
        nuevosDetalles = new ArrayList();
        nitCi = null;
        seleccionCliente = null;
        nuevaFactura = null;

        efectivo = 0.0;
        cambio = 0.0;
    }

    public void actualizarCambio() {
        cambio = efectivo - monto();
    }

    public void nuevaFacturaExterna() throws IOException {
        this.limpiarParametros();

        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (nuevosDetalles.size() > 0) {
                    if (seleccionCliente != null) {
                        if (efectivo >= monto()) {
                            nuevaFactura = new Factura();
                            nuevaFactura.setFecha(Reloj.getDate());
                            nuevaFactura.setMonto(monto());
                            nuevaFactura.setCondicion(CONDICION_VALIDA);
                            nuevaFactura.setDosificacion(dosificacion);
                            nuevaFactura.setCliente(seleccionCliente);
                            nuevaFactura.setTurnocaja(turnoCaja);

                            nuevaFactura.setEfectivo(efectivo);
                            nuevaFactura.setCambio(cambio);

                            if (nuevaFacturaFacade.nuevaFactura(nuevaFactura, nuevosDetalles)) {
                                int id_factura = nuevaFactura.getId_factura();
                                limpiar();

                                this.insertarParametro("id_factura", id_factura);
                                //comprobante formacion
                                this.insertarParametro("est", null);
                                this.insertarParametro("car", null);
                                //comprobante capacitacion
                                this.insertarParametro("car_cap", null);
                                this.insertarParametro("gru_cap", null);
                                this.insertarParametro("est_cap", null);
                                this.insertarParametro("cel_cap", null);

                                this.ejecutar("PF('dlg').show();");
                            } else {
                                this.mensajeDeError("No se pudo registrar la factura.");
                            }
                        } else {
                            this.mensajeDeError("El efectivo debe ser mayor o igual que el monto.");
                        }
                    } else {
                        this.mensajeDeError("Ningun cliente seleccionado.");
                    }
                } else {
                    this.mensajeDeError("No existen conceptos de pago.");
                }
            } else {
                this.mensajeDeError("No existe dosificacion activa.");
            }
        } else {
            this.mensajeDeError("No existe turno abierto.");
        }
    }

    public void toFacturaExterna() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexterna/FacturaExterna.xhtml");
    }

    public void limpiarParametros() {
        this.eliminarParametro("id_factura");
        this.eliminarParametro("id_usuario");
    }

    public void toSeleccionarPagos() throws IOException {
        seleccionPagosExternos = new ArrayList();

        this.redireccionarViewId("/facturacion/facturaexterna/seleccionarPagos.xhtml");
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexterna/nuevoCliente.xhtml");
    }

    public void toEditarCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexterna/editarCliente.xhtml");
    }

    /**
     * @return the nuevaFactura
     */
    public Factura getNuevaFactura() {
        return nuevaFactura;
    }

    /**
     * @param nuevaFactura the nuevaFactura to set
     */
    public void setNuevaFactura(Factura nuevaFactura) {
        this.nuevaFactura = nuevaFactura;
    }

    /**
     * @return the seleccionCliente
     */
    public Cliente getSeleccionCliente() {
        return seleccionCliente;
    }

    /**
     * @param seleccionCliente the seleccionCliente to set
     */
    public void setSeleccionCliente(Cliente seleccionCliente) {
        this.seleccionCliente = seleccionCliente;
    }

    /**
     * @return the nuevosDetalles
     */
    public List<Detalle> getNuevosDetalles() {
        return nuevosDetalles;
    }

    /**
     * @param nuevosDetalles the nuevosDetalles to set
     */
    public void setNuevosDetalles(List<Detalle> nuevosDetalles) {
        this.nuevosDetalles = nuevosDetalles;
    }

    /**
     * @return the nuevoCliente
     */
    public Cliente getNuevoCliente() {
        return nuevoCliente;
    }

    /**
     * @param nuevoCliente the nuevoCliente to set
     */
    public void setNuevoCliente(Cliente nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }

    /**
     * @return the seleccionDetalle
     */
    public Detalle getSeleccionDetalle() {
        return seleccionDetalle;
    }

    /**
     * @param seleccionDetalle the seleccionDetalle to set
     */
    public void setSeleccionDetalle(Detalle seleccionDetalle) {
        this.seleccionDetalle = seleccionDetalle;
    }

    /**
     * @return the seleccionPagosExternos
     */
    public List<PagoExterno> getSeleccionPagosExternos() {
        return seleccionPagosExternos;
    }

    /**
     * @param seleccionPagosExternos the seleccionPagosExternos to set
     */
    public void setSeleccionPagosExternos(List<PagoExterno> seleccionPagosExternos) {
        this.seleccionPagosExternos = seleccionPagosExternos;
    }

    /**
     * @return the nitCi
     */
    public String getNitCi() {
        return nitCi;
    }

    /**
     * @param nitCi the nitCi to set
     */
    public void setNitCi(String nitCi) {
        this.nitCi = nitCi;
    }

    /**
     * @return the efectivo
     */
    public Double getEfectivo() {
        return efectivo;
    }

    /**
     * @param efectivo the efectivo to set
     */
    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    /**
     * @return the cambio
     */
    public Double getCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }
}
