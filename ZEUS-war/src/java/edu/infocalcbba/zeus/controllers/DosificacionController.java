/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Dosificacion;
import edu.infocalcbba.zeus.facades.DosificacionFacade;
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
@Named("DosificacionController")
@SessionScoped
public class DosificacionController extends Controller implements Serializable {

    @EJB
    DosificacionFacade dosificacionFacade;
    @EJB
    CampusFacade campusFacade;

    private Campus seleccionCampus;
    private Dosificacion nuevaDosificacion = new Dosificacion();

    private Long numero_autorizacion1;
    private Long numero_autorizacion2;
    private Date flimite_emision1;
    private Date flimite_emision2;
    private String llave_dosificacion1;
    private String llave_dosificacion2;

    public Dosificacion getDosificacion(Integer id) {
        return dosificacionFacade.find(id);
    }

    public List<Dosificacion> listaDosificaciones() {
        List<Dosificacion> l = new ArrayList();
        if (seleccionCampus != null) {
            l = dosificacionFacade.listaDosificaciones(seleccionCampus.getId_campus());
        }

        return l;
    }

    public List<Campus> listaCampus() {
        return campusFacade.listaCampus();
    }

    public void crearDosificacion() throws IOException {
        if (numero_autorizacion1.compareTo(numero_autorizacion2) == 0) {
            if (flimite_emision1.compareTo(flimite_emision2) == 0) {
                if (llave_dosificacion1.compareTo(llave_dosificacion2) == 0) {
                        nuevaDosificacion.setNumero_autorizacion(numero_autorizacion1);
                        nuevaDosificacion.setFlimite_emision(flimite_emision1);
                        nuevaDosificacion.setLlave_dosificacion(llave_dosificacion1);
                        nuevaDosificacion.setFactivacion(Reloj.getDate());
                        nuevaDosificacion.setCampus(seleccionCampus);
                        if (dosificacionFacade.buscarDosificacion(nuevaDosificacion.getNumero_autorizacion()) == null) {
                            if (dosificacionFacade.create(nuevaDosificacion)) {
                                nuevaDosificacion = new Dosificacion();

                                this.toDosificaciones();
                            } else {
                                this.mensajeDeError("No se pudo crear el dosificacion.");
                            }
                        } else {
                            this.mensajeDeError("No se pudo crear el dosificacion, codigo repetido.");
                        }
                } else {
                    this.mensajeDeError("Las Llaves de Dosificación introducidas no coinciden.");
                }
            } else {
                this.mensajeDeError("Las Fechas Limites de Emisión introducidas no coinciden.");
            }
        } else {
            this.mensajeDeError("Los Numeros de Autorización introducidos no coinciden.");
        }
    }

    public void limpiar() {
        nuevaDosificacion = new Dosificacion();

        numero_autorizacion1 = null;
        numero_autorizacion2 = null;
        flimite_emision1 = null;
        flimite_emision2 = null;
        llave_dosificacion1 = null;
        llave_dosificacion2 = null;
    }


    public void toDosificaciones() throws IOException {
        this.redireccionarViewId("/configuracion/dosificacion/Dosificaciones.xhtml");
    }

    public void toNuevoDosificacion() throws IOException {
        this.limpiar();
        
        this.redireccionarViewId("/configuracion/dosificacion/nuevaDosificacion.xhtml");
    }

    public void toEditarDosificacion() throws IOException {
        this.redireccionarViewId("/configuracion/dosificacion/editarDosificacion.xhtml");
    }

    /**
     * @return the nuevaDosificacion
     */
    public Dosificacion getNuevaDosificacion() {
        return nuevaDosificacion;
    }

    /**
     * @param nuevaDosificacion the nuevaDosificacion to set
     */
    public void setNuevaDosificacion(Dosificacion nuevaDosificacion) {
        this.nuevaDosificacion = nuevaDosificacion;
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
     * @return the numero_autorizacion1
     */
    public Long getNumero_autorizacion1() {
        return numero_autorizacion1;
    }

    /**
     * @param numero_autorizacion1 the numero_autorizacion1 to set
     */
    public void setNumero_autorizacion1(Long numero_autorizacion1) {
        this.numero_autorizacion1 = numero_autorizacion1;
    }

    /**
     * @return the numero_autorizacion2
     */
    public Long getNumero_autorizacion2() {
        return numero_autorizacion2;
    }

    /**
     * @param numero_autorizacion2 the numero_autorizacion2 to set
     */
    public void setNumero_autorizacion2(Long numero_autorizacion2) {
        this.numero_autorizacion2 = numero_autorizacion2;
    }

    /**
     * @return the flimite_emision1
     */
    public Date getFlimite_emision1() {
        return flimite_emision1;
    }

    /**
     * @param flimite_emision1 the flimite_emision1 to set
     */
    public void setFlimite_emision1(Date flimite_emision1) {
        this.flimite_emision1 = flimite_emision1;
    }

    /**
     * @return the flimite_emision2
     */
    public Date getFlimite_emision2() {
        return flimite_emision2;
    }

    /**
     * @param flimite_emision2 the flimite_emision2 to set
     */
    public void setFlimite_emision2(Date flimite_emision2) {
        this.flimite_emision2 = flimite_emision2;
    }

    /**
     * @return the llave_dosificacion1
     */
    public String getLlave_dosificacion1() {
        return llave_dosificacion1;
    }

    /**
     * @param llave_dosificacion1 the llave_dosificacion1 to set
     */
    public void setLlave_dosificacion1(String llave_dosificacion1) {
        this.llave_dosificacion1 = llave_dosificacion1;
    }

    /**
     * @return the llave_dosificacion2
     */
    public String getLlave_dosificacion2() {
        return llave_dosificacion2;
    }

    /**
     * @param llave_dosificacion2 the llave_dosificacion2 to set
     */
    public void setLlave_dosificacion2(String llave_dosificacion2) {
        this.llave_dosificacion2 = llave_dosificacion2;
    }
}
