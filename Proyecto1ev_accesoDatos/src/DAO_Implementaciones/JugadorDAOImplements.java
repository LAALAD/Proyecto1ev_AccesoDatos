/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Implementaciones;

import DAO.JugadorDAO;
import IO.ConexionBBDD;
import Modelo.Jugador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación de la interfaz {@link DAO.JugadorDAO}. Proporciona operaciones
 * CRUD para gestionar jugadores en una base de datos. Utiliza una conexión a la
 * base de datos proporcionada por {@link IO.ConexionBBDD}.
 *
 * Métodos principales: - Crear, modificar, eliminar, buscar y listar jugadores.
 *
 * @author adria
 */
public class JugadorDAOImplements implements JugadorDAO {

    /**
     * Crea o actualiza un jugador en la base de datos. Si el jugador ya existe
     * (mismo id), actualiza sus datos.
     *
     * @param j El jugador que se desea crear o actualizar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean crearJugador(Jugador j) {
        // Sentencia SQL con placeholders y "ON DUPLICATE KEY UPDATE" para manejar duplicados
        String sql = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), partidas_ganadas=VALUES(partidas_ganadas), partidas_jugadas=VALUES(partidas_jugadas)";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql);) {
            // Establecemos los parámetros
            ps.setInt(1, j.getId_j());
            ps.setString(2, j.getNombre());
            ps.setDouble(3, j.getPartidasGanadas());
            ps.setDouble(4, j.getPartidasJugadas());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear un jugador" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    /**
     * Elimina un jugador de la base de datos.
     *
     * @param j El jugador que se desea eliminar.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    @Override
    public boolean eliminarJugador(Jugador j) {
        String sq = "DELETE FROM jugador WHERE id_j = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, j.getId_j());
            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) { 
            System.out.println("No puedes borrar a un jugador que ya ha participado en algún torneo.");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al eliminar jugador>" + e.toString());
            return false;
        }
    }

    /**
     * Modifica los datos de un jugador existente en la base de datos.
     *
     * @param j El jugador con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean modificarJugador(Jugador j) {
        String sq = "UPDATE jugador SET id_j = ?, nombre = ?, partidas_ganadas = ?, partidas_jugadas = ? WHERE id_j = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, j.getId_j());
            ps.setString(2, j.getNombre());
            ps.setDouble(3, j.getPartidasGanadas());
            ps.setDouble(4, j.getPartidasJugadas());
            ps.setInt(5, j.getId_j());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al modificar un jugador" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    /**
     * Guarda los datos de un jugador existente en la base de datos.
     *
     * @param j El jugador con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean guardarJugador(Jugador j) {
        String sq = "UPDATE jugador SET id_j = ?, nombre = ?, partidas_ganadas = ?, partidas_jugadas = ? WHERE id_j = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, j.getId_j());
            ps.setString(2, j.getNombre());
            ps.setDouble(3, j.getPartidasGanadas());
            ps.setDouble(4, j.getPartidasJugadas());
            ps.setInt(5, j.getId_j());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar un jugador" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    /**
     * Busca un jugador en la base de datos por su ID.
     *
     * @param id El ID del jugador que se desea buscar.
     * @return Un objeto {@link Modelo.Jugador} si se encuentra, null si no.
     */
    @Override
    public Jugador buscarJugador(int id) {
        String sq = "SELECT * FROM jugador WHERE id_j = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, id);
            try (ResultSet resultado = ps.executeQuery();) {
                if (resultado.next()) {//si se ha encontrado algun jugador
                    // Recuperar los datos del jugador
                    int id_j = resultado.getInt("id_j");
                    String nombre = resultado.getString("nombre");
                    int pg = resultado.getInt("partidas_ganadas");
                    int pj = resultado.getInt("partidas_jugadas");
                    // Crear el objeto Jugador
                    Jugador salida = new Jugador(id_j, nombre);
                    salida.setPartidasGanadas(pg);
                    salida.setPartidasJugadas(pj);
                    return salida;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar un jugador" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return null;
    }

    /**
     * Lista todos los jugadores en la base de datos.
     *
     * @return Una lista de objetos {@link Modelo.Jugador} o una lista vacía si
     * no hay jugadores.
     */
    @Override
    public ArrayList<Jugador> listarJugadores() {
        ArrayList<Jugador> jugadores_salida = new ArrayList<>();
        String sq = "SELECT * FROM jugador";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq); ResultSet rsJ = ps.executeQuery();) {

            while (rsJ.next()) {
                jugadores_salida.add(new Jugador(rsJ.getInt(1), rsJ.getString(2)));
                jugadores_salida.getLast().setPartidasGanadas(rsJ.getInt(3));
                jugadores_salida.getLast().setPartidasJugadas(rsJ.getInt(4));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return jugadores_salida;
    }
}
