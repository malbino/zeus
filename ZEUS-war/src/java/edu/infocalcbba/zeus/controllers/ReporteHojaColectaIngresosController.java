/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.publica.controllers.Controller;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("ReporteHojaColectaIngresosController")
@SessionScoped
public class ReporteHojaColectaIngresosController extends Controller implements Serializable {

    @EJB
    CampusFacade campusFacade;

    private Campus seleccionCampus;
    private Date desde;
    private Date hasta;

    public List<Campus> listaCampus() {
        return campusFacade.listaCampus();
    }
    
    public void generarReporte() throws IOException {
        this.insertarParametro("id_campus", seleccionCampus.getId_campus());
        this.insertarParametro("desde", desde);
        this.insertarParametro("hasta", hasta);
        this.redireccionarURL("/zeus/reportes/HojaColectaIngresos");
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
