/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Tincho
 */
public class Redondeo {

    public static double redondear_HALFEVEN(double numero, int numero_decimales) {
        BigDecimal bigDecimal = new BigDecimal(numero);
        BigDecimal bigDecimalRedondeado = bigDecimal.setScale(numero_decimales, RoundingMode.HALF_EVEN);

        return bigDecimalRedondeado.doubleValue();
    }

    public static double redondear_HALFUP(double numero, int numero_decimales) {
        BigDecimal bigDecimal = new BigDecimal(numero);
        BigDecimal bigDecimalRedondeado = bigDecimal.setScale(numero_decimales, RoundingMode.HALF_UP);

        return bigDecimalRedondeado.doubleValue();
    }

    public static double redondear_DOWN(double numero, int numero_decimales) {
        BigDecimal bigDecimal = new BigDecimal(numero);
        BigDecimal bigDecimalRedondeado = bigDecimal.setScale(numero_decimales, RoundingMode.DOWN);

        return bigDecimalRedondeado.doubleValue();
    }

    public static double redondear_UP(double numero, int numero_decimales) {
        BigDecimal bigDecimal = new BigDecimal(numero);
        BigDecimal bigDecimalRedondeado = bigDecimal.setScale(numero_decimales, RoundingMode.UP);

        return bigDecimalRedondeado.doubleValue();
    }

    public static String formatear_csm(double numero) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("###,##0.00", dfs);

        return df.format(numero);
    }

    public static String formatear_ssm(double numero) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("0.00", dfs);

        return df.format(numero);
    }

    public static String formatear_0p00(double numero) {
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(numero);
    }
}
