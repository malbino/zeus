/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.ASC;
import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.entities.Estudiante;
import edu.infocalcbba.argos.entities.GestionAcademica;
import edu.infocalcbba.argos.facades.ASCFacade;
import edu.infocalcbba.argos.facades.CarreraFacade;
import edu.infocalcbba.argos.facades.EstudianteFacade;
import edu.infocalcbba.argos.facades.GestionAcademicaFacade;
import edu.infocalcbba.argos.facades.NotaFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Cliente;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Dosificacion;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.ClienteFacade;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tincho
 */
@Named("FacturaExternaRegularController")
@SessionScoped
public class FacturaExternaRegularController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaExternaRegularController.class);

    private static final String CONDICION_VALIDA = "VALIDA";

    private static final int CANTIDAD_DETALLE = 1;
    private static final String[] CODIGOS_CUOTAS = {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"};
    private static final String[] CONCEPTOS_CUOTAS = {"Cuota 1", "Cuota 2", "Cuota 3", "Cuota 4", "Cuota 5", "Cuota 6", "Cuota 7", "Cuota 8", "Cuota 9", "Cuota 10"};

    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    EstudianteFacade estudianteFacade;
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
    @EJB
    ASCFacade ascFacade;
    @EJB
    NotaFacade notaFacade;
    @EJB
    GestionAcademicaFacade gestionAcademicaFacade;
    @EJB
    ParametroFacade parametroFacade;

    @Inject
    LoginController loginController;

    private Estudiante seleccionEstudiante;
    private Carrera seleccionCarrera;
    private GestionAcademica seleccionGestionAcademica;
    private List<Detalle> listaDetalles = new ArrayList();
    private List<Detalle> nuevosDetalles = new ArrayList();
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes;
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

    public List<GestionAcademica> listaGestionesAcademicas() {
        List<GestionAcademica> gestionesAcademicas = new ArrayList();
        if (seleccionCarrera != null) {
            gestionesAcademicas = gestionAcademicaFacade.listaGestionesAcademicas(loginController.getSeleccionRol().getId_rol(), seleccionCarrera.getRegimen().getId_regimen());
        }
        return gestionesAcademicas;
    }

    public void actualizarDetalles(Estudiante estudiante, Carrera carrera, GestionAcademica gestionAcademica) {

        if (estudiante != null && carrera != null && gestionAcademica != null) {
            //oferta de materias
            int nivel = ascFacade.nivelAsignaturasCurriculares(carrera.getId_carrera(), estudiante.getId_persona());
            if (nivel == 0) {
                nivel = ascFacade.nivelAsignaturasNoCurriculares(carrera.getId_carrera(), estudiante.getId_persona());
            }

            long cantidad_nivel = ascFacade.cantidadASCsPorCarreraNivel(carrera.getId_carrera(), nivel);
            long aprobadas_nivel = notaFacade.cantidadNotasAprobadas_EstudianteNivel(estudiante.getId_persona(), carrera.getId_carrera(), nivel);
            long reprobadas_nivel = cantidad_nivel - aprobadas_nivel;

            List<ASC> oferta = new ArrayList();
            if (reprobadas_nivel == 0) {
                oferta = ascFacade.ofertaMaterias(carrera.getId_carrera(), estudiante.getId_persona(), nivel);
            } else if (reprobadas_nivel <= gestionAcademica.getEvaluacion().getMax_re()) {
                oferta = ascFacade.ofertaMaterias(carrera.getId_carrera(), estudiante.getId_persona(), (nivel + 2));
            } else if (reprobadas_nivel > gestionAcademica.getEvaluacion().getMax_re()) {
                oferta = ascFacade.ofertaMaterias(carrera.getId_carrera(), estudiante.getId_persona(), nivel);
            }

            //colegiatura estudiante
            double monto_colegiatura = 0;
            int costo_credito = Integer.valueOf(parametroFacade.find(ParametroFacade.COSTO_CREDITO).getValor());
            for (ASC asc : oferta) {
                monto_colegiatura += asc.getNro_creditos() * costo_credito;
            }

            //lista detalles
            listaDetalles = new ArrayList();
            if (monto_colegiatura > 0) {
                int numero_cuotas = carrera.getRegimen().getNumero_cuotas();
                for (int i = 0; i < carrera.getRegimen().getNumero_cuotas(); i++) {
                    if (i == 0) {
                        double monto_cuota_sinredondear = monto_colegiatura * 0.44;
                        Integer monto_cuota_redondeado = (int) Redondeo.redondear_UP(monto_cuota_sinredondear, 0);

                        Detalle detalle = new Detalle();
                        detalle.setCodigo(CODIGOS_CUOTAS[i]);
                        detalle.setConcepto(CONCEPTOS_CUOTAS[i]);
                        detalle.setCantidad(CANTIDAD_DETALLE);
                        detalle.setUnitario(monto_cuota_redondeado.doubleValue());
                        detalle.setTotal(monto_cuota_redondeado.doubleValue());
                        listaDetalles.add(detalle);

                        monto_colegiatura -= monto_cuota_redondeado;
                        numero_cuotas--;
                    } else {
                        double monto_cuota_sinredondear = monto_colegiatura / numero_cuotas;
                        Integer monto_cuota_redondeado = (int) Redondeo.redondear_UP(monto_cuota_sinredondear, 0);

                        Detalle detalle = new Detalle();
                        detalle.setCodigo(CODIGOS_CUOTAS[i]);
                        detalle.setConcepto(CONCEPTOS_CUOTAS[i]);
                        detalle.setCantidad(CANTIDAD_DETALLE);
                        detalle.setUnitario(monto_cuota_redondeado.doubleValue());
                        detalle.setTotal(monto_cuota_redondeado.doubleValue());
                        listaDetalles.add(detalle);

                        monto_colegiatura -= monto_cuota_redondeado;
                        numero_cuotas--;
                    }
                }
            }
        }
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

    public int montoDetalles() {
        int monto = 0;

        for (Detalle detalle : listaDetalles) {
            monto += detalle.getTotal();
        }

        return monto;
    }

    public void actualizar() {
        seleccionCarrera = null;
        seleccionGestionAcademica = null;
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
            this.toFacturaExternaRegular();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void limpiarCliente() {
        this.seleccionCliente = null;
    }

    public void limpiar() {
        seleccionEstudiante = null;
        seleccionCarrera = null;
        seleccionGestionAcademica = null;
        listaDetalles = new ArrayList();
        nuevosDetalles = new ArrayList();
        listaClientes = new ArrayList();
        nuevoCliente = new Cliente();
        seleccionCliente = null;
        nuevaFactura = null;

        efectivo = 0;
        cambio = 0;
    }

    public void actualizarCambio() {
        cambio = efectivo - monto().intValue();
    }

    public void añadirDetalles() throws IOException {
        this.actualizarCambio();
        this.toFacturaExternaRegular();
    }

    public void nuevaFacturaExternaRegular() throws IOException {
        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (seleccionEstudiante != null) {
                    if (seleccionCarrera != null) {
                        if (seleccionGestionAcademica != null) {
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
                                            this.insertarParametro("est", seleccionEstudiante.toString());
                                            this.insertarParametro("car", seleccionCarrera.toString());
                                            //comprobante capacitacion
                                            this.insertarParametro("car_cap", null);
                                            this.insertarParametro("gru_cap", null);
                                            this.insertarParametro("est_cap", null);
                                            this.insertarParametro("cel_cap", null);

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
                            this.mensajeDeError("Ninguna gestión academica seleccionada.");
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

    public void toFacturaExternaRegular() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternaregular/FacturaExternaRegular.xhtml");
    }

    public void toSeleccionarDetalles() throws IOException {
        if (seleccionEstudiante != null) {
            if (seleccionCarrera != null) {
                if (seleccionGestionAcademica != null) {
                    actualizarDetalles(seleccionEstudiante, seleccionCarrera, seleccionGestionAcademica);

                    this.redireccionarViewId("/facturacion/facturaexternaregular/seleccionarDetalles.xhtml");
                } else {
                    this.mensajeDeError("Ninguna gestión academica seleccionada.");
                }
            } else {
                this.mensajeDeError("Ninguna carrera seleccionada.");
            }
        } else {
            this.mensajeDeError("Ningun estudiante seleccionado.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternaregular/nuevoCliente.xhtml");
    }

    public void toEditarCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternaregular/editarCliente.xhtml");
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
     * @return the seleccionGestionAcademica
     */
    public GestionAcademica getSeleccionGestionAcademica() {
        return seleccionGestionAcademica;
    }

    /**
     * @param seleccionGestionAcademica the seleccionGestionAcademica to set
     */
    public void setSeleccionGestionAcademica(GestionAcademica seleccionGestionAcademica) {
        this.seleccionGestionAcademica = seleccionGestionAcademica;
    }

    /**
     * @return the listaDetalles
     */
    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    /**
     * @param listaDetalles the listaDetalles to set
     */
    public void setListaDetalles(List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
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
}
