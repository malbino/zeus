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
import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.facades.CampusFacade;
import edu.infocalcbba.argos.facades.CarreraFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.facades.DetalleFacade;
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
@WebServlet(name = "HojaColectaIngresos", urlPatterns = {"/reportes/HojaColectaIngresos"})
public class HojaColectaIngresos extends HttpServlet {

    private static final String CONTENIDO_PDF = "application/pdf";

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font NEGRITA = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
    private static final Font ESPACIO = FontFactory.getFont(FontFactory.HELVETICA, 2, Font.NORMAL, BaseColor.BLACK);

    private static final int MARGEN_IZQUIERDO = -40;
    private static final int MARGEN_DERECHO = -40;
    private static final int MARGEN_SUPERIOR = 20;
    private static final int MARGEN_INFERIOR = 20;

    private static final double PROPORCION_IVA = 0.13;

    @EJB
    CampusFacade campusFacade;
    @EJB
    CarreraFacade carreraFacade;
    @EJB
    DetalleFacade detalleFacade;

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

                Document document = new Document(PageSize.A4, MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());

                document.open();

                document.add(encabezado());
                document.add(titulo(campus, desde, hasta));
                document.add(cuerpo(campus, desde, hasta));

                document.close();
            } catch (IOException | DocumentException ex) {

            }
        } else {
            try {
                response.setContentType(CONTENIDO_PDF);

                Document document = new Document(PageSize.A4, MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
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

    public PdfPTable titulo(Campus campus, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("HOJA DE COLECTA/INGRESOS", TITULO));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUCURSAL: " + campus.getNombre(), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DESDE: " + Reloj.formatearFecha_ddMMyyyy(desde) + " HASTA: " + Reloj.formatearFecha_ddMMyyyy(hasta), NEGRITA));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        return table;
    }

    public PdfPTable cuerpo(Campus campus, Date desde, Date hasta) {
        PdfPTable table = new PdfPTable(40);

        PdfPCell cell = new PdfPCell(new Phrase("CONCEPTO", NEGRITA));
        cell.setColspan(12);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ESTUDIANTE/CLIENTE", NEGRITA));
        cell.setColspan(12);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NRO.\n FACTURA", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL \n INGRESOS\n Bs.", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("13% IVA", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL\n INGRESO \n NETO(Bs.)", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(" ", ESPACIO));
        cell.setColspan(40);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double totalgeneral_totalingreso = 0;
        double totalgeneral_debitofiscal = 0;
        double totalgeneral_ingresoneto = 0;

        cell = new PdfPCell(new Phrase("FORMACIÓN", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        List<Carrera> carreras1 = carreraFacade.listaCarreras();
        double total_totalingreso1 = 0;
        double total_debitofiscal1 = 0;
        double total_ingresoneto1 = 0;
        for (int i = 0; i < carreras1.size(); i++) {
            Carrera carrera = carreras1.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_Formacion(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso1 = 0;
                double subtotal_debitofiscal1 = 0;
                double subtotal_ingresoneto1 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getConcepto(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso1 += total_ingreso;
                    total_totalingreso1 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal1 += debito_fiscal;
                    total_debitofiscal1 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto1 += ingreso_neto;
                    total_ingresoneto1 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto1), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }
        
        cell = new PdfPCell(new Phrase("FORMACIÓN GESTIONES ANTERIORES", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        List<Carrera> carreras2 = carreraFacade.listaCarreras();
        double total_totalingreso7 = 0;
        double total_debitofiscal7 = 0;
        double total_ingresoneto7 = 0;
        for (int i = 0; i < carreras2.size(); i++) {
            Carrera carrera = carreras2.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_Formacion_GestionesAnteriores(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso7 = 0;
                double subtotal_debitofiscal7 = 0;
                double subtotal_ingresoneto7 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getConcepto() + " - " + detalle.getCuota().getInscrito().getGestionacademica().getGestion(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso7 += total_ingreso;
                    total_totalingreso7 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal7 += debito_fiscal;
                    total_debitofiscal7 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto7 += ingreso_neto;
                    total_ingresoneto7 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto7), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase("CAPACITACIÓN", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double total_totalingreso2 = 0;
        double total_debitofiscal2 = 0;
        double total_ingresoneto2 = 0;
        for (int i = 0; i < carreras2.size(); i++) {
            Carrera carrera = carreras2.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_Capacitacion(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso2 = 0;
                double subtotal_debitofiscal2 = 0;
                double subtotal_ingresoneto2 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getPago().getPlanpago().getInscrito().getGrupo().getCurso().getNombre(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso2 += total_ingreso;
                    total_totalingreso2 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal2 += debito_fiscal;
                    total_debitofiscal2 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto2 += ingreso_neto;
                    total_ingresoneto2 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto2), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }
        
        cell = new PdfPCell(new Phrase("CAPACITACIÓN GESTIONES ANTERIORES", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double total_totalingreso8 = 0;
        double total_debitofiscal8 = 0;
        double total_ingresoneto8 = 0;
        for (int i = 0; i < carreras2.size(); i++) {
            Carrera carrera = carreras2.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_Capacitacion_GestionesAnteriores(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso8 = 0;
                double subtotal_debitofiscal8 = 0;
                double subtotal_ingresoneto8 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getPago().getPlanpago().getInscrito().getGrupo().getCurso().getNombre() + " - " + detalle.getPago().getPlanpago().getInscrito().getGestion().getCodigo(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso8 += total_ingreso;
                    total_totalingreso8 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal8 += debito_fiscal;
                    total_debitofiscal8 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto8 += ingreso_neto;
                    total_ingresoneto8 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto8), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase("TRAMITES Y CERTIFICACIONES", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double total_totalingreso3 = 0;
        double total_debitofiscal3 = 0;
        double total_ingresoneto3 = 0;
        for (int i = 0; i < carreras2.size(); i++) {
            Carrera carrera = carreras2.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_TramCert(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso3 = 0;
                double subtotal_debitofiscal3 = 0;
                double subtotal_ingresoneto3 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getConcepto(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso3 += total_ingreso;
                    total_totalingreso3 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal3 += debito_fiscal;
                    total_debitofiscal3 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto3 += ingreso_neto;
                    total_ingresoneto3 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase("DEFENSA DE GRADO", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double total_totalingreso5 = 0;
        double total_debitofiscal5 = 0;
        double total_ingresoneto5 = 0;
        for (int i = 0; i < carreras2.size(); i++) {
            Carrera carrera = carreras2.get(i);
            List<Detalle> detalles = detalleFacade.listaDetalles_DefensaGrado(campus.getId_campus(), carrera.getId_carrera(), desde, hasta);

            if (detalles.size() > 0) {
                cell = new PdfPCell(new Phrase(carrera.toString(), NEGRITA));
                cell.setColspan(40);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double subtotal_totalingreso3 = 0;
                double subtotal_debitofiscal3 = 0;
                double subtotal_ingresoneto3 = 0;
                for (int j = 0; j < detalles.size(); j++) {
                    Detalle detalle = detalles.get(j);

                    cell = new PdfPCell(new Phrase(detalle.getConcepto(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                    cell.setColspan(12);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double total_ingreso = detalle.getTotal();
                    subtotal_totalingreso3 += total_ingreso;
                    total_totalingreso5 += total_ingreso;
                    totalgeneral_totalingreso += total_ingreso;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double debito_fiscal = total_ingreso * PROPORCION_IVA;
                    subtotal_debitofiscal3 += debito_fiscal;
                    total_debitofiscal5 += debito_fiscal;
                    totalgeneral_debitofiscal += debito_fiscal;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    double ingreso_neto = total_ingreso - debito_fiscal;
                    subtotal_ingresoneto3 += ingreso_neto;
                    total_ingresoneto5 += ingreso_neto;
                    totalgeneral_ingresoneto += ingreso_neto;
                    cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto3), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Total:", NEGRITA));
                cell.setColspan(24);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("", NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso5), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal5), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto5), NEGRITA));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", ESPACIO));
                cell.setColspan(40);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase("COBROS EXTERNOS", NEGRITA));
        cell.setColspan(40);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        double total_totalingreso6 = 0;
        double total_debitofiscal6 = 0;
        double total_ingresoneto6 = 0;

        List<Detalle> detalles2 = detalleFacade.listaDetalles_Externa(campus.getId_campus(), desde, hasta);
        if (detalles2.size() > 0) {

            double subtotal_totalingreso4 = 0;
            double subtotal_debitofiscal4 = 0;
            double subtotal_ingresoneto4 = 0;
            for (int j = 0; j < detalles2.size(); j++) {
                Detalle detalle = detalles2.get(j);

                cell = new PdfPCell(new Phrase(detalle.getConcepto(), NORMAL));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(detalle.getFactura().getCliente().getNombre_razonsocial(), NORMAL));
                cell.setColspan(12);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(detalle.getFactura().getNumero()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double total_ingreso = detalle.getTotal();
                subtotal_totalingreso4 += total_ingreso;
                total_totalingreso6 += total_ingreso;
                totalgeneral_totalingreso += total_ingreso;
                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double debito_fiscal = total_ingreso * PROPORCION_IVA;
                subtotal_debitofiscal4 += debito_fiscal;
                total_debitofiscal6 += debito_fiscal;
                totalgeneral_debitofiscal += debito_fiscal;
                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(debito_fiscal), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                double ingreso_neto = total_ingreso - debito_fiscal;
                subtotal_ingresoneto4 += ingreso_neto;
                total_ingresoneto6 += ingreso_neto;
                totalgeneral_ingresoneto += ingreso_neto;
                cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(ingreso_neto), NORMAL));
                cell.setColspan(4);
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase("SubTotal:", NEGRITA));
            cell.setColspan(24);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_totalingreso4), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_debitofiscal4), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(subtotal_ingresoneto4), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total:", NEGRITA));
            cell.setColspan(24);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_totalingreso6), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_debitofiscal6), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(total_ingresoneto6), NEGRITA));
            cell.setColspan(4);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(" ", ESPACIO));
            cell.setColspan(40);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("Total General:", NEGRITA));
        cell.setColspan(24);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral_totalingreso), NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral_debitofiscal), NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(totalgeneral_ingresoneto), NEGRITA));
        cell.setColspan(4);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);

        return table;
    }

}
