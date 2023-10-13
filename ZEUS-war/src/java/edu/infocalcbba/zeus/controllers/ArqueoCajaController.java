/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.kardia.facades.EmpleadoFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.FacturaFacade;
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
@Named("ArqueoCajaController")
@SessionScoped
public class ArqueoCajaController extends Controller implements Serializable {

    private static final String CONDICION_CERRRADO = "CERRADO";

    @EJB
    EmpleadoFacade empleadoFacade;
    @Inject
    LoginController loginController;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    TurnoCajaFacade turnoCajaFacade;

    private List<Factura> listaFacturas = new ArrayList();
    private TurnoCaja seleccionTurnoCaja;
    private Empleado empleado;

    public void init() throws IOException {
        empleado = empleadoFacade.find(loginController.getUsr().getId_persona());
        seleccionTurnoCaja = turnoCajaFacade.turnoCajaAbierto(empleado.getId_persona());

        if (seleccionTurnoCaja != null) {
            listaFacturas = facturaFacade.listaFacturas(seleccionTurnoCaja.getId_turnocaja());
        }

        toArqueoCaja();
    }

    public double monto() {
        double monto = 0;

        for (Factura factura : listaFacturas) {
            monto += factura.getMonto();
        }

        return monto;
    }

    public void cerrrarTurno() throws IOException {
        seleccionTurnoCaja.setFin(Reloj.getDate());
        seleccionTurnoCaja.setCondicion(CONDICION_CERRRADO);
        if (turnoCajaFacade.edit(seleccionTurnoCaja)) {
            this.insertarParametro("id_turnocaja", seleccionTurnoCaja.getId_turnocaja());

            this.ejecutar("PF('dlg2').show();");
        } else {
            this.mensajeDeError("No se pudo cerrar el turno.");
        }
    }

    public void toArqueoCaja() throws IOException {
        this.redireccionarViewId("/caja/ArqueoCaja.xhtml");
    }

    public void toHome() throws IOException {
        this.redireccionarViewId("/home.xhtml");
    }

    /**
     * @return the seleccionTurnoCaja
     */
    public TurnoCaja getSeleccionTurnoCaja() {
        return seleccionTurnoCaja;
    }

    /**
     * @param seleccionTurnoCaja the seleccionTurnoCaja to set
     */
    public void setSeleccionTurnoCaja(TurnoCaja seleccionTurnoCaja) {
        this.seleccionTurnoCaja = seleccionTurnoCaja;
    }

    /**
     * @return the listaFacturas
     */
    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    /**
     * @param listaFacturas the listaFacturas to set
     */
    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }

    /**
     * @return the empleado
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * @param empleado the empleado to set
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
