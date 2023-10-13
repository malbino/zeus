/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.kardia.facades.EmpleadoFacade;
import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Caja;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.CajaFacade;
import edu.infocalcbba.zeus.facades.TurnoCajaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("AbrirTurnoController")
@SessionScoped
public class AbrirTurnoController extends Controller implements Serializable {

    private static final String CONDICION_ABIERTO = "ABIERTO";

    @EJB
    CampusFacade campusFacade;
    @EJB
    CajaFacade cajaFacade;
    @EJB
    EmpleadoFacade empleadoFacade;
    @Inject
    LoginController loginController;
    @EJB
    TurnoCajaFacade turnoCajaFacade;

    private Campus seleccionCampus;
    private Caja seleccionCaja;
    private TurnoCaja nuevoTurnoCaja = new TurnoCaja();
    
    public List<Campus> listaCampus() {
        return campusFacade.listaCampus();
    }

    public List<Caja> listaCajas() {
        List<Caja> l = new ArrayList();

        if (seleccionCampus != null) {
            l = cajaFacade.listaCajas(seleccionCampus.getId_campus());
        }

        return l;
    }

    public void crearTurnoCaja() throws IOException {
        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(loginController.getUsr().getId_persona());
        if (turnoCaja == null) {
            nuevoTurnoCaja.setInicio(Reloj.getDate());
            nuevoTurnoCaja.setCondicion(CONDICION_ABIERTO);
            nuevoTurnoCaja.setCaja(seleccionCaja);
            Empleado empleado = empleadoFacade.find(loginController.getUsr().getId_persona());
            nuevoTurnoCaja.setEmpleado(empleado);
            if (turnoCajaFacade.create(nuevoTurnoCaja)) {
                this.toHome();
            } else {
                this.mensajeDeError("No se pudo abrir el Turno.");
            }
        } else {
            this.mensajeDeError("Ya existe un turno abierto.");
        }

    }
    
    public void toHome() throws IOException {
        this.redireccionarViewId("/home.xhtml");
    }

    /**
     * @return the seleccionCampus
     */
    public Campus getSeleccionCampus() {
        return seleccionCampus;
    }

    /**
     * @param seleccionCampus the seleccionCampus to set
     */
    public void setSeleccionCampus(Campus seleccionCampus) {
        this.seleccionCampus = seleccionCampus;
    }

    /**
     * @return the seleccionCaja
     */
    public Caja getSeleccionCaja() {
        return seleccionCaja;
    }

    /**
     * @param seleccionCaja the seleccionCaja to set
     */
    public void setSeleccionCaja(Caja seleccionCaja) {
        this.seleccionCaja = seleccionCaja;
    }

    /**
     * @return the nuevoTurnoCaja
     */
    public TurnoCaja getNuevoTurnoCaja() {
        return nuevoTurnoCaja;
    }

    /**
     * @param nuevoTurnoCaja the nuevoTurnoCaja to set
     */
    public void setNuevoTurnoCaja(TurnoCaja nuevoTurnoCaja) {
        this.nuevoTurnoCaja = nuevoTurnoCaja;
    }
}
