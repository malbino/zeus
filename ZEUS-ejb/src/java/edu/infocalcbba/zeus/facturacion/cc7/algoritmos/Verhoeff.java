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
public class Verhoeff {

    private static final int MUL[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
        {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
        {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
        {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
        {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
        {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
        {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
        {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
        {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
    };
    private static final int PER[][] = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}
    };
    private static final int INV[] = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

    public static int obtener(String cifra) {
        int check = 0;

        String numeroInvertido = invierteCifra(cifra);
        for (int o = 0; o < numeroInvertido.length(); o++) {
            check = MUL[check][PER[((o + 1) % 8)][Integer.parseInt(numeroInvertido.substring(o, o + 1))]];
        }

        return INV[check];
    }

    public static String invierteCifra(String cifra) {
        String aux = "";

        String este = cifra;
        for (int i = 0; i < cifra.length(); i++) {
            aux = este.substring(0, 1) + aux;
            este = este.substring(1, este.length());
        }

        return aux;
    }
}
