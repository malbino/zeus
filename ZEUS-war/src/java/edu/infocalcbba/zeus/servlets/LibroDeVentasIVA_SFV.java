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
@WebServlet(name = "LibroDeVentasIVA_SFV", urlPatterns = {"/reportes/LibroDeVentasIVA_SFV"})
public class LibroDeVentasIVA_SFV extends HttpServlet {

    private static final String CONTENIDO_PDF = "application/pdf";

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
    private static final Font ESPACIO = FontFactory.getFont(FontFactory.HELVETICA, 2, Font.NORMAL, BaseColor.BLACK);

    private static final int MARGEN_IZQUIERDO = -80;
    private static final int MARGEN_DERECHO = -80;
    private static final int MARGEN_SUPERIOR = 20;
    private static final int MARGEN_INFERIOR = 20;

    private static final double PROPORCION_IVA = 0.13;
    private static final String CONDICION_VALIDA = "VALIDA";
    private static final String CONDICION_ANULADA = "ANULADA";

    @EJB
    CampusFacade campusFacade;
    @EJB
    FacturaFacade facturaFacade;

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

                document.open();

                document.add(titulo(campus, desde, hasta));
                document.add(cuerpo(campus, desde, hasta));

                document.close();
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

    public PdfPTable titulo(Campus campus, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(70);

        PdfPCell cell = new PdfPCell(new Phrase("LIBRO DE VENTAS IVA", TITULO));
        cell.setColspan(70);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setColspan(70);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PERIODO:", NEGRITA));
        cell.setColspan(8);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NEGRITA));
        cell.setColspan(62);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("AÑO:", NEGRITA));
        cell.setColspan(8);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_yyyy(desde), NORMAL));
        cell.setColspan(6);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("MES:", NEGRITA));
        cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_MM(desde), NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NEGRITA));
        cell.setColspan(50);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setColspan(70);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL:", NEGRITA));
        cell.setColspan(8);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(campus.getInstitucion().getRazon_social(), NORMAL));
        cell.setColspan(36);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NIT:", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.valueOf(campus.getInstitucion().getNit()), NORMAL));
        cell.setColspan(8);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NEGRITA));
        cell.setColspan(14);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    public PdfPTable cuerpo(Campus campus, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(70);

        PdfPCell cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setColspan(70);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NRO", NEGRITA));
        cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FECHA FACTURA", NEGRITA));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Nº DE FACTURA", NEGRITA));
        cell.setColspan(3);
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

        cell = new PdfPCell(new Phrase("EST", NEGRITA));
        cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NIT/CI CLIENTE", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NOMBRE O RAZÓN SOCIAL", NEGRITA));
        cell.setColspan(12);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IMPORTE TOTAL DE VENTA\nA", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IMPORTE ICE/IEHD/TASAS\nB", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("EXP Y OPE EXENTAS\n\nC", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("VENTAS GRAVADAS A TASA CERO\nD", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTOTAL\n\n\nE=A-B-C-D", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DES, BON Y REB OTORGADAS\nF", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IMP BASE PARA DÉBITO FISCAL\nG=E-F", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DÉBITO FISCAL\n\nH=G*13%", NEGRITA));
        cell.setColspan(4);
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

        double sumatoria_A = 0;
        double sumatoria_E = 0;
        double sumatoria_G = 0;
        double sumatoria_H = 0;

        List<Factura> facturas = facturaFacade.listaFacturas(campus.getId_campus(), desde, hasta);
        for (int i = 0; i < facturas.size(); i++) {
            Factura factura = facturas.get(i);

            if (factura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
                cell = new PdfPCell(new Phrase(String.valueOf(i + 1), NORMAL));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Reloj.formatearFecha_ddMMyyyy(factura.getFecha()), NORMAL));
                cell.setColspan(3);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(factura.getNumero()), NORMAL));
                cell.setColspan(3);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(factura.getDosificacion().getNumero_autorizacion()), NORMAL));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(factura.getCondicionAbreviada(), NORMAL));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(factura.getCliente().getNit_ci()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(factura.getCliente().getNombre_razonsocial(), NORMAL));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_A += factura.getMonto();

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_E += factura.getMonto();

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_G += factura.getMonto();

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto() * PROPORCION_IVA), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_H += factura.getMonto() * PROPORCION_IVA;

                cell = new PdfPCell(new Phrase(factura.getCodigo_control(), NORMAL));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
            } else if (factura.getCondicion().compareTo(CONDICION_ANULADA) == 0) {
                cell = new PdfPCell(new Phrase(String.valueOf(i + 1), NORMAL));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Reloj.formatearFecha_ddMMyyyy(factura.getFecha()), NORMAL));
                cell.setColspan(3);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(factura.getNumero()), NORMAL));
                cell.setColspan(3);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(factura.getDosificacion().getNumero_autorizacion()), NORMAL));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(factura.getCondicionAbreviada(), NORMAL));
                cell.setColspan(2);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("0", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(CONDICION_ANULADA, NORMAL));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_A += 0;

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_E += 0;

                cell = new PdfPCell(new Phrase("", NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_G += 0;

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(0 * PROPORCION_IVA), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table.addCell(cell);
                sumatoria_H += 0 * PROPORCION_IVA;

                cell = new PdfPCell(new Phrase(factura.getCodigo_control(), NORMAL));
                cell.setColspan(6);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setColspan(70);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTALES", NEGRITA));
        cell.setColspan(32);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(sumatoria_A), NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(sumatoria_E), NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(sumatoria_G), NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(sumatoria_H), NORMAL));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NORMAL));
        cell.setColspan(6);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }
}
