/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;

import java.io.Serializable;

/**
 * Clase que representa una carta en la baraja.
 * Cada carta tiene un palo y un valor.
 * 
 * @author Jorge
 */
public class Carta implements Serializable {
    private String palo;
    private int valor;

    /**
     * Constructor de la carta.
     * 
     * @param palo El palo de la carta (Corazones, Diamantes, Tréboles, Picas).
     * @param valor El valor de la carta (1-13, donde 1 es As, 11 es J, 12 es Q, 13 es K).
     */
    public Carta(String palo, int valor) {
        this.palo = palo;
        this.valor = valor;
    }

    /**
     * Obtiene el palo de la carta.
     * 
     * @return El palo de la carta.
     */
    public String getPalo() {
        return palo;
    }

    /**
     * Obtiene el valor de la carta.
     * 
     * @return El valor de la carta.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Devuelve una representación en cadena de la carta, con el nombre correcto para las figuras.
     * 
     * @return Representación en cadena de la carta.
     */
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
