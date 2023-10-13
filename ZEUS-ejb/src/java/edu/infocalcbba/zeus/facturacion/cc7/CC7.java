/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facturacion.cc7;

import edu.infocalcbba.zeus.facturacion.cc7.algoritmos.AllegedRC4;
import edu.infocalcbba.zeus.facturacion.cc7.algoritmos.Base64;
import edu.infocalcbba.zeus.facturacion.cc7.algoritmos.Verhoeff;

/**
 *
 * @author tincho
 */
public class CC7 {

    public static String obtener(String numero_autorizacion, String numero_factura, String nit_ci, String fecha_transaccion, String monto_transaccion, String llave_dosificacion) {
        /*########## Paso 1 ##########*/
        numero_factura = numero_factura + Verhoeff.obtener(numero_factura);
        nit_ci = nit_ci + Verhoeff.obtener(nit_ci);
        fecha_transaccion = fecha_transaccion + Verhoeff.obtener(fecha_transaccion);
        monto_transaccion = monto_transaccion + Verhoeff.obtener(monto_transaccion);

        numero_factura = numero_factura + Verhoeff.obtener(numero_factura);
        nit_ci = nit_ci + Verhoeff.obtener(nit_ci);
        fecha_transaccion = fecha_transaccion + Verhoeff.obtener(fecha_transaccion);
        monto_transaccion = monto_transaccion + Verhoeff.obtener(monto_transaccion);

        long sumatoria = Long.parseLong(numero_factura) + Long.parseLong(nit_ci) + Long.parseLong(fecha_transaccion) + Long.parseLong(monto_transaccion);
        String sumaStr = String.valueOf(sumatoria);

        sumaStr = sumaStr + Verhoeff.obtener(sumaStr);
        sumaStr = sumaStr + Verhoeff.obtener(sumaStr);
        sumaStr = sumaStr + Verhoeff.obtener(sumaStr);
        sumaStr = sumaStr + Verhoeff.obtener(sumaStr);
        sumaStr = sumaStr + Verhoeff.obtener(sumaStr);
        String digitos = sumaStr.substring(sumaStr.length() - 5, sumaStr.length());

        /*########## Paso 2 ##########*/
        int uno = Integer.parseInt(digitos.substring(0, 1)) + 1;
        int dos = Integer.parseInt(digitos.substring(1, 2)) + 1;
        int tres = Integer.parseInt(digitos.substring(2, 3)) + 1;
        int cuatro = Integer.parseInt(digitos.substring(3, 4)) + 1;
        int cinco = Integer.parseInt(digitos.substring(4, 5)) + 1;

        String cuno = llave_dosificacion.substring(0, uno);
        String cdos = llave_dosificacion.substring(uno, uno + dos);
        String ctres = llave_dosificacion.substring(uno + dos, uno + dos + tres);
        String ccuatro = llave_dosificacion.substring(uno + dos + tres, uno + dos + tres + cuatro);
        String ccinco = llave_dosificacion.substring(uno + dos + tres + cuatro, uno + dos + tres + cuatro + cinco);

        cuno = numero_autorizacion + cuno;
        cdos = numero_factura + cdos;
        ctres = nit_ci + ctres;
        ccuatro = fecha_transaccion + ccuatro;
        ccinco = monto_transaccion + ccinco;

        /*########## Paso 3 ##########*/
        String mensaje = cuno + cdos + ctres + ccuatro + ccinco;
        String llave = llave_dosificacion + digitos;
        String cifrado = AllegedRC4.encriptaSinguion(mensaje, llave);

        /*########## Paso 4 ##########*/
        int numero0 = 0;
        int numero1 = 0;
        int numero2 = 0;
        int numero3 = 0;
        int numero4 = 0;
        int num = 0;
        for (int u = 0; u < cifrado.length(); u++) {
            if (num == 0) {
                numero0 = obtieneAscii(cifrado.toCharArray()[u]) + numero0;
            }
            if (num == 1) {
                numero1 = obtieneAscii(cifrado.toCharArray()[u]) + numero1;
            }
            if (num == 2) {
                numero2 = obtieneAscii(cifrado.toCharArray()[u]) + numero2;
            }
            if (num == 3) {
                numero3 = obtieneAscii(cifrado.toCharArray()[u]) + numero3;
            }
            if (num == 4) {
                numero4 = obtieneAscii(cifrado.toCharArray()[u]) + numero4;
            }
            num++;
            if (num == 5) {
                num = 0;
            }
        }
        int total = numero0 + numero1 + numero2 + numero3 + numero4;

        /*########## Paso 5 ##########*/
        numero0 = (total * numero0) / uno;
        numero1 = (total * numero1) / dos;
        numero2 = (total * numero2) / tres;
        numero3 = (total * numero3) / cuatro;
        numero4 = (total * numero4) / cinco;

        int nuevoTotal = numero0 + numero1 + numero2 + numero3 + numero4;
        String enbase = Base64.codificar(nuevoTotal);

        /*########## Paso 6 ##########*/
        String nuevaLlave = llave_dosificacion + digitos;
        String nuevoMensaje = enbase;
        String codigoControl = AllegedRC4.encriptar(nuevoMensaje, nuevaLlave);

        return codigoControl;
    }

    public static int obtieneAscii(char valor) {
        return (int) valor;
    }
}
