/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.publica.entities.Parametro;
import edu.infocalcbba.publica.facades.ParametroFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("ConfiguracionController")
@SessionScoped
public class ConfiguracionController extends Controller implements Serializable {

    private static final int MAILING = 2;
    private static final int FACTURACION = 3;

    @EJB
    ParametroFacade parametroFacade;

    private Parametro seleccionParametro;

    private List<Parametro> parametrosMailing = new ArrayList();
    private List<Parametro> parametrosFacturacion = new ArrayList();

    @PostConstruct
    public void init() {
        seleccionParametro = null;
        parametrosMailing = parametroFacade.listaParametros(MAILING);
        parametrosFacturacion = parametroFacade.listaParametros(FACTURACION);
    }

    public void reinit() {
        seleccionParametro = null;
        parametrosMailing = parametroFacade.listaParametros(MAILING);
        parametrosFacturacion = parametroFacade.listaParametros(FACTURACION);
    }

    public void editarParametro() throws IOException {
        if (parametroFacade.edit(seleccionParametro)) {
            reinit();

            this.redireccionarViewId("/admin/configuracion/Configuracion.xhtml");
        } else {
            this.mensajeDeError("No se pudo editar el parametro.");
        }

    }

    public void toConfiguracion() throws IOException {
        this.redireccionarViewId("/admin/configuracion/Configuracion.xhtml");
    }

    public void toEditarParametro() throws IOException {
        this.redireccionarViewId("/admin/configuracion/editarParametro.xhtml");
    }

    /**
     * @return the seleccionConfiguracion
     */
    public Parametro getSeleccionParametro() {
        return seleccionParametro;
    }

    /**
     * @param seleccionParametro the seleccionConfiguracion to set
     */
    public void setSeleccionParametro(Parametro seleccionParametro) {
        this.seleccionParametro = seleccionParametro;
    }


    /**
     * @return the parametrosMailing
     */
    public List<Parametro> getParametrosMailing() {
        return parametrosMailing;
    }

    /**
     * @param parametrosMailing the parametrosMailing to set
     */
    public void setParametrosMailing(List<Parametro> parametrosMailing) {
        this.parametrosMailing = parametrosMailing;
    }

    /**
     * @return the parametrosFacturacion
     */
    public List<Parametro> getParametrosFacturacion() {
        return parametrosFacturacion;
    }

    /**
     * @param parametrosFacturacion the parametrosFacturacion to set
     */
    public void setParametrosFacturacion(List<Parametro> parametrosFacturacion) {
        this.parametrosFacturacion = parametrosFacturacion;
    }
}
