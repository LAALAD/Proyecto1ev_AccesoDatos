/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Scanner;

/**
 * Clase que representa una moneda como un tipo de ítem.
 *
 * Esta clase extiende de {@link Item} y proporciona una implementación concreta
 * del método `lanzar()`. El método simula el lanzamiento de una moneda,
 * generando un resultado aleatorio entre dos opciones: "CARA" y "CRUZ". El
 * resultado se devuelve como un valor entero, donde 1 representa "CARA" y 2
 * representa "CRUZ".
 *
 * @author Usuario
 */
public class ItemMoneda extends Item {

    /**
     * Lanza la moneda y devuelve un resultado aleatorio entre dos opciones:
     * "CARA" (1) o "CRUZ" (2).
     *
     * Este método sobrescribe el método abstracto `lanzar()` de la clase
     * {@link Item}, y simula un lanzamiento de moneda utilizando la función
     * {@link Math#random()} para generar un número aleatorio. Si el número
     * aleatorio es 0 o menor, el resultado será "CARA" y si es mayor, será
     * "CRUZ".
     *
     * @return Un número entero que indica el resultado del lanzamiento: 1 para
     * "CARA" y 2 para "CRUZ".
     */
    public int lanzar() { //cara-1 cruz-2
        System.out.println("Lanzando moneda...");
        
        // Se genera un número aleatorio 0 y 1 para simular el lanzamiento.
        int min = 0;
        int max = 1;
        int resultado = (int) (Math.random() * (max - min + 1) + min);

        
        // Si el número aleatorio es 1 o mayor, el resultado es "CARA".
        if (resultado >= 1) {
            System.out.println("Ha salido CARA");
            System.out.println("");
            return 1; // CARA //true
        }

        // Si el número aleatorio es 0, el resultado es "CRUZ".
        System.out.println("Ha salido CRUZ");
        return 2; // CRUZ //false
    }
}
