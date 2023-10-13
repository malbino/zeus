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
public class Base64 {

    private static final String[] DICCIONARIO = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d",
        "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
        "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
        "y", "z", "+", "/"
    };

    public static String codificar(long valor) {
        String res = "";

        long cociente = 1;
        long resto;

        while (cociente > 0) {
            cociente = valor / 64;
            resto = valor % 64;
            res = DICCIONARIO[(int) resto] + res;
            valor = cociente;
        }

        return res;
    }
}
