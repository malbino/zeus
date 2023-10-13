/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.zeus.entities.PagoExterno;
import edu.infocalcbba.zeus.facades.PagoExternoFacade;
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
@Named("PagoExternoController")
@SessionScoped
public class PagoExternoController extends Controller implements Serializable {

    @EJB
    PagoExternoFacade pagoExternoFacade;

    private PagoExterno nuevoPagoExterno = new PagoExterno();
    private PagoExterno seleccionPagoExterno;

    public List<PagoExterno> listaPagosExternos() {
        return pagoExternoFacade.listaPagosExternos();
    }

    public void crearPagoExterno() throws IOException {
        if (pagoExternoFacade.buscarPagoExterno(nuevoPagoExterno.getCodigo()) == null) {
            if (pagoExternoFacade.create(nuevoPagoExterno)) {
                nuevoPagoExterno = new PagoExterno();

                this.toPagosExternos();
            } else {
                this.mensajeDeError("No se pudo crear el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void editarPagoExterno() throws IOException {
        if (pagoExternoFacade.buscarPagoExterno(seleccionPagoExterno.getCodigo(), seleccionPagoExterno.getId_pagoexterno()) == null) {
            if (pagoExternoFacade.edit(seleccionPagoExterno)) {
                this.toPagosExternos();
            } else {
                this.mensajeDeError("No se pudo editar el pago.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void eliminarPagoExterno() throws IOException {
        if (pagoExternoFacade.remove(seleccionPagoExterno)) {
            this.toPagosExternos();
        } else {
            this.mensajeDeError("No se pudo eliminar el pago.");
        }
    }

    public void toPagosExternos() throws IOException {
        this.redireccionarViewId("/configuracion/pagoexterno/PagosExternos.xhtml");
    }

    public void toNuevoPagoExterno() throws IOException {
        this.redireccionarViewId("/configuracion/pagoexterno/nuevoPagoExterno.xhtml");
    }

    public void toEditarPagoExterno() throws IOException {
        this.redireccionarViewId("/configuracion/pagoexterno/editarPagoExterno.xhtml");
    }

    /**
     * @return the seleccionPagoExterno
     */
    public PagoExterno getSeleccionPagoExterno() {
        return seleccionPagoExterno;
    }

    /**
     * @param seleccionPagoExterno the seleccionPagoExterno to set
     */
    public void setSeleccionPagoExterno(PagoExterno seleccionPagoExterno) {
        this.seleccionPagoExterno = seleccionPagoExterno;
    }

    /**
     * @return the nuevoPagoExterno
     */
    public PagoExterno getNuevoPagoExterno() {
        return nuevoPagoExterno;
    }

    /**
     * @param nuevoPagoExterno the nuevoPagoExterno to set
     */
    public void setNuevoPagoExterno(PagoExterno nuevoPagoExterno) {
        this.nuevoPagoExterno = nuevoPagoExterno;
    }

}
