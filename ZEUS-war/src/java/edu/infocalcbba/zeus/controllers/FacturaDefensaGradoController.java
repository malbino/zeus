/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.DefensaGrado;
import edu.infocalcbba.argos.entities.Derecho;
import edu.infocalcbba.argos.entities.Egresado;
import edu.infocalcbba.argos.facades.DefensaGradoFacade;
import edu.infocalcbba.argos.facades.DerechoFacade;
import edu.infocalcbba.argos.facades.EgresadoFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Cliente;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Dosificacion;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.ClienteFacade;
import edu.infocalcbba.zeus.facades.DetalleFacade;
import edu.infocalcbba.zeus.facades.DosificacionFacade;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facades.PagoFormacionFacade;
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
@Named("FacturaDefensaGradoController")
@SessionScoped
public class FacturaDefensaGradoController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaDefensaGradoController.class);

    private static final String CONDICION_ADEUDADA = "ADEUDADA";
    private static final int CANTIDAD_DETALLE = 1;
    private static final String CONDICION_VALIDA = "VALIDA";

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    EgresadoFacade egresadoFacade;
    @EJB
    DefensaGradoFacade defensaGradoFacade;
    @EJB
    DerechoFacade derechoFacade;
    @EJB
    PagoFormacionFacade pagoFormacionFacade;
    @EJB
    ClienteFacade clienteFacade;
    @EJB
    DosificacionFacade dosificacionFacade;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    ParametroFacade parametroFacade;
    @EJB
    NuevaFacturaFacade nuevaFacturaFacade;
    @EJB
    DetalleFacade detalleFacade;
    @Inject
    LoginController loginController;

    private Egresado seleccionEgresado;
    private DefensaGrado seleccionDefensaGrado;
    private List<Detalle> nuevosDetalles = new ArrayList();
    private List<Derecho> seleccionDerecho;
    private Detalle seleccionDetalle;
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes = new ArrayList();
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Integer efectivo = 0;
    private Integer cambio = 0;

    public List<Egresado> completarEgresado(String consulta) {
        List<Egresado> egresados = egresadoFacade.findAll();
        List<Egresado> egresadosFiltrados = new ArrayList();

        for (Egresado e : egresados) {
            if (e.toString_Estudiante().startsWith(consulta.toUpperCase())) {
                egresadosFiltrados.add(e);
            }
        }

        return egresadosFiltrados;
    }

    public List<DefensaGrado> listaDefensaGrado() {
        List<DefensaGrado> l = new ArrayList();

        if (seleccionEgresado != null) {
            l = defensaGradoFacade.listaDefensasGrado(seleccionEgresado.getId_egresado());
        }

        return l;
    }

    public List<Derecho> listaDerechos() {
        List<Derecho> l = new ArrayList();
        if (seleccionDefensaGrado != null) {
            l = derechoFacade.listaDerecho(seleccionDefensaGrado.getId_defensagrado(), CONDICION_ADEUDADA);
        }
        return l;
    }

    public void añadirDerechos() throws IOException {
        for (Derecho derecho : seleccionDerecho) {
            if (derecho.getAdeudado() != 0) {
                Detalle detalle = new Detalle();
                detalle.setCantidad(CANTIDAD_DETALLE);
                detalle.setCodigo(derecho.getCodigo());
                detalle.setConcepto(derecho.getConcepto());
                detalle.setUnitario(derecho.getAdeudado().doubleValue());
                Double total = detalle.getCantidad() * detalle.getUnitario();
                detalle.setTotal(total);
                detalle.setDerecho(derecho);

                if (!nuevosDetalles.contains(detalle)) {
                    nuevosDetalles.add(detalle);
                }
            }
        }

        this.actualizarCambio();
        this.toFacturaDefensaGrado();
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

    public void actualizar() {
        nuevosDetalles = new ArrayList();

        listaClientes = new ArrayList();
        if (seleccionEgresado.getEstudiante() != null) {
            listaClientes = clienteFacade.listaClientes(seleccionEgresado.getEstudiante().getId_persona());
        }
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            listaClientes.add(nuevoCliente);
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();
            this.toFacturaDefensaGrado();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void limpiar() {
        seleccionEgresado = null;
        seleccionDefensaGrado = null;
        nuevosDetalles = new ArrayList();
        listaClientes = new ArrayList();
        seleccionCliente = null;
        nuevaFactura = null;

        efectivo = 0;
        cambio = 0;
    }

    public void actualizarCambio() {
        cambio = efectivo - monto().intValue();
    }

    public void nuevaFactura() throws IOException {

        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (seleccionEgresado != null) {
                    if (seleccionDefensaGrado != null) {
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

                                    nuevaFactura.setEfectivo(efectivo.doubleValue());
                                    nuevaFactura.setCambio(cambio.doubleValue());

                                    if (nuevaFacturaFacade.nuevaFactura(nuevaFactura, nuevosDetalles)) {
                                        int id_factura = nuevaFactura.getId_factura();
                                        int id_defensagrado = seleccionDefensaGrado.getId_defensagrado();
                                        limpiar();

                                        this.insertarParametro("id_factura", id_factura);
                                        this.insertarParametro("id_defensagrado", id_defensagrado);

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
                        this.mensajeDeError("Ninguna defensa seleccionada.");
                    }
                } else {
                    this.mensajeDeError("Ningun egresado seleccionado.");
                }
            } else {
                this.mensajeDeError("No existe dosificacion activa.");
            }
        } else {
            this.mensajeDeError("No existe turno abierto.");
        }
    }

    public void toFacturaDefensaGrado() throws IOException {
        this.redireccionarViewId("/facturacion/facturadefensagrado/FacturaDefensaGrado.xhtml");
    }

    public void toSeleccionarDerechos() throws IOException {
        if (seleccionEgresado != null) {
            if (seleccionDefensaGrado != null) {
                seleccionDerecho = new ArrayList();

                this.redireccionarViewId("/facturacion/facturadefensagrado/seleccionarDerechos.xhtml");
            } else {
                this.mensajeDeError("Ninguna defensa seleccionada.");
            }
        } else {
            this.mensajeDeError("Ningun egresado seleccionado.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturadefensagrado/nuevoCliente.xhtml");
    }

    /**
     * @return the seleccionEgresado
     */
    public Egresado getSeleccionEgresado() {
        return seleccionEgresado;
    }

    /**
     * @param seleccionEgresado the seleccionEgresado to set
     */
    public void setSeleccionEgresado(Egresado seleccionEgresado) {
        this.seleccionEgresado = seleccionEgresado;
    }

    /**
     * @return the seleccionDefensaGrado
     */
    public DefensaGrado getSeleccionDefensaGrado() {
        return seleccionDefensaGrado;
    }

    /**
     * @param seleccionDefensaGrado the seleccionDefensaGrado to set
     */
    public void setSeleccionDefensaGrado(DefensaGrado seleccionDefensaGrado) {
        this.seleccionDefensaGrado = seleccionDefensaGrado;
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
     * @return the seleccionDerecho
     */
    public List<Derecho> getSeleccionDerecho() {
        return seleccionDerecho;
    }

    /**
     * @param seleccionDerecho the seleccionDerecho to set
     */
    public void setSeleccionDerecho(List<Derecho> seleccionDerecho) {
        this.seleccionDerecho = seleccionDerecho;
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
     * @return the listaClientes
     */
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientes the listaClientes to set
     */
    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
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
     * @return the efectivo
     */
    public int getEfectivo() {
        return efectivo;
    }

    /**
     * @param efectivo the efectivo to set
     */
    public void setEfectivo(int efectivo) {
        this.efectivo = efectivo;
    }

    /**
     * @return the cambio
     */
    public int getCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(int cambio) {
        this.cambio = cambio;
    }
}
