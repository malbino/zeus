/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.servlets;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tincho
 */
@WebServlet(name = "LibroDeVentasIVATXT", urlPatterns = {"/reportes/LibroDeVentasIVATXT"})
public class LibroDeVentasIVATXT extends HttpServlet {

    private static final String CONTENIDO_TXT = "text/plain";

    private static final double PROPORCION_IVA = 0.13;
    private static final String CONDICION_VALIDA = "VALIDA";
    private static final String CONDICION_ANULADA = "ANULADA";

    @EJB
    CampusFacade campusFacade;
    @EJB
    FacturaFacade facturaFacade;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarTXT(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarTXT(request, response);
    }

    public void generarTXT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id_campus = (Integer) request.getSession().getAttribute("id_campus");
        Date desde = (Date) request.getSession().getAttribute("desde");
        Date hasta = (Date) request.getSession().getAttribute("hasta");

        if (id_campus != null && desde != null && hasta != null) {
            Campus campus = campusFacade.find(id_campus);

            response.setContentType(CONTENIDO_TXT);
            String reportName = "LibrDeVentasIVA_" + campus.getNombre() + "_" + Reloj.formatearFecha_MM(desde) + Reloj.formatearFecha_yyyy(desde) + ".txt";
            response.setHeader("Content-disposition", "attachment; "
                    + "filename=" + reportName);

            ArrayList<String> rows = contenido(campus, desde, hasta);

            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()) {
                String outputString = (String) iter.next();
                response.getOutputStream().print(outputString);
            }

            response.getOutputStream().flush();
        } else {
            response.setContentType(CONTENIDO_TXT);
            String reportName = "Error.txt";
            response.setHeader("Content-disposition", "attachment; "
                    + "filename=" + reportName);

            ArrayList<String> rows = new ArrayList();
            rows.add("Error al generar el reporte.");

            Iterator<String> iter = rows.iterator();
            while (iter.hasNext()) {
                String outputString = (String) iter.next();
                response.getOutputStream().print(outputString);
            }

            response.getOutputStream().flush();
        }

    }

    public ArrayList<String> contenido(Campus campus, Date desde, Date hasta) {
        ArrayList<String> rows = new ArrayList();

        List<Factura> facturas = facturaFacade.listaFacturas(campus.getId_campus(), desde, hasta);
        for (int i = 0; i < facturas.size(); i++) {
            Factura factura = facturas.get(i);

            String nitci_cliente = "";
            String nombrerazonsocial_cliente = "";
            String numero_factura = "";
            String numero_autorizacion = "";
            String fecha_factura = "";
            String total_factura = "";
            String total_ice = "";
            String importes_exentos = "";
            String importe_neto = "";
            String debitofiscal = "";
            String condicion = "";
            String codigo_control = "";
            
            if (factura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
                nitci_cliente = String.valueOf(factura.getCliente().getNit_ci());
                nombrerazonsocial_cliente = factura.getCliente().getNombre_razonsocial();
                numero_factura = String.valueOf(factura.getNumero());
                numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
                fecha_factura = Reloj.formatearFecha_ddMMyyyy(factura.getFecha());
                total_factura = Redondeo.formatear_ssm(factura.getMonto());
                total_ice = "0";
                importes_exentos = "0";
                importe_neto = Redondeo.formatear_ssm(factura.getMonto());
                debitofiscal = Redondeo.formatear_ssm(factura.getMonto() * PROPORCION_IVA);
                condicion = factura.getCondicionAbreviada();
                codigo_control = factura.getCodigo_control();
            } else if (factura.getCondicion().compareTo(CONDICION_ANULADA) == 0) {
                nitci_cliente = "0";
                nombrerazonsocial_cliente = CONDICION_ANULADA;
                numero_factura = String.valueOf(factura.getNumero());
                numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
                fecha_factura = Reloj.formatearFecha_ddMMyyyy(factura.getFecha());
                total_factura = "0";
                total_ice = "0";
                importes_exentos = "0";
                importe_neto = "0";
                debitofiscal = "0";
                condicion = factura.getCondicionAbreviada();
                codigo_control = "0";
            }

            String fila = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", nitci_cliente, nombrerazonsocial_cliente, numero_factura, numero_autorizacion,
                    fecha_factura, total_factura, total_ice, importes_exentos, importe_neto, debitofiscal, condicion, codigo_control);

            rows.add(fila);
            rows.add("\r\n");
        }

        return rows;
    }
}
