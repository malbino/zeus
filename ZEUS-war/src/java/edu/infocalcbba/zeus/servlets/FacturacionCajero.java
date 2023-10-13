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
import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.kardia.facades.EmpleadoFacade;
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
@WebServlet(name = "FacturacionCajero", urlPatterns = {"/reportes/FacturacionCajero"})
public class FacturacionCajero extends HttpServlet {

    private static final String CONTENIDO_PDF = "application/pdf";

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
    private static final Font ESPACIO = FontFactory.getFont(FontFactory.HELVETICA, 2, Font.NORMAL, BaseColor.BLACK);

    private static final int MARGEN_IZQUIERDO = -40;
    private static final int MARGEN_DERECHO = -40;
    private static final int MARGEN_SUPERIOR = 20;
    private static final int MARGEN_INFERIOR = 20;

    @EJB
    CampusFacade campusFacade;
    @EJB
    EmpleadoFacade empleadoFacade;
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
        Integer id_persona = (Integer) request.getSession().getAttribute("id_persona");
        Date desde = (Date) request.getSession().getAttribute("desde");
        Date hasta = (Date) request.getSession().getAttribute("hasta");

        if (id_campus != null && id_persona != null && desde != null && hasta != null) {
            Campus campus = campusFacade.find(id_campus);
            Empleado empleado = empleadoFacade.find(id_persona);

            try {
                response.setContentType(CONTENIDO_PDF);

                Document document = new Document(PageSize.A4, MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());

                document.open();

                document.add(encabezado());
                document.add(titulo(campus, empleado, desde, hasta));
                document.add(cuerpo(campus, empleado, desde, hasta));

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

    public PdfPTable encabezado() throws IOException {
        PdfPTable table = new PdfPTable(2);

        PdfPCell cell = new PdfPCell(new Phrase("Instituto Tecnológico Infocal Oruro", NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha: " + Reloj.getFecha_ddMMyyyy(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Oruro-Bolivia ", NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Hora: " + Reloj.getHora_HHmmss(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    public PdfPTable titulo(Campus campus, Empleado empleado, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("REPORTE FACTURACIÓN CAJERO", TITULO));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUCURSAL: " + campus.getNombre(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CAJERO: " + empleado.toString(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DESDE: " + Reloj.formatearFecha_ddMMyyyy(desde) + " HASTA: " + Reloj.formatearFecha_ddMMyyyy(hasta), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    public PdfPTable cuerpo(Campus campus, Empleado empleado, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(50);

        PdfPCell cell = new PdfPCell(new Phrase("CAJA", NEGRITA));
        cell.setColspan(5);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FECHA", NEGRITA));
        cell.setColspan(5);
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
        cell.setColspan(13);
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

        cell = new PdfPCell(new Phrase("TOTAL FACTURA", NEGRITA));
        cell.setColspan(5);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        List<Factura> facturas = facturaFacade.listaFacturas(campus.getId_campus(), empleado.getId_persona(), desde, hasta);

        double total = 0;
        for (Factura factura : facturas) {
            cell = new PdfPCell(new Phrase(factura.getTurnocaja().getCaja().getNombre(), NORMAL));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Reloj.formatearFecha_ddMMyyyy(factura.getFecha()), NORMAL));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(factura.getCliente().getNit_ci()), NORMAL));
            cell.setColspan(5);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(factura.getCliente().getNombre_razonsocial(), NORMAL));
            cell.setColspan(13);
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
            total += factura.getMonto();

        }

        cell = new PdfPCell(new Phrase("", NEGRITA));
        cell.setColspan(39);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL:", NEGRITA));
        cell.setColspan(6);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total), NEGRITA));
        cell.setColspan(5);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        return table;
    }

}
