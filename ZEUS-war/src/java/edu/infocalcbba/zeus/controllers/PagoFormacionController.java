/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.GestionAcademica;
import edu.infocalcbba.argos.facades.GestionAcademicaFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.zeus.entities.PagoFormacion;
import edu.infocalcbba.zeus.facades.PagoFormacionFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("PagoFormacionController")
@SessionScoped
public class PagoFormacionController extends Controller implements Serializable {

    @EJB
    GestionAcademicaFacade gestionAcademicaFacade;
    @EJB
    PagoFormacionFacade pagoFormacionFacade;
    
    @Inject
    LoginController loginController;

    private PagoFormacion nuevoPagoFormacion = new PagoFormacion();
    private PagoFormacion seleccionPagoFormacion;

    public List<GestionAcademica> listaGestionesAcademicas() {
        return gestionAcademicaFacade.listaGestionesAcademicas(loginController.getSeleccionRol().getId_rol());
    }

    public List<PagoFormacion> listaPagoFormacion() {
        return pagoFormacionFacade.listaPagosFormacion();
    }

    public void crearPagoFormacion() throws IOException {
        if (pagoFormacionFacade.buscarPagoFormacion(nuevoPagoFormacion.getCodigo()) == null) {
            if (pagoFormacionFacade.create(nuevoPagoFormacion)) {
                nuevoPagoFormacion = new PagoFormacion();

                this.toPagosFormacion();
            } else {
                this.mensajeDeError("No se pudo crear el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void editarPagoFormacion() throws IOException {
        if (pagoFormacionFacade.buscarPagoFormacion(seleccionPagoFormacion.getCodigo(), seleccionPagoFormacion.getId_pagoformacion()) == null) {
            if (pagoFormacionFacade.edit(seleccionPagoFormacion)) {
                this.toPagosFormacion();
            } else {
                this.mensajeDeError("No se pudo editar el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void eliminarPagoFormacion() throws IOException {
        if (pagoFormacionFacade.remove(seleccionPagoFormacion)) {
            this.toPagosFormacion();
        } else {
            this.mensajeDeError("No se pudo eliminar el pago.");
        }
    }

    public void toPagosFormacion() throws IOException {
        this.redireccionarViewId("/configuracion/pagoformacion/PagosFormacion.xhtml");
    }

    public void toNuevoPagoFormacion() throws IOException {
        this.redireccionarViewId("/configuracion/pagoformacion/nuevoPagoFormacion.xhtml");
    }

    public void toEditarPagoFormacion() throws IOException {
        this.redireccionarViewId("/configuracion/pagoformacion/editarPagoFormacion.xhtml");
    }

    /**
     * @return the nuevoPagoFormacion
     */
    public PagoFormacion getNuevoPagoFormacion() {
        return nuevoPagoFormacion;
    }

    /**
     * @param nuevoPagoFormacion the nuevoPagoFormacion to set
     */
    public void setNuevoPagoFormacion(PagoFormacion nuevoPagoFormacion) {
        this.nuevoPagoFormacion = nuevoPagoFormacion;
    }

    /**
     * @return the seleccionPagoFormacion
     */
    public PagoFormacion getSeleccionPagoFormacion() {
        return seleccionPagoFormacion;
    }

    /**
     * @param seleccionPagoFormacion the seleccionPagoFormacion to set
     */
    public void setSeleccionPagoFormacion(PagoFormacion seleccionPagoFormacion) {
        this.seleccionPagoFormacion = seleccionPagoFormacion;
    }

}
