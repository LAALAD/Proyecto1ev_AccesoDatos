/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Jorge
 */
public class Carta implements Serializable{
    private String palo;
    private int valor;

    public Carta(String palo, int valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public String getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        String valorNombre;

        switch (valor) {
            case 1:
                valorNombre = "As";
                break;
            case 11:
                valorNombre = "J";
                break;
            case 12:
                valorNombre = "Q";
                break;
            case 13:
                valorNombre = "K";
                break;
            default:
                valorNombre = String.valueOf(valor);
        }

        return valorNombre + " de " + palo;
    }
}

