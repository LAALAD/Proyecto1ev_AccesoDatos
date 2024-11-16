/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Modelo.Torneo;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public interface TorneoDAO {
    /**
     * 
     * @param t
     * @return boolean
     */
    public abstract boolean crearTorneo(Torneo t); //C
    
    /**
     * 
     * @return ArrayList<Torneo>
     */
    public abstract ArrayList<Torneo> listarTorneos(); //R
    
    /**
     * 
     * @param id
     * @return Torneo
     */
    public abstract Torneo buscarTorneo(int id); //R
    
    /**
     * 
     * @param t
     * @return boolean
     */
    public abstract boolean modificarTorneo(Torneo t);
    
    /**
     * 
     * @param t
     * @return boolean
     */
    public abstract boolean guardarTorneoJugado(Torneo t); //U
    
    /**
     * 
     * @param t
     * @return boolean
     */
    public abstract boolean eliminarTorneo(Torneo t); //D
}
