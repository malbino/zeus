/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Estudiante;
import edu.infocalcbba.argos.entities.Inscrito;
import edu.infocalcbba.argos.entities.Nota;
import edu.infocalcbba.argos.facades.EstudianteFacade;
import edu.infocalcbba.argos.facades.InscritoFacade;
import edu.infocalcbba.argos.facades.NotaFacade;
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
@Named("FacturaRecuperacionController")
@SessionScoped
public class FacturaRecuperacionController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaRecuperacionController.class);

    private static final String CONDICION_ADEUDADA = "ADEUDADA";
    private static final String CONDICION_VALIDA = "VALIDA";

    private static final int CANTIDAD_DETALLE = 1;
    private static final String CODIGO_DETALLE = "PAF-016";
    private static final String CONCEPTO_DETALLE = "PRUEBA DE RECUPERACION";
    private static final Double MONTO_DETALLE = 35.0;

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    EstudianteFacade estudianteFacade;
    @EJB
    InscritoFacade inscritoFacade;
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
    NotaFacade notaFacade;
    @Inject
    LoginController loginController;

    private Estudiante seleccionEstudiante;
    private Inscrito seleccionInscrito;
    private Nota seleccionNota;
    private List<Detalle> nuevosDetalles = new ArrayList();
    private Detalle seleccionDetalle;
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes = new ArrayList();
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Integer efectivo = 0;
    private Integer cambio = 0;

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

    public List<Nota> listaNotasRecuperatorio() {
        List<Nota> l = new ArrayList();
        if (seleccionInscrito != null) {
            long numeroMateriaReprobadas = notaFacade.numeroMateriasReprobadas(seleccionInscrito.getId_inscrito());
            if (numeroMateriaReprobadas <= seleccionInscrito.getGestionacademica().getEvaluacion().getMax_re()) {
                l = notaFacade.listaNotasRecuperacion(seleccionInscrito.getId_inscrito());
            }
        }
        return l;
    }

    public void añadirMateria() throws IOException {
        nuevosDetalles.clear();

        Detalle detalle = new Detalle();
        detalle.setCantidad(CANTIDAD_DETALLE);
        detalle.setCodigo(CODIGO_DETALLE);
        detalle.setConcepto(CONCEPTO_DETALLE + " - " + seleccionNota.getAsc().getNombre().toUpperCase());
        detalle.setUnitario(MONTO_DETALLE);
        Double total = detalle.getCantidad() * detalle.getUnitario();
        detalle.setTotal(total);
        detalle.setEstudiante(seleccionEstudiante);
        detalle.setCarrera(seleccionInscrito.getCarrera());
        detalle.setNota(seleccionNota);

        if (!nuevosDetalles.contains(detalle)) {
            nuevosDetalles.add(detalle);
        }

        this.actualizarCambio();
        this.toFacturaRecuperacion();
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

        if (seleccionEstudiante != null) {
            listaClientes = clienteFacade.listaClientes(seleccionEstudiante.getId_persona());
        }
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            listaClientes.add(nuevoCliente);
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();
            this.toFacturaRecuperacion();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
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

    public void nuevaFactura() throws IOException {
        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (seleccionEstudiante != null) {
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
                                        int id_factura = nuevaFactura.getId_factura();
                                        int id_carrera = seleccionInscrito.getCarrera().getId_carrera();
                                        int id_estudiante = seleccionEstudiante.getId_persona();
                                        limpiar();

                                        this.insertarParametro("id_factura", id_factura);
                                        this.insertarParametro("id_carrera", id_carrera);
                                        this.insertarParametro("id_estudiante", id_estudiante);

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
                        this.mensajeDeError("Ninguna carrera seleccionada.");
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

    public void toFacturaRecuperacion() throws IOException {
        this.redireccionarViewId("/facturacion/facturarecuperacion/FacturaRecuperacion.xhtml");
    }

    public void toSeleccionarMateria() throws IOException {
        if (seleccionEstudiante != null) {
            if (seleccionInscrito != null) {
                seleccionNota = null;

                this.redireccionarViewId("/facturacion/facturarecuperacion/seleccionarMateria.xhtml");
            } else {
                this.mensajeDeError("Ninguna carrera seleccionada.");
            }
        } else {
            this.mensajeDeError("Ningun estudiante seleccionado.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturarecuperacion/nuevoCliente.xhtml");
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
     * @return the seleccionNota
     */
    public Nota getSeleccionNota() {
        return seleccionNota;
    }

    /**
     * @param seleccionNota the seleccionNota to set
     */
    public void setSeleccionNota(Nota seleccionNota) {
        this.seleccionNota = seleccionNota;
    }

    /**
     * @return the MONTO_DETALLE
     */
    public Double getMONTO_DETALLE() {
        return MONTO_DETALLE;
    }

}
