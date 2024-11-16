package Operativa;

import IO.InicializarBBDD;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author adria
 */
public class main {

    public static void main(String[] args){
        InicializarBBDD.crearBBDD();
        Menu.menu();
    }

}
