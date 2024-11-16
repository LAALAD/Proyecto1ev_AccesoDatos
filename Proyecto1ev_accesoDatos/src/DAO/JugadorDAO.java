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
    public abstract boolean crearJugador(Jugador j); //C
    public abstract boolean eliminarJugador(Jugador j); //D
    public abstract boolean modificarJugador(Jugador j); //U
    public abstract boolean guardarJugador(Jugador j); 
    public abstract Jugador buscarJugador(int id); //R    
    public abstract ArrayList<Jugador> listarJugadores(); //R
}
