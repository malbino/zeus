/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.zeus.entities.PagoTramCert;
import edu.infocalcbba.zeus.facades.PagoTramCertFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("PagoTramCertController")
@SessionScoped
public class PagoTramCertController extends Controller implements Serializable {

    @EJB
    PagoTramCertFacade pagoTramCertFacade;

    private PagoTramCert nuevoPagoTramCert = new PagoTramCert();
    private PagoTramCert seleccionPagoTramCert;

    public List<PagoTramCert> listaPagosTramCerts() {
        return pagoTramCertFacade.listaPagosTramCert();
    }

    public void crearPagoTramCert() throws IOException {
        if (pagoTramCertFacade.buscarPagoTramCert(nuevoPagoTramCert.getCodigo()) == null) {
            if (pagoTramCertFacade.create(nuevoPagoTramCert)) {
                nuevoPagoTramCert = new PagoTramCert();

                this.toPagosTramCert();
            } else {
                this.mensajeDeError("No se pudo crear el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void editarPagoTramCert() throws IOException {
        if (pagoTramCertFacade.buscarPagoTramCert(seleccionPagoTramCert.getCodigo(), seleccionPagoTramCert.getId_pagotramcert()) == null) {
            if (pagoTramCertFacade.edit(seleccionPagoTramCert)) {
                this.toPagosTramCert();
            } else {
                this.mensajeDeError("No se pudo editar el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void eliminarPagoTramCert() throws IOException {
        if (pagoTramCertFacade.remove(seleccionPagoTramCert)) {
            this.toPagosTramCert();
        } else {
            this.mensajeDeError("No se pudo eliminar el pago.");
        }
    }

    public void toPagosTramCert() throws IOException {
        this.redireccionarViewId("/configuracion/pagotramcert/PagosTramCert.xhtml");
    }

    public void toNuevoPagoTramCert() throws IOException {
        this.redireccionarViewId("/configuracion/pagotramcert/nuevoPagoTramCert.xhtml");
    }

    public void toEditarPagoTramCert() throws IOException {
        this.redireccionarViewId("/configuracion/pagotramcert/editarPagoTramCert.xhtml");
    }

    /**
     * @return the seleccionPagoTramCert
     */
    public PagoTramCert getSeleccionPagoTramCert() {
        return seleccionPagoTramCert;
    }

    /**
     * @param seleccionPagoTramCert the seleccionPagoTramCert to set
     */
    public void setSeleccionPagoTramCert(PagoTramCert seleccionPagoTramCert) {
        this.seleccionPagoTramCert = seleccionPagoTramCert;
    }

    /**
     * @return the nuevoPagoTramCert
     */
    public PagoTramCert getNuevoPagoTramCert() {
        return nuevoPagoTramCert;
    }

    /**
     * @param nuevoPagoTramCert the nuevoPagoTramCert to set
     */
    public void setNuevoPagoTramCert(PagoTramCert nuevoPagoTramCert) {
        this.nuevoPagoTramCert = nuevoPagoTramCert;
    }

}
