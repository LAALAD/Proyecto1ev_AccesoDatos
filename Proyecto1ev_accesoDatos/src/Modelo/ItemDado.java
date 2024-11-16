/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Random;

/**
 * Clase que representa un dado como un tipo de ítem.
 *
 * Esta clase extiende de {@link Item} y proporciona una implementación concreta
 * del método `lanzar()`. El método simula el lanzamiento de un dado, generando
 * un número aleatorio entre 1 y 6, similar a un dado estándar.
 *
 * @author Usuario
 */
public class ItemDado extends Item {

    /**
     * Lanza el dado generando un número aleatorio entre 1 y 6.
     *
     * Este método sobreescribe el método abstracto `lanzar()` de la clase
     * {@link Item}, y devuelve un número entre 1 y 6 como resultado de lanzar
     * el dado. Utiliza la clase {@link Random} para generar el número.
     *
     * @return El número aleatorio generado por el lanzamiento del dado, que
     * será un valor entre 1 y 6.
     */
    @Override
    public int lanzar() {
        //System.out.println("Lanzando dado...");
        // Creamos un objeto de la clase Random para generar el número aleatorio.
        Random rand = new Random();
        
        // Generamos un número aleatorio entre 0 y 5 (con rand.nextInt(6)) y le sumamos 1 
        // para obtener un valor entre 1 y 6, como el resultado de un dado.
        int resultado = rand.nextInt(6) + 1;
        return resultado; // Retorna el resultado del lanzamiento del dado.
    }

}
