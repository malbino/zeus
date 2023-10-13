/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.servlets;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
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
@WebServlet(name = "LibroDeVentasIVATXT_SFV", urlPatterns = {"/reportes/LibroDeVentasIVATXT_SFV"})
public class LibroDeVentasIVATXT_SFV extends HttpServlet {

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
            String reportName = "LibrDeVentasIVA_" + campus.getNombre() + "_" + Reloj.formatearFecha_yyyy(desde) + Reloj.formatearFecha_MM(desde) + ".txt";
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
            
            String especificacion = "";
            String numero = "";
            String fecha_factura = "";
            String numero_factura = "";
            String numero_autorizacion = "";;
            String estado = "";
            String nitci_cliente = "";
            String nombrerazonsocial_cliente = "";
            String importe_venta = "";
            String importe_iceiehdtasas = "";
            String exportaciones_operacionesexcentas = "";
            String ventas_gravadastasacero = "";
            String subtotal = "";
            String descuentos_bonificaciones_rebajas = "";
            String importebase_debitofiscal = "";
            String debitofiscal = "";
            String codigo_control = "";

            if (factura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
                especificacion = "3";
                numero = String.valueOf(i + 1);
                fecha_factura = Reloj.formatearFecha_ddMMyyyy(factura.getFecha());
                numero_factura = String.valueOf(factura.getNumero());
                numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
                estado = factura.getCondicionAbreviada();
                nitci_cliente = String.valueOf(factura.getCliente().getNit_ci());
                nombrerazonsocial_cliente = factura.getCliente().getNombre_razonsocial();
                importe_venta = Redondeo.formatear_ssm(factura.getMonto());
                importe_iceiehdtasas = "0";
                exportaciones_operacionesexcentas = "0";
                ventas_gravadastasacero = "0";
                subtotal = Redondeo.formatear_ssm(factura.getMonto());
                descuentos_bonificaciones_rebajas = "0";
                importebase_debitofiscal = Redondeo.formatear_ssm(factura.getMonto());
                debitofiscal = Redondeo.formatear_ssm(factura.getMonto() * PROPORCION_IVA);
                codigo_control = factura.getCodigo_control();
            } else if (factura.getCondicion().compareTo(CONDICION_ANULADA) == 0) {
                especificacion = "3";
                numero = String.valueOf(i + 1);
                fecha_factura = Reloj.formatearFecha_ddMMyyyy(factura.getFecha());
                numero_factura = String.valueOf(factura.getNumero());
                numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
                estado = factura.getCondicionAbreviada();
                nitci_cliente = "0";
                nombrerazonsocial_cliente = CONDICION_ANULADA;
                importe_venta = "0";
                importe_iceiehdtasas = "0";
                exportaciones_operacionesexcentas = "0";
                ventas_gravadastasacero = "0";
                subtotal = "0";
                descuentos_bonificaciones_rebajas = "0";
                importebase_debitofiscal = "0";
                debitofiscal = "0";
                codigo_control = "0";
            }

            String fila = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", especificacion, numero, fecha_factura,
                    numero_factura, numero_autorizacion, estado, nitci_cliente, nombrerazonsocial_cliente, importe_venta,
                    importe_iceiehdtasas, exportaciones_operacionesexcentas, ventas_gravadastasacero, subtotal, descuentos_bonificaciones_rebajas,
                    importebase_debitofiscal, debitofiscal, codigo_control);

            rows.add(fila);
            rows.add("\r\n");
        }

        return rows;
    }
}
