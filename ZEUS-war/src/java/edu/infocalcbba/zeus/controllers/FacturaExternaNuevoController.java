/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.ASC;
import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.facades.ASCFacade;
import edu.infocalcbba.argos.facades.CarreraFacade;
import edu.infocalcbba.argos.facades.EstudianteFacade;
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
@Named("FacturaExternaNuevoController")
@SessionScoped
public class FacturaExternaNuevoController extends Controller implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FacturaExternaNuevoController.class);

    private static final String CONDICION_VALIDA = "VALIDA";

    private static final int NIVEL_ESTUDIANTE_NUEVO = 1;
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
    ParametroFacade parametroFacade;

    @Inject
    LoginController loginController;

    private String estudiante = null;
    private Carrera seleccionCarrera;
    private List<Detalle> listaDetalles = new ArrayList();
    private List<Detalle> nuevosDetalles = new ArrayList();
    private Cliente seleccionCliente;
    private List<Cliente> listaClientes;
    private Cliente nuevoCliente = new Cliente();
    private Factura nuevaFactura;

    private Integer efectivo = 0;
    private Integer cambio = 0;

    public List<Carrera> listaCarreras() {
        return carreraFacade.listaCarreras();
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

    public void actualizarDetalles(Carrera carrera) {

        if (carrera != null) {
            //oferta de materias
            List<ASC> oferta = ascFacade.listaASCs_CarreraNivel(seleccionCarrera.getId_carrera(), NIVEL_ESTUDIANTE_NUEVO);

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
        nuevosDetalles = new ArrayList();
    }

    public void crearCliente() throws IOException {
        if (clienteFacade.create(nuevoCliente)) {
            seleccionCliente = nuevoCliente;

            nuevoCliente = new Cliente();

            this.toFacturaExternaNuevo();
        } else {
            this.mensajeDeError("No se pudo crear el cliente.");
        }
    }

    public void limpiarCliente() {
        this.seleccionCliente = null;
    }

    public void limpiar() {
        estudiante = null;
        seleccionCarrera = null;
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
        this.toFacturaExternaNuevo();
    }

    public void nuevaFacturaExternaNuevo() throws IOException {
        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja != null) {
            Dosificacion dosificacion = dosificacionFacade.dosificacionActiva(turnoCaja.getCaja().getCampus().getId_campus());
            if (dosificacion != null) {
                if (!estudiante.isEmpty()) {
                    if (seleccionCarrera != null) {
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
                                        this.insertarParametro("est", estudiante.toUpperCase());
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
                        this.mensajeDeError("Ninguna carrera seleccionada.");
                    }
                } else {
                    this.mensajeDeError("Escriba el nombre del estudiante.");
                }
            } else {
                this.mensajeDeError("No existe dosificacion activa.");
            }
        } else {
            this.mensajeDeError("No existe turno abierto.");
        }
    }

    public void toFacturaExternaNuevo() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternanuevo/FacturaExternaNuevo.xhtml");
    }

    public void toSeleccionarDetalles() throws IOException {
        if (seleccionCarrera != null) {
            actualizarDetalles(seleccionCarrera);

            this.redireccionarViewId("/facturacion/facturaexternanuevo/seleccionarDetalles.xhtml");
        } else {
            this.mensajeDeError("Ninguna carrera seleccionada.");
        }
    }

    public void toNuevoCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternanuevo/nuevoCliente.xhtml");
    }

    public void toEditarCliente() throws IOException {
        this.redireccionarViewId("/facturacion/facturaexternanuevo/editarCliente.xhtml");
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
}
