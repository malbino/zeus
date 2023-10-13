/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("ReporteImpresionFacturaController")
@SessionScoped
public class ReporteImpresionFacturaController extends Controller implements Serializable {

    @EJB
    FacturaFacade facturaFacade;

    private Integer numero;
    private Factura seleccionFactura;

    public List<Factura> buscarFacturas() {
        List<Factura> l = new ArrayList();

        if (numero != null) {
            l = facturaFacade.buscarFacturas(numero);
        }

        return l;
    }

    public void imprimirFactura() {
        List<Detalle> detalles = seleccionFactura.getDetalles();
        if (!detalles.isEmpty()) {
            Detalle detalle = detalles.get(0);
            if (detalle.getCarrera() == null && detalle.getCuota() == null && detalle.getEstudiante() == null
                    && detalle.getPago() == null && detalle.getDerecho() == null) {
                this.insertarParametro("id_factura", seleccionFactura.getId_factura());
                //comprobante formacion
                this.insertarParametro("est", null);
                this.insertarParametro("car", null);
                //comprobante capacitacion
                this.insertarParametro("car_cap", null);
                this.insertarParametro("gru_cap", null);
                this.insertarParametro("est_cap", null);
                this.insertarParametro("cel_cap", null);

                this.ejecutar("PF('dlg1').show();");
            } else if (detalle.getCarrera() != null && detalle.getCuota() == null && detalle.getEstudiante() != null
                    && detalle.getPago() == null && detalle.getDerecho() == null) {
                this.insertarParametro("id_factura", seleccionFactura.getId_factura());
                this.insertarParametro("id_carrera", detalle.getCarrera().getId_carrera());
                this.insertarParametro("id_estudiante", detalle.getEstudiante().getId_persona());

                this.ejecutar("PF('dlg2').show();");
            } else if (detalle.getCarrera() == null && detalle.getCuota() == null && detalle.getEstudiante() == null
                    && detalle.getPago() != null && detalle.getDerecho() == null) {
                this.insertarParametro("id_factura", seleccionFactura.getId_factura());
                this.insertarParametro("id_inscritoph", detalle.getPago().getPlanpago().getInscrito().getId_inscrito());

                this.ejecutar("PF('dlg3').show();");
            } else if (detalle.getCarrera() == null && detalle.getCuota() != null && detalle.getEstudiante() == null
                    && detalle.getPago() == null && detalle.getDerecho() == null) {
                this.insertarParametro("id_factura", seleccionFactura.getId_factura());
                this.insertarParametro("id_inscrito", detalle.getCuota().getInscrito().getId_inscrito());

                this.ejecutar("PF('dlg4').show();");
            } else if (detalle.getCarrera() == null && detalle.getCuota() == null && detalle.getEstudiante() == null
                    && detalle.getPago() == null && detalle.getDerecho() != null) {
                this.insertarParametro("id_factura", seleccionFactura.getId_factura());
                this.insertarParametro("id_defensagrado", detalle.getDerecho().getDefensaGrado().getId_defensagrado());

                this.ejecutar("PF('dlg5').show();");
            }
        }
    }

    public void toReporteImprimirFactura() throws IOException {
        this.redireccionarViewId("/reportes/ReporteImpresionFactura.xhtml");
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
