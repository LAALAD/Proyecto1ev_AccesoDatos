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
 * Clase que representa la relación entre un torneo y un jugador.
 * Contiene información sobre el torneo, el jugador inscrito y su posición en el ranking.
 * 
 * Implementa la interfaz {@link Comparable} para permitir la comparación entre jugadores
 * dentro de un torneo en función de su posición.
 * 
 * @author DAM2_02
 */
@Entity
public class TorneoXJugador implements Comparable<TorneoXJugador>, Serializable {

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
    private int posicion;  // Posición del jugador en el torneo

    /**
     * Constructor vacío requerido por JPA.
     */
    public TorneoXJugador() {
    }

    /**
     * Constructor de la clase TorneoXJugador.
     * 
     * @param torneo  El torneo en el que participa el jugador.
     * @param jugador El jugador inscrito en el torneo.
     */
    public TorneoXJugador(Torneo torneo, Jugador jugador) {
        this.torneo = torneo;
        this.jugador = jugador;
        this.posicion = 0; // Inicialmente en 0
    }

    /**
     * Obtiene el identificador único de la relación TorneoXJugador.
     * 
     * @return El ID de la relación.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el torneo asociado a esta relación.
     * 
     * @return Objeto {@link Torneo} en el que participa el jugador.
     */
    public Torneo getTorneo() {
        return torneo;
    }

    /**
     * Obtiene el jugador asociado a esta relación.
     * 
     * @return Objeto {@link Jugador} que participa en el torneo.
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Obtiene la posición del jugador en el torneo.
     * 
     * @return La posición del jugador en el ranking del torneo.
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Establece el identificador único de la relación TorneoXJugador.
     * 
     * @param id El nuevo ID de la relación.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el torneo asociado a esta relación.
     * 
     * @param torneo Objeto {@link Torneo} en el que participa el jugador.
     */
    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    /**
     * Establece el jugador asociado a esta relación.
     * 
     * @param jugador Objeto {@link Jugador} que participa en el torneo.
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * Establece la posición del jugador en el torneo.
     * 
     * @param posicion La nueva posición del jugador en el ranking del torneo.
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     * Representación en cadena del objeto TorneoXJugador.
     * 
     * @return Una cadena con la información del torneo, jugador y posición.
     */
    @Override
    public String toString() {
        return "TorneoXJugador{" + "id=" + id + ", torneo=" + torneo + ", jugador=" + jugador + ", posicion=" + posicion + '}';
    }

    /**
     * Compara dos objetos {@link TorneoXJugador} según la posición del jugador en el torneo.
     * 
     * @param entrada Objeto {@link TorneoXJugador} con el que se va a comparar.
     * @return Un valor positivo si el jugador actual tiene una mejor posición,
     *         un valor negativo si la otra entrada tiene mejor posición, o 0 si tienen la misma.
     * @throws NullPointerException Si el objeto comparado es nulo.
     */
    @Override
    public int compareTo(TorneoXJugador entrada) {
        //1->mayor 0->igual -1->menor
        if (entrada == null) {
            throw new NullPointerException();
        }

        // Comparar por posiciones en torneo
        if (this.posicion > entrada.posicion) {
            return 1; // El jugador actual tiene mejor posición
        } else if (this.posicion < entrada.posicion) {
            return -1; // El otro jugador tiene mejor posición
        } else {
            return 0; // Empate
        }
    }
}
