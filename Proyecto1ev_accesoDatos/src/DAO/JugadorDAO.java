/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Modelo.Jugador;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public interface JugadorDAO {
    /**
     * 
     * @param j
     * @return boolean
     */
    public abstract boolean crearJugador(Jugador j); //C
    /**
     * 
     * @param j
     * @return boolean
     */
    public abstract boolean eliminarJugador(Jugador j); //D
    
    /**
     * 
     * @param j
     * @return boolean
     */
    public abstract boolean modificarJugador(Jugador j); //U
    
    /**
     * 
     * @param j
     * @return boolean
     */
    public abstract boolean guardarJugador(Jugador j); 
    
    /**
     * 
     * @param id
     * @return Jugador
     */
    public abstract Jugador buscarJugador(int id); //R    
    
    /**
     * 
     * @return ArrayList<Jugador>
     */
    public abstract ArrayList<Jugador> listarJugadores(); //R
}
