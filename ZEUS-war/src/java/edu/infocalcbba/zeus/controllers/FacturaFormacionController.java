/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Cuota;
import edu.infocalcbba.argos.entities.Estudiante;
import edu.infocalcbba.argos.entities.Inscrito;
import edu.infocalcbba.argos.facades.CuotaFacade;
import edu.infocalcbba.argos.facades.EstudianteFacade;
import edu.infocalcbba.argos.facades.InscritoFacade;
import edu.infocalcbba.log_argos.entities.LogCuota;
import edu.infocalcbba.log_argos.facades.LogCuotaFacade;
import edu.infocalcbba.mikrotik.facades.MikrotikFacade;
import edu.infocalcbba.phoenix.facades.PagoFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Cliente;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Dosificacion;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.PagoFormacion;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tincho
 */
@Named("FacturaFormacionController")
@SessionScoped
public class FacturaFormacionController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaFormacionController.class);

    private static final String CONDICION_ADEUDADA = "ADEUDADA";
    private static final int CANTIDAD_DETALLE = 1;
    private static final String CONDICION_VALIDA = "VALIDA";

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    EstudianteFacade estudianteFacade;
    @EJB
    InscritoFacade inscritoFacade;
    @EJB
    CuotaFacade cuotaFacade;
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
    @EJB
    LogCuotaFacade logCuotaFacade;
    @EJB
    MikrotikFacade mikrotikFacade;
    @EJB
    PagoFacade pagoFacade;
    @Inject
    LoginController loginController;

    private Estudiante seleccionEstudiante;
    private Inscrito seleccionInscrito;
    private List<Detalle> nuevosDetalles = new ArrayList();
    private List<PagoFormacion> listaPagosFormacion;
    private List<PagoFormacion> seleccionPagosFormacion;
    private Detalle seleccionDetalle;
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes = new ArrayList();
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Integer efectivo = 0;
    private Integer cambio = 0;

    private Integer montoAbonar;

    public List<Estudiante> completarEstudiante(String consulta) {
        List<Estudiante> estudiantes = estudianteFacade.findAll();
        List<Estudiante> estudiantesFiltrados = new ArrayList();

        for (Estudiante e : estudiantes) {
            if (e.toString().startsWith(consulta.toUpperCase())) {
                estudiantesFiltrados.add(e);
            }
        }

        return estudiantesFiltrados;
    }

    public List<Inscrito> listaInscritos() {
        List<Inscrito> l = new ArrayList();

        if (seleccionEstudiante != null) {
            l = inscritoFacade.inscripcionesPorEstudiante(loginController.getSeleccionRol().getId_rol(), seleccionEstudiante.getId_persona());
        }

        return l;
    }

    public List<Cuota> listaCuotas() {
        List<Cuota> l = new ArrayList();
        if (seleccionInscrito != null) {
            l = cuotaFacade.listaCuota(seleccionInscrito.getId_inscrito(), CONDICION_ADEUDADA);
        }
        return l;
    }

    public List<PagoFormacion> listaPagosFormacion() {
        return pagoFormacionFacade.listaPagosFormacion();
    }

    public void añadirCuotas() throws IOException {
        nuevosDetalles.clear();

        List<Cuota> cuotas = listaCuotas();
        for (Cuota cuota : cuotas) {
            if (montoAbonar > 0) {
                if (montoAbonar >= cuota.getAdeudado()) {
                    Detalle detalle = new Detalle();
                    detalle.setCantidad(CANTIDAD_DETALLE);
                    detalle.setCodigo(cuota.getCodigo());
                    detalle.setConcepto(cuota.getConcepto());
                    detalle.setUnitario(cuota.getAdeudado().doubleValue());
                    Double total = detalle.getCantidad() * detalle.getUnitario();
                    detalle.setTotal(total);
                    detalle.setCuota(cuota);
                    nuevosDetalles.add(detalle);

                    montoAbonar -= cuota.getAdeudado();
                } else {
                    Detalle detalle = new Detalle();
                    detalle.setCantidad(CANTIDAD_DETALLE);
                    detalle.setCodigo(cuota.getCodigo());
                    detalle.setConcepto(cuota.getConcepto());
                    detalle.setUnitario(montoAbonar.doubleValue());
                    Double total = detalle.getCantidad() * detalle.getUnitario();
                    detalle.setTotal(total);
                    detalle.setCuota(cuota);
                    nuevosDetalles.add(detalle);

                    montoAbonar -= cuota.getAdeudado();
                }
            }
        }

        this.actualizarCambio();
        this.toFacturaFormacion();
    }

    public void añadirPagos() throws IOException {
        nuevosDetalles.clear();

        for (PagoFormacion pagoformacion : seleccionPagosFormacion) {

            Detalle detalle = new Detalle();
            detalle.setCantidad(CANTIDAD_DETALLE);
            detalle.setCodigo(pagoformacion.getCodigo());
            detalle.setConcepto(pagoformacion.getConcepto());
            detalle.setUnitario(pagoformacion.getMonto().doubleValue());
            Double total = detalle.getCantidad() * detalle.getUnitario();
            detalle.setTotal(total);
            detalle.setEstudiante(seleccionEstudiante);
            detalle.setCarrera(seleccionInscrito.getCarrera());

            if (!nuevosDetalles.contains(detalle)) {
                nuevosDetalles.add(detalle);
            }
        }

        this.actualizarCambio();
        this.toFacturaFormacion();
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

    public int montoCuotas() {
        int monto = 0;

        List<Cuota> cuotas = listaCuotas();
        for (Cuota cuota : cuotas) {
            monto += cuota.getAdeudado();
        }

        return monto;
    }

    public void actualizar() {
        nuevosDetalles = new ArrayList();

        if (seleccionEstudiante != null) {
            listaClientes = clienteFacade.listaClientes(seleccionEstudiante.getId_persona());
        }
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            listaClientes.add(nuevoCliente);
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();
            this.toFacturaFormacion();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void editarPagoFormacion(CellEditEvent event) {
        PagoFormacion pagoFormacion = listaPagosFormacion.get(event.getRowIndex());

        if (pagoFormacion.getPago() != null) {
            if (pagoFormacion.getPago() < 0) {
                pagoFormacion.setPago(pagoFormacion.getMonto());
                this.mensajeDeError("El pago no puede ser negativo.");
            }
        } else {
            pagoFormacion.setPago(pagoFormacion.getMonto());
            this.mensajeDeError("El pago no puede ser nulo.");
        }
    }

    public void limpiar() {
        seleccionEstudiante = null;
        seleccionInscrito = null;
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

    public boolean facturaFormacion(List<Detalle> detalles) {
        boolean res = true;

        for (Detalle detalle : detalles) {
            if (detalle.getCuota() == null) {
                res = false;
                break;
            }
        }
        return res;
    }

    public void nuevaFactura() throws IOException {
        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (seleccionEstudiante != null) {
                    long adeudadoFormacionValores = pagoFacade.adeudadoFormacionValores(seleccionEstudiante.getId_persona());
                    log.info("Adeudado Formación de Valores: " + adeudadoFormacionValores);
                    if (adeudadoFormacionValores == 0) {
                        if (seleccionInscrito != null) {
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
                                            for (Detalle detalle : nuevosDetalles) {
                                                if (detalle.getCuota() != null) {
                                                    logCuotaFacade.create(new LogCuota(Reloj.getDate(), loginController.getUsr().toString(), recuperarIP(), "EDICION CUOTA POR FACTURA FORMACION", detalle.getCuota().getId_cuota(), detalle.getCuota().getCodigo(), detalle.getCuota().getConcepto(), detalle.getCuota().getNumero(), detalle.getCuota().getMonto(), detalle.getCuota().getPagado(), detalle.getCuota().getAdeudado(), detalle.getCuota().getCondicion()));
                                                }
                                            }

                                            if (facturaFormacion(nuevosDetalles)) {
                                                long pagadoColegiatura = cuotaFacade.pagadoColegiatura(seleccionInscrito.getId_inscrito());
                                                mikrotikFacade.editLimitBytesOutHotspotUser(seleccionInscrito.getEstudiante().getDni(), pagadoColegiatura);

                                                int id_factura = nuevaFactura.getId_factura();
                                                int id_inscrito = seleccionInscrito.getId_inscrito();
                                                limpiar();

                                                this.insertarParametro("id_factura", id_factura);
                                                this.insertarParametro("id_inscrito", id_inscrito);

                                                this.ejecutar("PF('dlg0').show();");
                                            } else {
                                                int id_factura = nuevaFactura.getId_factura();
                                                int id_carrera = seleccionInscrito.getCarrera().getId_carrera();
                                                int id_estudiante = seleccionEstudiante.getId_persona();
                                                limpiar();

                                                this.insertarParametro("id_factura", id_factura);
                                                this.insertarParametro("id_carrera", id_carrera);
                                                this.insertarParametro("id_estudiante", id_estudiante);

                                                this.ejecutar("PF('dlg1').show();");
                                            }

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
                            this.mensajeDeError("Ninguna carrera seleccionada.");
                        }
                    } else {
                        this.mensajeDeError("Deuda curso \"Formación de Valores\".");
                    }
                } else {
                    this.mensajeDeError("Ningun estudiante seleccionado.");
                }
            } else {
                this.mensajeDeError("No existe dosificacion activa.");
            }
        } else {
            this.mensajeDeError("No existe turno abierto.");
        }
    }

    public void toFacturaFormacion() throws IOException {
        this.redireccionarViewId("/facturacion/facturaformacion/FacturaFormacion.xhtml");
    }

    public void toSeleccionarCuotas() throws IOException {
        if (seleccionEstudiante != null) {
            if (seleccionInscrito != null) {
                montoAbonar = 0;

                this.redireccionarViewId("/facturacion/facturaformacion/seleccionarCuotas.xhtml");
            } else {
                this.mensajeDeError("Ninguna carrera seleccionada.");
            }
        } else {
            this.mensajeDeError("Ningun estudiante seleccionado.");
        }
    }

    public void toSeleccionarPagos() throws IOException {
        if (seleccionEstudiante != null) {
            if (seleccionInscrito != null) {
                seleccionPagosFormacion = new ArrayList();

                this.redireccionarViewId("/facturacion/facturaformacion/seleccionarPagos.xhtml");
            } else {
                this.mensajeDeError("Ninguna carrera seleccionada.");
            }
        } else {
            this.mensajeDeError("Ningun estudiante seleccionado.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaformacion/nuevoCliente.xhtml");
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
     * @return the seleccionInscrito
     */
    public Inscrito getSeleccionInscrito() {
        return seleccionInscrito;
    }

    /**
     * @param seleccionInscrito the seleccionInscrito to set
     */
    public void setSeleccionInscrito(Inscrito seleccionInscrito) {
        this.seleccionInscrito = seleccionInscrito;
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
     * @return the seleccionEstudiante
     */
    public Estudiante getSeleccionEstudiante() {
        return seleccionEstudiante;
    }

    /**
     * @param seleccionEstudiante the seleccionEstudiante to set
     */
    public void setSeleccionEstudiante(Estudiante seleccionEstudiante) {
        this.seleccionEstudiante = seleccionEstudiante;
    }

    /**
     * @return the seleccionPagosFormacion
     */
    public List<PagoFormacion> getSeleccionPagosFormacion() {
        return seleccionPagosFormacion;
    }

    /**
     * @param seleccionPagosFormacion the seleccionPagosFormacion to set
     */
    public void setSeleccionPagosFormacion(List<PagoFormacion> seleccionPagosFormacion) {
        this.seleccionPagosFormacion = seleccionPagosFormacion;
    }

    /**
     * @return the listaPagosFormacion
     */
    public List<PagoFormacion> getListaPagosFormacion() {
        return listaPagosFormacion;
    }

    /**
     * @param listaPagosFormacion the listaPagosFormacion to set
     */
    public void setListaPagosFormacion(List<PagoFormacion> listaPagosFormacion) {
        this.listaPagosFormacion = listaPagosFormacion;
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
     * @return the montoAbonar
     */
    public int getMontoAbonar() {
        return montoAbonar;
    }

    /**
     * @param montoAbonar the montoAbonar to set
     */
    public void setMontoAbonar(int montoAbonar) {
        this.montoAbonar = montoAbonar;
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

}
