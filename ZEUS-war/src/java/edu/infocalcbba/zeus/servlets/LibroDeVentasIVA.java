/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.servlets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import java.io.IOException;
import java.util.Date;
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
@WebServlet(name = "LibroDeVentasIVA", urlPatterns = {"/reportes/LibroDeVentasIVA"})
public class LibroDeVentasIVA extends HttpServlet {

    private static final String CONTENIDO_PDF = "application/pdf";

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
    private static final Font ESPACIO = FontFactory.getFont(FontFactory.HELVETICA, 2, Font.NORMAL, BaseColor.BLACK);

    private static final int MARGEN_IZQUIERDO = -80;
    private static final int MARGEN_DERECHO = -80;
    private static final int MARGEN_SUPERIOR = 20;
    private static final int MARGEN_INFERIOR = 20;

    private static final int FACTURAS_HOJA = 40;

    private static final double PROPORCION_IVA = 0.13;
    private static final String CONDICION_VALIDA = "VALIDA";
    private static final String CONDICION_ANULADA = "ANULADA";

    @EJB
    CampusFacade campusFacade;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    ParametroFacade parametroFacade;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarPDF(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarPDF(request, response);
    }

    public void generarPDF(HttpServletRequest request, HttpServletResponse response) {
        Integer id_campus = (Integer) request.getSession().getAttribute("id_campus");
        Date desde = (Date) request.getSession().getAttribute("desde");
        Date hasta = (Date) request.getSession().getAttribute("hasta");

        if (id_campus != null && desde != null && hasta != null) {
            Campus campus = campusFacade.find(id_campus);

            try {
                response.setContentType(CONTENIDO_PDF);

                Document document = new Document(PageSize.A4.rotate(), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());

                generarDocumento(document, campus, desde, hasta);
            } catch (IOException | DocumentException ex) {

            }
        } else {
            try {
                response.setContentType(CONTENIDO_PDF);

                Document document = new Document(PageSize.A4.rotate(), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());

                document.open();

                document.add(new Paragraph("Error al generar el reporte."));

                document.close();
            } catch (DocumentException | IOException ex) {

            }
        }

    }

    public void generarDocumento(Document document, Campus campus, Date desde, Date hasta) throws DocumentException {
        document.open();

        List<Factura> facturas = facturaFacade.listaFacturas(campus.getId_campus(), desde, hasta);
        double cantidadhojas_sinredondear = (double) facturas.size() / FACTURAS_HOJA;
        int cantidadhojas = (int) Redondeo.redondear_UP(cantidadhojas_sinredondear, 0);

        double totalgeneral1 = 0;
        double totalgeneral2 = 0;
        double totalgeneral3 = 0;

        if (cantidadhojas > 0) {
            for (int i = 0; i < cantidadhojas; i++) {
                PdfPTable table = new PdfPTable(70);

                //titulo
                PdfPCell cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(60);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("FOLIO", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(i + 1), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("LIBRO DE VENTAS IVA", TITULO));
                cell.setColspan(70);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PERIODO FISCAL " + Reloj.formatearFecha_MM(desde) + "-" + Reloj.formatearFecha_yyyy(desde), NEGRITA));
                cell.setColspan(70);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(70);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL:", NEGRITA));
                cell.setColspan(11);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(campus.getInstitucion().getRazon_social(), NEGRITA));
                cell.setColspan(44);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("NIT:", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(campus.getInstitucion().getNit()), NEGRITA));
                cell.setColspan(10);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nº SUCURSAL:", NEGRITA));
                cell.setColspan(11);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(campus.getNombre()), NEGRITA));
                cell.setColspan(22);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("DIRECCIÓN:", NEGRITA));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(campus.getDireccion()), NEGRITA));
                cell.setColspan(31);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(70);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("FECHA", NEGRITA));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(39);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("CIFRA EN BOLIVIANOS", NEGRITA));
                cell.setColspan(25);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("D", NEGRITA));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("M", NEGRITA));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("A", NEGRITA));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nº DEL NIT/CI DEL COMPRADOR", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL DEL COMPRADOR", NEGRITA));
                cell.setColspan(17);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nº DE FACTURA", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nº DE AUTORIZACIÓN", NEGRITA));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("CÓDIGO DE CONTROL", NEGRITA));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTAL FACTURA (A)", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTAL I.C.E. (B)", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("IMPORTES EXENTOS (C)", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("IMPORTES NETO (A-B-C)", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("DÉBITO FISCAL I.V.A.", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);

                double totalparcial1 = 0;
                double totalparcial2 = 0;
                double totalparcial3 = 0;

                for (int j = i * FACTURAS_HOJA; j < (i * FACTURAS_HOJA) + FACTURAS_HOJA && j < facturas.size(); j++) {
                    Factura factura = facturas.get(j);

                    if (factura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_dd(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_MM(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_yyyy(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(String.valueOf(factura.getCliente().getNit_ci()), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(factura.getCliente().getNombre_razonsocial(), NORMAL));
                        cell.setColspan(17);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(String.valueOf(factura.getNumero()), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(String.valueOf(factura.getDosificacion().getNumero_autorizacion()), NORMAL));
                        cell.setColspan(6);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(factura.getCodigo_control(), NORMAL));
                        cell.setColspan(6);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                        totalparcial1 += factura.getMonto();

                        cell = new PdfPCell(new Phrase("", NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("", NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                        totalparcial2 += factura.getMonto();

                        double debitofiscalIVA = factura.getMonto() * PROPORCION_IVA;
                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debitofiscalIVA), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                        totalparcial3 += debitofiscalIVA;
                    } else if (factura.getCondicion().compareTo(CONDICION_ANULADA) == 0) {
                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_dd(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_MM(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_yyyy(factura.getFecha()), NORMAL));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("0", NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(CONDICION_ANULADA, NORMAL));
                        cell.setColspan(17);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(String.valueOf(factura.getNumero()), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(String.valueOf(factura.getDosificacion().getNumero_autorizacion()), NORMAL));
                        cell.setColspan(6);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(factura.getCodigo_control(), NORMAL));
                        cell.setColspan(6);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("", NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("", NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                    }

                }

                cell = new PdfPCell(new Phrase(parametroFacade.find(ParametroFacade.CI_RESP_LIBROVENTASIVA).getValor(), NEGRITA));
                cell.setColspan(11);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(parametroFacade.find(ParametroFacade.NOM_RESP_LIBROVENTASIVA).getValor(), NEGRITA));
                cell.setColspan(22);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTALES PARCIALES:", NEGRITA));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalparcial1), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                totalgeneral1 += totalparcial1;

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalparcial2), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                totalgeneral2 += totalparcial2;

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalparcial3), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                totalgeneral3 += totalparcial3;

                cell = new PdfPCell(new Phrase("C.I.", NEGRITA));
                cell.setColspan(11);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("NOMBRE COMPLETO DEL RESPONSABLE", NEGRITA));
                cell.setColspan(22);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTALES GENERALES:", NEGRITA));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral1), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral2), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral3), NEGRITA));
                cell.setColspan(5);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                document.add(table);
                document.newPage();
            }

        } else {
            PdfPTable table = new PdfPTable(70);

            //titulo
            PdfPCell cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(60);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("FOLIO", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("1", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("LIBRO DE VENTAS IVA", TITULO));
            cell.setColspan(70);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("PERIODO FISCAL " + Reloj.formatearFecha_MM(desde) + "-" + Reloj.formatearFecha_yyyy(desde), NEGRITA));
            cell.setColspan(70);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", ESPACIO));
            cell.setColspan(70);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL:", NEGRITA));
            cell.setColspan(11);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(campus.getInstitucion().getRazon_social(), NEGRITA));
            cell.setColspan(44);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("NIT:", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(campus.getInstitucion().getNit()), NEGRITA));
            cell.setColspan(10);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nº SUCURSAL:", NEGRITA));
            cell.setColspan(11);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(campus.getNombre()), NEGRITA));
            cell.setColspan(22);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DIRECCIÓN:", NEGRITA));
            cell.setColspan(6);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(campus.getDireccion()), NEGRITA));
            cell.setColspan(31);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", ESPACIO));
            cell.setColspan(70);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("FECHA", NEGRITA));
            cell.setColspan(6);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(39);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("CIFRA EN BOLIVIANOS", NEGRITA));
            cell.setColspan(25);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("D", NEGRITA));
            cell.setColspan(2);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("M", NEGRITA));
            cell.setColspan(2);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("A", NEGRITA));
            cell.setColspan(2);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nº DEL NIT/CI DEL COMPRADOR", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL DEL COMPRADOR", NEGRITA));
            cell.setColspan(17);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nº DE FACTURA", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nº DE AUTORIZACIÓN", NEGRITA));
            cell.setColspan(6);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("CÓDIGO DE CONTROL", NEGRITA));
            cell.setColspan(6);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("TOTAL FACTURA (A)", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("TOTAL I.C.E. (B)", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("IMPORTES EXENTOS (C)", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("IMPORTES NETO (A-B-C)", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("DÉBITO FISCAL I.V.A.", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("5931459", NEGRITA));
            cell.setColspan(11);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("ALEJANDRA CAMACHO ZELADA", NEGRITA));
            cell.setColspan(22);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("TOTALES PARCIALES:", NEGRITA));
            cell.setColspan(12);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C.I.", NEGRITA));
            cell.setColspan(11);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("NOMBRE COMPLETO DEL RESPONSABLE", NEGRITA));
            cell.setColspan(22);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("TOTALES GENERALES:", NEGRITA));
            cell.setColspan(12);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NEGRITA));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            document.add(table);
            document.newPage();
        }

        document.close();
    }
}
