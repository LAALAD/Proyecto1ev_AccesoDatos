/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;

/**
 * Clase que representa una baraja de cartas.
 * Contiene una lista de cartas y permite operaciones como barajar y tomar una carta.
 * 
 * @author Jorge
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Baraja implements Serializable {
    private ArrayList<Carta> cartas;

    /**
     * Constructor que inicializa la baraja con las cartas estándar de una baraja francesa.
     */
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

    /**
     * Método para mezclar las cartas en la baraja.
     */
    public void barajar() {
        Collections.shuffle(cartas);
    }

    /**
     * Método para tomar una carta de la baraja.
     * 
     * @return La carta tomada, o null si la baraja está vacía.
     */
    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            System.out.println("El mazo está vacío. No se pueden tomar más cartas.");
            return null;
        }
        return cartas.remove(0);
    }

    /**
     * Método para obtener la cantidad de cartas restantes en la baraja.
     * 
     * @return Número de cartas restantes en la baraja.
     */
    public int cartasRestantes() {
        return cartas.size();
    }
}
