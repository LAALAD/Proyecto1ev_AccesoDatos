/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class ItemMoneda extends Item {

    public ItemMoneda() {
    }

    public int lanzar() { //cara-1 cruz-2
        System.out.println("Lanzando moneda...");
        int min = 0;
        int max = 1;
        int resultado = (int) (Math.random() * (max - min + 1) + min);

        if (resultado >= 1) {
            System.out.println("Ha salido CARA");
            System.out.println("");
            return 1; //true
        }

        System.out.println("Ha salido CRUZ");
        return 2; //false
    }
}
