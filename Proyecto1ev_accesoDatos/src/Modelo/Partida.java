package Modelo;

import Modelo.Jugador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adria
 */
public abstract class Partida {

    private Jugador p1;
    private Jugador p2;

    /*public Partida(Jugador p1, Jugador p2) {
        this.p1 = p1;
        this.p2 = p2;
    }*/
 /*private static boolean lanzarMoneda() {
        System.out.println("Lanzando moneda...");
        int min = 0;
        int max = 1;
        int resultado = (int) (Math.random() * (max - min + 1) + min);

        if (resultado >= 1) {
            System.out.println("Ha salido CARA");
            System.out.println("");
            return true;
        }

        System.out.println("Ha salido CRUZ");
        return false;
    }*/
    public static void jugar(Jugador p1, Jugador p2) {
        //Resetear variables locales del torneo

        System.out.println("Iniciando partida entre");

        System.out.println(p1.getNombre() + " VS " + p2.getNombre());

        //no necesario pero por claridad
        p1.seleccionarCara();

        System.out.println(p1.toString());
        System.out.println(p2.toString());

        if (p1.getSeleccion() == 2) { //false == 2
            p2.setSeleccion(1);
        }

        int contp1 = 0;
        int contp2 = 0;
        int rondas = 0;

        while (contp1 < 2 && contp2 < 2) {
            System.out.println("Ronda: " + rondas);
            //System.out.println("Ronda: " + (contp1 + contp2));
            if (p1.getSeleccion() == p1.cogerMoneda().lanzar()) {
                contp1++;
            } else {
                contp2++;
            }
            rondas++;
        }
        System.out.println("El ganador de la partida ha sido: ");

        if (contp1 >= 2) {
            //Global
            System.out.println(p1.getNombre());
            p1.actualizarPuntuacion(true);
            p2.actualizarPuntuacion(false);
            //Torneo
            p1.actualizarPuntuacionTorneo(true);
            p2.actualizarPuntuacionTorneo(false);
        } else {
            System.out.println(p2.getNombre());
            p2.actualizarPuntuacion(true);
            p1.actualizarPuntuacion(false);
            //Torneo
            p2.actualizarPuntuacionTorneo(true);
            p1.actualizarPuntuacionTorneo(false);
        }

        System.out.println("Fin de partida");
        System.out.println("");
        System.out.println("");
    }

}
