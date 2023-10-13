/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.servlets;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facades.TurnoCajaFacade;
import java.io.IOException;
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
@WebServlet(name = "ArqueoCaja", urlPatterns = {"/reportes/ArqueoCaja"})
public class ArqueoCaja extends HttpServlet {

    private static final String TIPO_CONTENIDO = "application/pdf";

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
    private static final Font NEGRITA_GRANDE = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font ESPACIO = FontFactory.getFont(FontFactory.HELVETICA, 4, Font.NORMAL, BaseColor.BLACK);

    private static final int ANCHO = 227;
    private static final int ALTO = 340;
    private static final int MARGEN_IZQUIERDO = -25;
    private static final int MARGEN_DERECHO = -25;
    private static final int MARGEN_SUPERIOR = 0;
    private static final int MARGEN_INFERIOR = 0;

    @EJB
    TurnoCajaFacade turnoCajaFacade;
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
        Integer id_turnocaja = (Integer) request.getSession().getAttribute("id_turnocaja");

        if (id_turnocaja != null) {
            TurnoCaja turnoCaja = turnoCajaFacade.find(id_turnocaja);

            try {
                response.setContentType(TIPO_CONTENIDO);

                Document document = new Document(new Rectangle(ANCHO, ALTO), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();

                document.add(encabezado(turnoCaja));
                document.add(cuerpo(turnoCaja));
                document.add(pie(turnoCaja));

                document.close();
            } catch (IOException | DocumentException ex) {

            }
        } else {
            try {
                response.setContentType(TIPO_CONTENIDO);

                Document document = new Document(new Rectangle(ANCHO, ALTO), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph(" "));
                document.close();
            } catch (IOException | DocumentException ex) {

            }
        }
    }

    public PdfPTable encabezado(TurnoCaja turnoCaja) throws BadElementException, IOException {
        PdfPTable table = new PdfPTable(6);

        String realPath = getServletContext().getRealPath("/resources/images/logo.png");
        Image image = Image.getInstance(realPath);
        image.scalePercent(25);
        image.setAlignment(Image.ALIGN_LEFT);
        PdfPCell cell = new PdfPCell();
        cell.addElement(image);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getCaja().getCampus().getInstitucion().getRazon_social(), NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getCaja().getCampus().getSucursal(), NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getCaja().getCampus().getCiudad(), NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ARQUEO DE CAJA", TITULO));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);

        return table;
    }

    public PdfPTable cuerpo(TurnoCaja turnoCaja) {
        PdfPTable table = new PdfPTable(4);

        PdfPCell cell = new PdfPCell(new Phrase("Campus:", NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getCaja().getCampus().getNombre(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Caja:", NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getCaja().getNombre(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Inicio:", NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_ddMMyyyyHHmm(turnoCaja.getInicio()), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fin:", NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Reloj.formatearFecha_ddMMyyyyHHmm(turnoCaja.getFin()), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Monto (Bs.):", NORMAL));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        double monto = facturaFacade.montoFacturas(turnoCaja.getId_turnocaja());
        cell = new PdfPCell(new Phrase(String.valueOf(Redondeo.redondear_HALFUP(monto, 2)), NEGRITA_GRANDE));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        table.addCell(cell);

        return table;
    }

    public PdfPTable pie(TurnoCaja turnoCaja) throws BadElementException {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(turnoCaja.getEmpleado().iniciales(), NORMAL));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        return table;
    }

}
