package Modelo;

import Factory.FactoriaItems;
import java.io.Serializable;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adria
 */
public class Jugador implements Comparable<Jugador> , Serializable{
//    private static int id_general = 0;
    private int id_j;
    private String nombre;
    private double partidasGanadas = 0;
    private double partidasJugadas = 0;
    private double partidasGanadasTorneo = 0;
    private double partidasJugadasTorneo = 0;
    //private boolean seleccion = false; //seleccion: false --> cruz / true --> cara
    private int seleccion = 2; //seleccion: 2 --> cruz / 1 --> cara
    private Item item;

    public Jugador(int id_j, String nombre) {
        this.id_j = id_j;
        this.nombre = nombre;
//        id_general++;
    }

    public Item cogerMoneda() {
        return FactoriaItems.getItem("moneda");
    }

    public Item cogerDado() {
        return FactoriaItems.getItem("dado");
    }

    public Item getItem() {
        return item;
    }

    public void setId_j(int id_j) {
        this.id_j = id_j;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }

    public void seleccionarCara() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Seleccione Cara(1) o Cruz(2)");
        String selec = sc.nextLine();
        if (selec.equals("1")) {
            seleccion = 1;
        }
        /*if (selec.equals("1")) {
            seleccion = true;
        }else{
            seleccion = false;
        }*/
    }

    public double[] obtenerPuntuacion() {//getter
        double[] puntuacion = new double[2];
        puntuacion[0] = partidasGanadas;
        puntuacion[1] = (partidasGanadas / partidasJugadas) * 100; //winRate
        //printf tiene funciones para mostrar solo dos decimales
        return puntuacion;
    }

    public double calcularWinRate() {
        return (partidasGanadasTorneo / partidasJugadasTorneo) * 100;
    }

    public void actualizarPuntuacion(boolean ganador) {
        if (ganador) {
            partidasGanadas++;
        }
        partidasJugadas++;
    }

    public void actualizarPuntuacionTorneo(boolean ganador) {
        if (ganador) {
            partidasGanadasTorneo++;
        }
        partidasJugadasTorneo++;
    }

    public void resetearPuntuacionTorneo() {
        partidasJugadasTorneo = 0;
        partidasGanadasTorneo = 0;
    }

//    public Jugador(String nombre) {
//        this.id_j = id_j;
//        this.nombre = nombre;
//        id_general++;
//    }
    public int getId_j() {
        return id_j;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPartidasGanadasTorneo(double partidasGanadasTorneo) {
        this.partidasGanadasTorneo = partidasGanadasTorneo;
    }

    public void setPartidasJugadasTorneo(double partidasJugadasTorneo) {
        this.partidasJugadasTorneo = partidasJugadasTorneo;
    }

    public double getPartidasGanadasTorneo() {
        return partidasGanadasTorneo;
    }

    public double getPartidasJugadasTorneo() {
        return partidasJugadasTorneo;
    }

    public double getPartidasGanadas() {
        return partidasGanadas;
    }

    public double getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getSeleccion() {
        return seleccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public void setSeleccion(int seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public String toString() {
        String auxSelec;
        if (seleccion == 1) {//true = 1
            auxSelec = "CARA";
        } else {
            auxSelec = "CRUZ";
        }
        return "ID: " + id_j + "NOMBRE: " + nombre + ", partidasGanadas=" + partidasGanadas + ", partidasJugadas=" + partidasJugadas;

    }

    //Se haría con object si no hubiera implementado la interfaz indicando Jugador implements Comparable<Jugador> vs Comparable 
    /*@Override
    public int compareTo(Object o){
        if(o  == null){
            throw new NullPointerException();
        }
   
        //getClass es un equals, el instance of lo necesitammos para interfaces. Porque no sabes que clase va a implementar la interfaz  
        if(o.getClass().equals(Jugador.class)){
            throw new ClassCastException();
        }
    }*/
    @Override
    public int compareTo(Jugador entrada) {
        //1->mayor 0->igual -1->menor
        if (entrada == null) {
            throw new NullPointerException();
        }

        if (this.partidasGanadasTorneo > entrada.partidasGanadasTorneo) { //cuando this > entrada 1
            return 1;
        } else if (this.partidasGanadasTorneo < entrada.partidasGanadasTorneo) {
            return -1;
        } else {
            return 0;
        }

        /*if (this.partidasGanadas > entrada.partidasGanadas) { //cuando this > entrada 1
            return 1;
        } else{
            return -1;
        }

        return 0;*/
    }

}
