/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.excels.PlanillaLibroVentas;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Tincho
 */
@Named("ReporteLibroDeVentasIVAController")
@SessionScoped
public class ReporteLibroDeVentasIVAController extends Controller implements Serializable {

    private static final String TIPO_PDF = "PDF";
    private static final String TIPO_TXT = "TXT";

    private static final String SISTEMA_NSF = "NUEVO SISTEMA DE FACTURACION";
    private static final String SISTEMA_SFV = "SISTEMA DE FACTURACION VIRTUAL";

    @EJB
    CampusFacade campusFacade;
    @EJB
    FacturaFacade facturaFacade;

    private Campus seleccionCampus;
    private Date desde;
    private Date hasta;
    private String formato;
    private String sistema;

    private StreamedContent fileXLSX;
    private String fileNameXLSX;

    public List<Campus> listaCampus() {
        return campusFacade.listaCampus();
    }

    public void generarReporte() throws IOException {
        this.insertarParametro("id_campus", seleccionCampus.getId_campus());
        this.insertarParametro("desde", desde);
        this.insertarParametro("hasta", hasta);

        if (sistema.compareTo(SISTEMA_NSF) == 0) {
            if (formato.compareTo(TIPO_PDF) == 0) {
                this.redireccionarURL("/zeus/reportes/LibroDeVentasIVA");
            } else if (formato.compareTo(TIPO_TXT) == 0) {
                this.redireccionarURL("/zeus/reportes/LibroDeVentasIVATXT");
            }
        } else if (sistema.compareTo(SISTEMA_SFV) == 0) {
            if (formato.compareTo(TIPO_PDF) == 0) {
                this.redireccionarURL("/zeus/reportes/LibroDeVentasIVA_SFV");
            } else if (formato.compareTo(TIPO_TXT) == 0) {
                this.redireccionarURL("/zeus/reportes/LibroDeVentasIVATXT_SFV");
            }
        }
    }

    public void generarXLSX() throws DocumentException, BadElementException, IOException {
        fileNameXLSX = seleccionCampus.getNombre();

        PlanillaLibroVentas planillaLibroVentas = new PlanillaLibroVentas();
        List<Factura> facturas = facturaFacade.listaFacturas(seleccionCampus.getId_campus(), desde, hasta);
        planillaLibroVentas.generarXLSX(facturas, fileNameXLSX);
    }

    public void leerXLSX() {
        try {
            InputStream stream = new FileInputStream(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + fileNameXLSX + ".xlsx");
            fileXLSX = new DefaultStreamedContent(stream, "application/octet-stream", seleccionCampus.getNombre() + ".xlsx");
        } catch (FileNotFoundException ex) {

        }
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
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
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

    /**
     * @return the sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    /**
     * @return the fileXLSX
     */
    public StreamedContent getFileXLSX() {
        leerXLSX();

        return fileXLSX;
    }
}
