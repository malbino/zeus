
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Tincho
 */
public abstract class Controller implements Serializable {

    protected void mensajeDeError(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    protected void mensajeDeInformacion(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
    }

    protected void insertarParametro(String key, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().put(key, value);
    }

    protected Object recuperarParametro(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        return extContext.getSessionMap().get(key);
    }

    protected void eliminarParametro(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().remove(key);
    }

    protected void redireccionarViewId(String viewId) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, viewId));
        extContext.redirect(url);
    }

    protected void redireccionarURL(String url) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.redirect(url);
    }

    public void invalidateSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();

        extContext.invalidateSession();
    }

    protected void ejecutar(String javascript) {
        RequestContext.getCurrentInstance().execute(javascript);
    }

    protected String recuperarIP() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
        return request.getRemoteAddr();
    }
}
