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
    public abstract boolean crearTorneo(Torneo t); //C
    public abstract ArrayList<Torneo> listarTorneos(); //R
    public abstract Torneo buscarTorneo(int id); //R
    public abstract boolean modificarTorneo(Torneo t);
    public abstract boolean guardarTorneoJugado(Torneo t); //U
    public abstract boolean eliminarTorneo(Torneo t); //D
}
