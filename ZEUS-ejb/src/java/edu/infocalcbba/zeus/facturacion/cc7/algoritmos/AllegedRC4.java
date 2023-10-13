/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facturacion.cc7.algoritmos;

/**
 *
 * @author tincho
 */
public class AllegedRC4 {

    public static String encriptaSinguion(String mensaje, String llave) {
        String resul;

        resul = encriptar(mensaje, llave);
        resul = resul.replace("-", "");

        return resul;
    }

    public static String encriptar(String mensaje, String llave) {
        int state[] = new int[256];
        int index1 = 0;
        int index2 = 0;
        int x = 0;
        int y = 0;
        int nmen;
        String cifrado = "";
        for (int i = 0; i < 256; i++) {
            state[i] = i;
        }
        for (int o = 0; o < 256; o++) {
            index2 = (obtieneAscii(llave.toCharArray()[index1]) + state[o] + index2) % 256;
            int aux;
            //intercambiando valores
            aux = state[o];
            state[o] = state[index2];
            state[index2] = aux;
            index1 = (index1 + 1) % llave.length();
        }
        int uno;
        int dos;
        for (int u = 0; u < mensaje.length(); u++) {
            x = (x + 1) % 256;
            y = (state[x] + y) % 256;
            //intercambiando valor
            int aux2;
            aux2 = state[x];
            state[x] = state[y];
            state[y] = aux2;
            uno = obtieneAscii(mensaje.toCharArray()[u]);
            dos = state[(state[x] + state[y]) % 256];
            nmen = uno ^ dos;
            cifrado = cifrado + "-" + rellenaCero(decimalaHexadecimal(nmen));

        }
        String Resultado;
        Resultado = cifrado.substring(1, cifrado.length());
        return Resultado;
    }

    public static int obtieneAscii(char valor) {
        return (int) valor;
    }

    public static String rellenaCero(String valor) {
        if (valor.length() == 1) {
            valor = "0" + valor;
        }
        return valor;
    }

    public static String decimalaHexadecimal(int valor) {
        return Integer.toHexString(valor).toUpperCase();
    }
}
