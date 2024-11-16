package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adria
 */
public class Torneo implements Serializable { 

    private int id_t;
    private String nombre;
    private int fecha;
    private int num_max_jugadores;
    private boolean inscripciones_abiertas;
    //private boolean jugado = false;
    private ArrayList<Jugador> inscritos = new ArrayList<>();

    public Torneo(int id_t, String nombre, int fecha, int num_max_jugadores) {
        this.id_t = id_t;
        this.nombre = nombre;
        this.fecha = fecha;
        this.num_max_jugadores = num_max_jugadores;
        this.inscripciones_abiertas = true;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    /*public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }*/

    public void setInscripciones_abiertas(boolean inscripciones_abiertas) {
        this.inscripciones_abiertas = inscripciones_abiertas;
    }

    public boolean isInscripciones_abiertas() {
        return inscripciones_abiertas;
    }

    /*public boolean isJugado() {
        return jugado;
    }*/

    public int getId_t() {
        return id_t;
    }

    public int getFecha() {
        return fecha;
    }

    public int getNum_max_jugadores() {
        return num_max_jugadores;
    }

    public ArrayList<Jugador> getInscritos() {
        return inscritos;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public void setNum_max_jugadores(int num_max_jugadores) {
        this.num_max_jugadores = num_max_jugadores;
    }

    public void setInscritos(ArrayList<Jugador> inscritos) {
        this.inscritos = inscritos;
    }

    @Override
    public String toString() {
        return "ID: " + id_t +  " NOMBRE: " + nombre;
    }

    public void jugar() {
        for (int i = 0; i < inscritos.size(); i++) {
            for (int j = i + 1; j < inscritos.size(); j++) {
                Partida.jugar(inscritos.get(i), inscritos.get(j));
            }
        }
        //jugado = true;
        inscripciones_abiertas = false;
        ranking();
    }

    public void ranking() {
        Collections.sort(inscritos);
        Collections.reverse(inscritos);
    }

    public void inscribir(Jugador entrada) {
        if (!inscripciones_abiertas) {//no me carga los inscritos desde la bbdd pq las inscripciones se cierran al jugar...
            System.out.println("Las inscripciones a este torneo han sido cerradas");
        } else if (comprobarJugadorInscrito(entrada)) {
            System.out.println("Ya estás inscrito en este torneo.");
        } else if (inscritos.size() >= num_max_jugadores) {
            System.out.println("No hay plazas disponibles en el torneo");
        } else {
            inscritos.add(entrada);

        }
    }

    public boolean comprobarJugadorInscrito(Jugador entrada) {
        for (Jugador it : inscritos) {
            if (it.getId_j() == entrada.getId_j()) {
                return true;
            }
        }
        return false;
    }
}
