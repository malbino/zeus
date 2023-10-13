/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.kardia.facades.EmpleadoFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.TurnoCajaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("ReporteFacturacionCajeroController")
@SessionScoped
public class ReporteFacturacionCajeroController extends Controller implements Serializable {

    @EJB
    CampusFacade campusFacade;
    @EJB
    EmpleadoFacade empleadoFacade;

    private Campus seleccionCampus;
    private Empleado seleccionEmpleado;
    private Date desde;
    private Date hasta;

    public List<Campus> listaCampus() {
        return campusFacade.listaCampus();
    }

    public List<Empleado> listaEmpleados() {
        return empleadoFacade.listaEmpleados();
    }


    public void generarReporte() throws IOException {
        this.insertarParametro("id_campus", seleccionCampus.getId_campus());
        this.insertarParametro("id_persona", seleccionEmpleado.getId_persona());
        this.insertarParametro("desde", desde);
        this.insertarParametro("hasta", hasta);

        this.redireccionarURL("/zeus/reportes/FacturacionCajero");
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
     * @return the seleccionEmpleado
     */
    public Empleado getSeleccionEmpleado() {
        return seleccionEmpleado;
    }

    /**
     * @param seleccionEmpleado the seleccionEmpleado to set
     */
    public void setSeleccionEmpleado(Empleado seleccionEmpleado) {
        this.seleccionEmpleado = seleccionEmpleado;
    }

    /**
     * @return the desde
     */
    public Date getDesde() {
        return desde;
    }

    /**
     * @param desde the desde to set
     */
    public void setDesde(Date desde) {
        this.desde = desde;
    }

    /**
     * @return the hasta
     */
    public Date getHasta() {
        return hasta;
    }

    /**
     * @param hasta the hasta to set
     */
    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }
}
