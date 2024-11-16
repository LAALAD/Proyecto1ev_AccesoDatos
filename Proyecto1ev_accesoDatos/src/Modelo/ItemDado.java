/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Random;

/**
 *
 * @author Usuario
 */
public class ItemDado extends Item {

    @Override
    public int lanzar() {
        //System.out.println("Lanzando dado...");
        Random rand = new Random();
        int resultado = rand.nextInt(6) + 1;
        return resultado; // Genera un número entre 1 y 6}
    }
    
}