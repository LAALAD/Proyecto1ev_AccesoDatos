/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DAM2_02
 */
@Entity
public class TorneoXJugador implements Serializable{
      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relación con Torneo (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "id_torneo", nullable = false)
    private Torneo torneo;

    // Relación con Jugador (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @Basic
    private int posicion;  // Opcional: Puntos del jugador en este torneo

    public TorneoXJugador() {}

    public TorneoXJugador(Torneo torneo, Jugador jugador) {
        this.torneo = torneo;
        this.jugador = jugador;
        this.posicion = 0; // Inicialmente en 0
    }

    public int getId() {
        return id;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }       
}
