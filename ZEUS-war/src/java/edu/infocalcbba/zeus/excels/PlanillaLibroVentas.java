/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.excels;

import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author tincho
 */
public class PlanillaLibroVentas {

    private static final double PROPORCION_IVA = 0.13;

    private static final String CONDICION_VALIDA = "VALIDA";
    private static final String CONDICION_ANULADA = "ANULADA";

    public void generarXLSX(List<Factura> facturas, String file) throws FileNotFoundException, IOException {
        Workbook wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("Hoja1");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Nº");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(1);
        cell.setCellValue("ESPECIFICACION");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(2);
        cell.setCellValue("FECHA DE LA FACTURA");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(3);
        cell.setCellValue("N° DE LA FACTURA");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(4);
        cell.setCellValue("CODIGO DE AUTORIZACION");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(5);
        cell.setCellValue("NIT / CI CLIENTE");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(6);
        cell.setCellValue("COMPLEMENTO");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(7);
        cell.setCellValue("NOMBRE O RAZON SOCIAL");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(8);
        cell.setCellValue("IMPORTE TOTAL DE LA VENTA");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(9);
        cell.setCellValue("IMPORTE ICE");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(10);
        cell.setCellValue("IMPORTE IEHD");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(11);
        cell.setCellValue("IMPORTE IPJ");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(12);
        cell.setCellValue("TASAS");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(13);
        cell.setCellValue("OTROS NO SUJETOS AL IVA");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(14);
        cell.setCellValue("EXPORTACIONES Y OPERACIONES EXENTAS");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(15);
        cell.setCellValue("VENTAS GRAVADAS A TASA CERO");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(16);
        cell.setCellValue("SUBTOTAL");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(17);
        cell.setCellValue("DESCUENTOS, BONIFICACIONES Y REBAJAS SUJETAS AL IVA");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(18);
        cell.setCellValue("IMPORTE GIFT CARD");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(19);
        cell.setCellValue("IMPORTE BASE PARA DEBITO FISCAL");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(20);
        cell.setCellValue("DEBITO FISCAL");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(21);
        cell.setCellValue("ESTADO");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(22);
        cell.setCellValue("CODIGO DE CONTROL");
        cell.setCellStyle(styles.get("general_center"));

        cell = row.createCell(23);
        cell.setCellValue("TIPO DE VENTA");
        cell.setCellStyle(styles.get("general_center"));

        for (int i = 0; i < facturas.size(); i++) {
            Factura factura = facturas.get(i);

            row = sheet.createRow(i + 1);

            cell = row.createCell(0);
            cell.setCellValue(i + 1);
            cell.setCellStyle(styles.get("number_center"));

            cell = row.createCell(1);
            cell.setCellValue(2);
            cell.setCellStyle(styles.get("number_center"));

            cell = row.createCell(2);
            cell.setCellValue(Reloj.formatearFecha_ddMMyyyy(factura.getFecha()));
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(3);
            cell.setCellValue(factura.getNumero());
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(4);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(factura.getDosificacion().getNumero_autorizacion());
            cell.setCellStyle(styles.get("number_center"));

            cell = row.createCell(5);
            cell.setCellValue(factura.getCliente().getNit_ci());
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(7);
            cell.setCellValue(factura.getCliente().getNombre_razonsocial());
            cell.setCellStyle(styles.get("general_center"));

            if (factura.getCondicion().compareTo(CONDICION_VALIDA) == 0) {
                cell = row.createCell(8);
                cell.setCellValue(factura.getMonto());
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(9);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(10);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(11);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(12);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(13);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(14);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(15);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(16);
                cell.setCellValue(factura.getMonto());
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(17);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(18);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(19);
                cell.setCellValue(factura.getMonto());
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(20);
                cell.setCellValue(factura.getMonto() * PROPORCION_IVA);
                cell.setCellStyle(styles.get("custom_center"));
            } else if (factura.getCondicion().compareTo(CONDICION_ANULADA) == 0) {
                cell = row.createCell(8);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(9);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(10);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(11);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(12);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(13);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(14);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(15);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(16);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(17);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(18);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("number_center"));

                cell = row.createCell(19);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("custom_center"));

                cell = row.createCell(20);
                cell.setCellValue(0);
                cell.setCellStyle(styles.get("custom_center"));
            }

            cell = row.createCell(21);
            cell.setCellValue(factura.getCondicionAbreviada());
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(22);
            cell.setCellValue(factura.getCodigo_control());
            cell.setCellStyle(styles.get("general_center"));

            cell = row.createCell(23);
            cell.setCellValue(0);
            cell.setCellStyle(styles.get("number_center"));
        }

        //ancho columnas
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 50 * 256); //nombre o razon social
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 15 * 256);
        sheet.setColumnWidth(14, 15 * 256);
        sheet.setColumnWidth(15, 15 * 256);
        sheet.setColumnWidth(16, 15 * 256);
        sheet.setColumnWidth(17, 15 * 256);
        sheet.setColumnWidth(18, 15 * 256);
        sheet.setColumnWidth(19, 15 * 256);
        sheet.setColumnWidth(20, 15 * 256);
        sheet.setColumnWidth(21, 15 * 256);
        sheet.setColumnWidth(22, 20 * 256); // codigo de control
        sheet.setColumnWidth(23, 15 * 256);

        // Write the output to a file
        String pathname = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file + ".xlsx";
        FileOutputStream out = new FileOutputStream(pathname);
        wb.write(out);
    }

    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        styles.put("general_center", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat((short) 1);
        styles.put("number_center", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("custom_center", style);

        return styles;
    }
}
