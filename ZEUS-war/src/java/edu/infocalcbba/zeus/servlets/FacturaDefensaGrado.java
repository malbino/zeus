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
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.infocalcbba.argos.entities.DefensaGrado;
import edu.infocalcbba.argos.facades.DefensaGradoFacade;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Detalle;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.facades.DetalleFacade;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facturacion.converters.NumberToLetterConverter;
import java.io.IOException;
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
@WebServlet(name = "FacturaDefensaGrado", urlPatterns = {"/reportes/FacturaDefensaGrado"})
public class FacturaDefensaGrado extends HttpServlet {

    private static final String TIPO_CONTENIDO = "application/pdf";

    private static final int NRO_MAX_CAR_FILA = 15;
    private static final int ANCHO = 227;
    // 28 filas
    private static final int ALTO_MINIMO = 680;
    private static final int ALTO_FILA = 15;
    private static final int MARGEN_IZQUIERDO = -25;
    private static final int MARGEN_DERECHO = -25;
    private static final int MARGEN_SUPERIOR = 0;
    private static final int MARGEN_INFERIOR = 0;

    Font titulo;
    Font normal;
    Font leyenda1;
    Font leyenda2;

    @EJB
    FacturaFacade facturaFacade;
    @EJB
    DetalleFacade detalleFacade;
    @EJB
    DefensaGradoFacade defensaGradoFacade;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarPDF(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.generarPDF(request, response);
    }

    public int nroFilas(Factura factura) {
        int filas_detalles = 0;

        for (Detalle detalle : factura.getDetalles()) {
            double filas_detalle = (double) detalle.getConcepto().length() / NRO_MAX_CAR_FILA;
            filas_detalles += Redondeo.redondear_UP(filas_detalle, 0);
        }

        return filas_detalles;
    }

    public void generarPDF(HttpServletRequest request, HttpServletResponse response) {
        Integer id_factura = (Integer) request.getSession().getAttribute("id_factura");
        Integer id_defensagrado = (Integer) request.getSession().getAttribute("id_defensagrado");

        if (id_factura != null && id_defensagrado != null) {
            Factura factura = facturaFacade.find(id_factura);
            DefensaGrado defensagrado = defensaGradoFacade.find(id_defensagrado);

            try {
                response.setContentType(TIPO_CONTENIDO);

                float largo = ALTO_MINIMO + (ALTO_FILA * nroFilas(factura));
                Document document = new Document(new Rectangle(ANCHO, largo), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();

                String realPath = getServletContext().getRealPath("/resources/fonts/ARIALNB.TTF");
                FontFactory.register(realPath, "Arial Narrow Bold");
                titulo = FontFactory.getFont("Arial Narrow Bold", 16, Font.BOLD, BaseColor.BLACK);
                normal = FontFactory.getFont("Arial Narrow Bold", 11, Font.NORMAL, BaseColor.BLACK);
                leyenda1 = FontFactory.getFont("Arial Narrow Bold", 9, Font.BOLD, BaseColor.BLACK);
                leyenda2 = FontFactory.getFont("Arial Narrow Bold", 8, Font.NORMAL, BaseColor.BLACK);

                document.add(encabezado_factura(factura));
                document.add(titulo_factura(factura, defensagrado));
                document.add(detalle_factura(factura));
                document.add(pie_factura(factura));

                document.newPage();

                document.add(encabezado_coprobante(factura));
                document.add(titulo_comprobante(factura, defensagrado));
                document.add(detalle_comprobante(factura));
                document.add(pie_coprobante(factura));

                document.close();
            } catch (IOException | DocumentException ex) {

            }
        } else {
            try {
                response.setContentType(TIPO_CONTENIDO);

                Document document = new Document(new Rectangle(ANCHO, ALTO_MINIMO), MARGEN_IZQUIERDO, MARGEN_DERECHO, MARGEN_SUPERIOR, MARGEN_INFERIOR);
                PdfWriter.getInstance(document, response.getOutputStream());
                document.open();
                document.add(new Paragraph(" "));
                document.close();
            } catch (IOException | DocumentException ex) {

            }
        }
    }

    public PdfPTable encabezado_factura(Factura factura) throws BadElementException, IOException {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getInstitucion().getRazon_social(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getSucursal(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getDireccion(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.valueOf(factura.getDosificacion().getCampus().getTelefono()), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getCiudad(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FACTURA", titulo));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(1);
        cell.setPaddingBottom(15);
        table.addCell(cell);

        return table;
    }

    public PdfPTable titulo_factura(Factura factura, DefensaGrado defensaGrado) {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("NIT: " + factura.getDosificacion().getCampus().getInstitucion().getNit(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FACTURA Nº " + factura.getNumero(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("AUTORIZACIÓN Nº " + factura.getDosificacion().getNumero_autorizacion(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(0.8f);
        cell.setPaddingBottom(10);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getInstitucion().getActividad_economica(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(10);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha: " + Reloj.formatearFecha_ddMMyyyy(factura.getFecha()) + "  Hora: " + Reloj.formatearFecha_HHmm(factura.getFecha()), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("NIT/CI: " + factura.getCliente().getNit_ci(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Señor(es): " + factura.getCliente().getNombre_razonsocial(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Egresado: " + defensaGrado.getEgresado().toString_Estudiante(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Convocatoria: " + defensaGrado.getConvocatoria().toString(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Carrera: " + defensaGrado.getEgresado().toString_Carrera(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Modalidad: " + defensaGrado.getModalidad(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(0.8f);
        cell.setPaddingBottom(10);
        table.addCell(cell);

        return table;
    }

    public PdfPTable detalle_factura(Factura factura) {
        PdfPTable table = new PdfPTable(20);

        PdfPCell cell = new PdfPCell(new Phrase("CANT", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setPaddingTop(10);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DETALLE", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(9);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("UNIT", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTO", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(4);
        table.addCell(cell);

        List<Detalle> detalles = detalleFacade.listaDetalles_Capacitacion(factura.getId_factura());
        for (Detalle detalle : detalles) {

            cell = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(detalle.getConcepto(), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setColspan(9);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getUnitario()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setColspan(4);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("DESCTO TOTAL", normal));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(17);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("0.00", normal));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL Bs.", normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(15);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getMonto()), normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("EFECTIVO Bs.", normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(15);
        table.addCell(cell);

        if (factura.getEfectivo() != null) {
            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getEfectivo()), normal));
        } else {
            cell = new PdfPCell(new Phrase(" ", normal));
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CAMBIO Bs.", normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(15);
        table.addCell(cell);

        if (factura.getCambio() != null) {
            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(factura.getCambio()), normal));
        } else {
            cell = new PdfPCell(new Phrase(" ", normal));
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SON: " + NumberToLetterConverter.convertNumberToLetter(factura.getMonto()), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(0.8f);
        cell.setPaddingBottom(10);
        cell.setColspan(20);
        table.addCell(cell);

        return table;
    }

    public PdfPTable pie_factura(Factura factura) throws BadElementException {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("CODIGO DE CONTROL: " + factura.getCodigo_control(), normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setPaddingTop(10);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FECHA LIMITE DE EMISIÓN: " + factura.getDosificacion().getFlimite_emision_ddMMyyyy(), normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(cell);

        String nit_emisor = String.valueOf(factura.getDosificacion().getCampus().getInstitucion().getNit());
        String numero_factura = String.valueOf(factura.getNumero());
        String numero_autorizacion = String.valueOf(factura.getDosificacion().getNumero_autorizacion());
        String fecha_emision = Reloj.formatearFecha_ddMMyyyy(factura.getFecha());
        String total = Redondeo.formatear_csm(factura.getMonto());
        String base_creditofiscal = Redondeo.formatear_csm(factura.getMonto());
        String codigo_control = factura.getCodigo_control();
        String nitci_comprador = String.valueOf(factura.getCliente().getNit_ci());
        String importe_ice = "0";
        String importe_nograbadas = "0";
        String importe_nocreditofiscal = "0";
        String descuentos_bonificaciones_rebajas = "0";

        String qr = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", nit_emisor, numero_factura, numero_autorizacion,
                fecha_emision, total, base_creditofiscal, codigo_control, nitci_comprador, importe_ice, importe_nograbadas,
                importe_nocreditofiscal, descuentos_bonificaciones_rebajas);
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(qr, 1, 1, null);
        Image image = barcodeQRCode.getImage();
        image.scalePercent(200);
        cell = new PdfPCell(image);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("\"ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAÍS.", leyenda1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("EL USO ILÍCITO DE ÉSTA SERÁ SANCIONADO DE ACUERDO A LEY.\"", leyenda1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("\"" + factura.getDosificacion().getLeyenda() + "\"", leyenda2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getTurnocaja().getEmpleado().iniciales(), normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        return table;
    }

    public PdfPTable encabezado_coprobante(Factura factura) throws BadElementException, IOException {
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

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getInstitucion().getRazon_social(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getSucursal(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getDosificacion().getCampus().getCiudad(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("COMPROBANTE DE PAGO", titulo));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(1);
        cell.setPaddingBottom(15);
        cell.setColspan(6);
        table.addCell(cell);

        return table;
    }

    public PdfPTable titulo_comprobante(Factura factura, DefensaGrado defensaGrado) {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("COMPROBANTE Nº " + factura.getNumero(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fecha: " + Reloj.formatearFecha_ddMMyyyy(factura.getFecha()), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Egresado: " + defensaGrado.getEgresado().toString_Estudiante(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Convocatoria: " + defensaGrado.getConvocatoria().toString(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Carrera: " + defensaGrado.getEgresado().toString_Carrera(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Modalidad: " + defensaGrado.getModalidad(), normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(0.8f);
        cell.setPaddingBottom(10);
        table.addCell(cell);

        return table;
    }

    public PdfPTable detalle_comprobante(Factura factura) {
        PdfPTable table = new PdfPTable(20);

        PdfPCell cell = new PdfPCell(new Phrase("CANT", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setPaddingTop(10);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DETALLE", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(9);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("UNIT", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTO", normal));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        cell.setColspan(4);
        table.addCell(cell);

        for (Detalle detalle : factura.getDetalles()) {

            cell = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(detalle.getConcepto(), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setColspan(9);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getUnitario()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(Redondeo.formatear_csm(detalle.getTotal()), normal));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cell.setColspan(4);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase(" ", normal));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(0.8f);
        cell.setColspan(20);
        table.addCell(cell);

        return table;
    }

    public PdfPTable pie_coprobante(Factura factura) throws BadElementException {
        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell(new Phrase("\"Este comprobante de pago es intransferible, exclusivo para fines institucionales y no valido para credito fiscal.\"", leyenda1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(factura.getTurnocaja().getEmpleado().iniciales(), normal));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table.addCell(cell);

        return table;
    }

}
