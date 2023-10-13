/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.argos.entities.Inscrito;
import edu.infocalcbba.argos.facades.CuotaFacade;
import edu.infocalcbba.argos.facades.InscritoFacade;
import edu.infocalcbba.log_argos.entities.LogCuota;
import edu.infocalcbba.log_argos.facades.LogCuotaFacade;
import edu.infocalcbba.mikrotik.facades.MikrotikFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facades.negocio.AnularFacturaFacade;
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
@Named("AnularFacturaController")
@SessionScoped
public class AnularFacturaController extends Controller implements Serializable {

    private static final String CONDICION_VALIDA = "VALIDA";
    private static final String CONDICION_ANULADA = "ANULADA";

    @EJB
    FacturaFacade facturaFacade;
    @EJB
    AnularFacturaFacade anularFacturaFacade;
    @EJB
    LogCuotaFacade logCuotaFacade;
    @EJB
    CuotaFacade cuotaFacade;
    @EJB
    InscritoFacade inscritoFacade;
    @EJB
    MikrotikFacade mikrotikFacade;
    @Inject
    LoginController loginController;

    private Integer numero;
    private Factura seleccionFactura;

    public List<Factura> buscarFacturas() {
        List<Factura> l = new ArrayList();

        if (numero != null) {
            l = facturaFacade.buscarFacturas(numero);
        }

        return l;
    }

    public boolean facturaFormacion(List<Detalle> detalles) {
        boolean res = true;

        for (Detalle detalle : detalles) {
            if (detalle.getCuota() == null) {
                res = false;
                break;
            }
        }
        return res;
    }

    public void anularFactura() throws IOException {
        if (seleccionFactura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
            seleccionFactura.setCondicion(CONDICION_ANULADA);
            if (anularFacturaFacade.anularFactura(seleccionFactura)) {
                for (Detalle detalle : seleccionFactura.getDetalles()) {
                    if (detalle.getCuota() != null) {
                        logCuotaFacade.create(new LogCuota(Reloj.getDate(), loginController.getUsr().toString(), recuperarIP(), "EDICION CUOTA POR ANULAR FACTURA", detalle.getCuota().getId_cuota(), detalle.getCuota().getCodigo(), detalle.getCuota().getConcepto(), detalle.getCuota().getNumero(), detalle.getCuota().getMonto(), detalle.getCuota().getPagado(), detalle.getCuota().getAdeudado(), detalle.getCuota().getCondicion()));
                    }
                }

                if (facturaFormacion(seleccionFactura.getDetalles())) {
                    Inscrito inscrito = inscritoFacade.buscarInscrito(seleccionFactura.getId_factura());
                    if (inscrito != null) {
                        long pagadoColegiatura = cuotaFacade.pagadoColegiatura(inscrito.getId_inscrito());
                        mikrotikFacade.editLimitBytesOutHotspotUser(inscrito.getEstudiante().getDni(), pagadoColegiatura);
                    }
                }
            } else {
                this.mensajeDeError("No se pudo anular la factura.");
            }
        } else {
            this.mensajeDeError("La factura ya esta anulada.");
        }
    }

    /**
     * @return the seleccionFactura
     */
    public Factura getSeleccionFactura() {
        return seleccionFactura;
    }

    /**
     * @param seleccionFactura the seleccionFactura to set
     */
    public void setSeleccionFactura(Factura seleccionFactura) {
        this.seleccionFactura = seleccionFactura;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
