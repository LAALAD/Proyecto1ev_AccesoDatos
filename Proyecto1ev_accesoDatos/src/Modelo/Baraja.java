/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Jorge
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Baraja implements Serializable{
    private ArrayList<Carta> cartas;

    public Baraja() {
        cartas = new ArrayList<>();
        String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};

        // Crear cartas para cada palo y valor
        for (String palo : palos) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.add(new Carta(palo, valor));
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            System.out.println("El mazo está vacío. No se pueden tomar más cartas.");
            return null;
        }
        return cartas.remove(0);
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}

