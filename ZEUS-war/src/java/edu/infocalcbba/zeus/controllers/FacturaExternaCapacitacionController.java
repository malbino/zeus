/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.facades.CarreraFacade;
import edu.infocalcbba.argos.facades.EstudianteFacade;
import edu.infocalcbba.phoenix.entities.GestionPh;
import edu.infocalcbba.phoenix.entities.GrupoPh;
import edu.infocalcbba.phoenix.entities.Pago;
import edu.infocalcbba.phoenix.facades.GestionPhFacade;
import edu.infocalcbba.phoenix.facades.GrupoPhFacade;
import edu.infocalcbba.phoenix.facades.InscritoPhFacade;
import edu.infocalcbba.phoenix.facades.PagoFacade;
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
@Named("FacturaExternaCapacitacionController")
@SessionScoped
public class FacturaExternaCapacitacionController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaExternaCapacitacionController.class);

    private static final String CONDICION_ADEUDADA = "ADEUDADA";
    private static final int CANTIDAD_DETALLE = 1;
    private static final String CONDICION_VALIDA = "VALIDA";

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    EstudianteFacade estudianteFacade;
    @EJB
    InscritoPhFacade inscritoPhFacade;
    @EJB
    PagoFacade pagoFacade;
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
    @EJB
    GestionPhFacade gestionFacade;
    @EJB
    CarreraFacade carreraFacade;
    @EJB
    GrupoPhFacade grupoPhFacade;
    @Inject
    LoginController loginController;

    private List<Detalle> nuevosDetalles = new ArrayList();
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes = new ArrayList();
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Integer efectivo = 0;
    private Integer cambio = 0;

    private GestionPh seleccionGestion;
    private Carrera seleccionCarrera;
    private GrupoPh seleccionGrupo;

    private String estudiante;
    private Integer celular;

    public List<GestionPh> listaGestiones() {
        return gestionFacade.listaGestiones();
    }

    public List<Carrera> listaCarreras() {
        return carreraFacade.listaCarreras();
    }

    public List<GrupoPh> listaGrupos() {
        List<GrupoPh> l = new ArrayList();

        if (seleccionGestion != null && seleccionCarrera != null) {
            l = grupoPhFacade.listaGrupos_GestionCarrera(seleccionGestion.getId_gestion(), seleccionCarrera.getId_carrera());
        }

        return l;
    }

    public List<Pago> listaPagos() {
        List<Pago> l = new ArrayList();
        if (seleccionGrupo != null) {
            Pago pago = new Pago();
            pago.setCodigo("P1");
            pago.setConcepto("Pago 1");
            pago.setNumero(1);
            pago.setMonto(seleccionGrupo.getCosto());
            pago.setPagado(0);
            pago.setAdeudado(seleccionGrupo.getCosto());
            pago.setCondicion(CONDICION_ADEUDADA);

            l.add(pago);
        }
        return l;
    }

    public void añadirPagos() throws IOException {
        nuevosDetalles.clear();

        List<Pago> pagos = listaPagos();
        for (Pago pago : pagos) {
            Detalle detalle = new Detalle();
            detalle.setCantidad(CANTIDAD_DETALLE);
            detalle.setCodigo(pago.getCodigo());
            detalle.setConcepto(pago.getConcepto());
            detalle.setUnitario(pago.getAdeudado().doubleValue());
            Double total = detalle.getCantidad() * detalle.getUnitario();
            detalle.setTotal(total);

            nuevosDetalles.add(detalle);
        }

        this.actualizarCambio();
        this.toFacturaExternaCapacitacion();
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

    public void onRowEdit(RowEditEvent event) {
        Detalle detalle = (Detalle) event.getObject();
        detalle.setTotal(detalle.getCantidad() * detalle.getUnitario());
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

    public int montoPagos() {
        int monto = 0;

        List<Pago> pagos = listaPagos();
        for (Pago pago : pagos) {
            monto += pago.getAdeudado();
        }

        return monto;
    }

    public void actualizar() {
        nuevosDetalles = new ArrayList();
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();
            this.toFacturaExternaCapacitacion();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void limpiar() {
        seleccionGestion = null;
        seleccionCarrera = null;
        seleccionGrupo = null;
        nuevosDetalles = new ArrayList();
        listaClientes = new ArrayList();
        seleccionCliente = null;
        nuevaFactura = null;

        efectivo = 0;
        cambio = 0;

        estudiante = null;
        celular = null;
    }

    public void actualizarCambio() {
        cambio = efectivo - monto().intValue();
    }

    public void nuevaFactura() throws IOException {

        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (seleccionGestion != null) {
                    if (seleccionCarrera != null) {
                        if (seleccionGrupo != null) {
                            if (!estudiante.isEmpty()) {
                                if (celular != null) {
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
                                                nuevaFactura.setValorada(Boolean.FALSE);

                                                if (nuevaFacturaFacade.nuevaFactura(nuevaFactura, nuevosDetalles)) {
                                                    int id_factura = nuevaFactura.getId_factura();

                                                    this.insertarParametro("id_factura", id_factura);
                                                    //comprobante formacion
                                                    this.insertarParametro("est", null);
                                                    this.insertarParametro("car", null);
                                                    //comprobante capacitacion
                                                    this.insertarParametro("car_cap", seleccionCarrera.toString());
                                                    this.insertarParametro("gru_cap", seleccionGrupo.toString());
                                                    this.insertarParametro("est_cap", estudiante.toUpperCase());
                                                    this.insertarParametro("cel_cap", celular);

                                                    this.ejecutar("PF('dlg').show();");

                                                    limpiar();
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
                                    this.mensajeDeError("Escriba el celular del estudiante.");
                                }
                            } else {
                                this.mensajeDeError("Escriba el nombre del estudiante.");
                            }
                        } else {
                            this.mensajeDeError("Ningun grupo seleccionado.");
                        }
                    } else {
                        this.mensajeDeError("Ninguna carrera seleccionada.");
                    }
                } else {
                    this.mensajeDeError("Ninguna gestión seleccionada.");
                }
            } else {
                this.mensajeDeError("No existe dosificacion activa.");
            }
        } else {
            this.mensajeDeError("No existe turno abierto.");
        }
    }

    public void toFacturaExternaCapacitacion() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternacapacitacion/FacturaExternaCapacitacion.xhtml");
    }

    public void toSeleccionarPagos() throws IOException {
        if (seleccionGestion != null) {
            if (seleccionCarrera != null) {
                if (seleccionGrupo != null) {
                    this.redireccionarViewId("/facturacion/facturaexternacapacitacion/seleccionarPagos.xhtml");
                } else {
                    this.mensajeDeError("Ningun grupo seleccionado.");
                }
            } else {
                this.mensajeDeError("Ninguna carrera seleccionada.");
            }
        } else {
            this.mensajeDeError("Ninguna gestión seleccionada.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternacapacitacion/nuevoCliente.xhtml");
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

    /**
     * @return the seleccionGestion
     */
    public GestionPh getSeleccionGestion() {
        return seleccionGestion;
    }

    /**
     * @param seleccionGestion the seleccionGestion to set
     */
    public void setSeleccionGestion(GestionPh seleccionGestion) {
        this.seleccionGestion = seleccionGestion;
    }

    /**
     * @return the seleccionCarrera
     */
    public Carrera getSeleccionCarrera() {
        return seleccionCarrera;
    }

    /**
     * @param seleccionCarrera the seleccionCarrera to set
     */
    public void setSeleccionCarrera(Carrera seleccionCarrera) {
        this.seleccionCarrera = seleccionCarrera;
    }

    /**
     * @return the seleccionGrupo
     */
    public GrupoPh getSeleccionGrupo() {
        return seleccionGrupo;
    }

    /**
     * @param seleccionGrupo the seleccionGrupo to set
     */
    public void setSeleccionGrupo(GrupoPh seleccionGrupo) {
        this.seleccionGrupo = seleccionGrupo;
    }

    /**
     * @return the estudiante
     */
    public String getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the celular
     */
    public Integer getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(Integer celular) {
        this.celular = celular;
    }

}
