/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_Implementaciones;

import DAO.TorneoDAO;
import IO.ConexionBBDD;
import IO.GenerarInforme;
import Modelo.Jugador;
import Modelo.Torneo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author adria
 */
public class TorneoDAOImplements implements TorneoDAO {

    @Override
    public boolean crearTorneo(Torneo t) {
        try {
            String sq = "INSERT INTO torneo (id_t, nombre, fecha, num_max_jugadores, inscripciones_abiertas) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setString(2, t.getNombre());
            ps.setInt(3, t.getFecha());
            ps.setInt(4, t.getNum_max_jugadores());
            ps.setBoolean(5, t.isInscripciones_abiertas());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            // Cerrar el PreparedStatement después de su uso
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear torneo" + e.toString());
            //e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    @Override
    public Torneo buscarTorneo(int id) {
        try {
            String sq = "SELECT * FROM torneo WHERE id_t = ?";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            ps.setInt(1, id);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {//si se ha encontrado algun jugador
                // Recuperar los datos del jugador
                int id_t = resultado.getInt("id_t");
                String nombre = resultado.getString("nombre");
                int fecha = resultado.getInt("fecha");
                int num_max_j = resultado.getInt("num_max_jugadores");
                boolean i_abiertas = resultado.getBoolean("inscripciones_abiertas");

                // Crear el objeto Torneo
                Torneo salida = new Torneo(id_t, nombre, fecha, num_max_j);
                salida.setInscripciones_abiertas(i_abiertas);
                return salida;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al buscar un torneo" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return null;//arreglar el devolver un null
    }

    @Override
    public boolean modificarTorneo(Torneo t) {
        try {
            String sq = "UPDATE torneo SET id_t = ?, nombre=?, fecha = ?, num_max_jugadores = ?, inscripciones_abiertas = ? WHERE id_t = ?";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setString(2, t.getNombre());
            ps.setInt(3, t.getFecha());
            ps.setInt(4, t.getNum_max_jugadores());
            ps.setBoolean(5, t.isInscripciones_abiertas());
            ps.setInt(6, t.getId_t());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            // Cerrar el PreparedStatement después de su uso
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar un torneo" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }

    }

    @Override
    public boolean guardarTorneoJugado(Torneo t) {
        Connection con = ConexionBBDD.getConnection();
        GenerarInforme.generarInformeTorneo(t);
        try {//update en torneo y insert en jxt
            /*String sq = "UPDATE torneo SET id_t = ?, fecha = ?, num_max_jugadores = ?, inscripciones_abiertas = ? WHERE id_t = ?";
            PreparedStatement ps = con.prepareStatement(sq);
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setInt(2, t.getFecha());
            ps.setInt(3, t.getNum_max_jugadores());
            ps.setBoolean(4, t.isInscripciones_abiertas());
            ps.setInt(5, t.getId_t());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            // Cerrar el PreparedStatement después de su uso
            ps.close();*/

            modificarTorneo(t);

            String sqJxT = "INSERT INTO jugadorXtorneo (id_j, id_t, posicion) VALUES (?, ?, ?)";
            PreparedStatement psJxT = con.prepareStatement(sqJxT);
            t.ranking();//ordeno inscritos para guardar las posiciones
            for (int j = 0; j < t.getInscritos().size(); j++) {
                // Establecemos los parámetros
                psJxT.setInt(1, t.getInscritos().get(j).getId_j());
                psJxT.setInt(2, t.getId_t());
                psJxT.setInt(3, j + 1);
                // Ejecutamos la sentencia
                psJxT.executeUpdate();
            }
            // Cerrar el PreparedStatement después de su uso
            psJxT.close();

            JugadorDAOImplements j = new JugadorDAOImplements();
            for (Jugador inscrito : t.getInscritos()) {
                j.modificarJugador(inscrito);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("error guardando torneo jugado" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    @Override
    public boolean eliminarTorneo(Torneo t) {
        try {
            String sq = "DELETE FROM jugadorXtorneo WHERE id_t = ?";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            ps.setInt(1, t.getId_t());
            ps.executeUpdate();
            ps.close();

            String sq1 = "DELETE FROM torneo WHERE id_t = ?";
            PreparedStatement ps1 = ConexionBBDD.getConnection().prepareStatement(sq1);
            ps1.setInt(1, t.getId_t());
            ps1.executeUpdate();
            ps1.close();
            
            String sq2 = "DELETE FROM torneoSerializado WHERE id_t = ?";
            PreparedStatement ps2 = ConexionBBDD.getConnection().prepareStatement(sq2);
            ps2.setInt(1, t.getId_t());
            ps2.executeUpdate();
            ps2.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar torneo>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    @Override
    public ArrayList<Torneo> listarTorneos() {
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        try {
            String sq = "SELECT * FROM torneo";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            ResultSet rsT = ps.executeQuery();

            while (rsT.next()) {
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getString(2), rsT.getInt(3), rsT.getInt(4)));
                torneos_salida.getLast().setInscripciones_abiertas(rsT.getBoolean(5));
            }
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error " + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return torneos_salida;
    }

    public static ArrayList<Torneo> leerBBDDTorneos(ArrayList<Jugador> jugadores_entrada) { //esto deberia ser mi listar torneos? //o paso el al por parametro o llamo a jugadordaoimplements para lo cual necesito instanciarme la clase?
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        try {
            //Creamos 
            String sq = "Select * from torneo";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
            ResultSet rsT = ps.executeQuery();
            while (rsT.next()) {
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getString(2), rsT.getInt(3), rsT.getInt(4)));
                torneos_salida.getLast().setInscripciones_abiertas(rsT.getBoolean(5));
            }

            String sq1 = "Select * from jugadorXtorneo"; //intentar hacer todo con un slect mas complejo?
            PreparedStatement ps1 = ConexionBBDD.getConnection().prepareStatement(sq1);
            ResultSet rsJxT = ps1.executeQuery();
            while (rsJxT.next()) {
                for (Torneo torneo : torneos_salida) { //recorro todos los torneos de la tabla torneo
                    if (torneo.getId_t() == rsJxT.getInt(2)) { //busco en la tabla jugadorxtorneo cada torneo (por si no ha sido jugado)
                        for (Jugador jugador : jugadores_entrada) {//recorro todos los jugadores existentes
                            if (jugador.getId_j() == rsJxT.getInt(1)) {//busco si en la tabla salen asociados cada torneo a cada jugador y cuando hay coincidencia cargo los jugadores en los inscritos del torneo
                                torneo.getInscritos().add(jugador);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return torneos_salida;

    }

    public static void serializarTorneo(Torneo t) {
        // Serializar el objeto Torneo a un archivo
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] torneoSerializado = bos.toByteArray(); //array de bytes
            
            String sq = "INSERT INTO torneoSerializado (id_t, torneo_data) VALUES (?, ?)";
            PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);
                // Establecemos los parámetros
                ps.setInt(1, t.getId_t());
                ps.setBytes(2, torneoSerializado);
                ps.executeUpdate();
            // Cerrar el PreparedStatement después de su uso
            ps.close();
            oos.close();
            bos.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();  
        }catch(SQLException e){
            System.out.println("Error al guardar en la base de datos");
            e.printStackTrace();            
        }

    }
    
    public static boolean actualizarTorneoSerializado(Torneo t) {
    // Serializar el objeto Torneo a un array de bytes
    try {
        // Serializamos el objeto
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(t);
        byte[] torneoSerializado = bos.toByteArray(); // Array de bytes
        
        // Consulta SQL para actualizar
        String sql = "UPDATE torneoSerializado SET torneo_data = ? WHERE id_t = ?";
        
        // Preparar y ejecutar la consulta
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql)) {
             
            ps.setBytes(1, torneoSerializado); // Serialización del torneo
            ps.setInt(2, t.getId_t()); // Identificador del torneo
            
            int filasAfectadas = ps.executeUpdate(); // Ejecutar el UPDATE
            
            // Verificar si el torneo fue actualizado
            if (filasAfectadas > 0) {
                return true; // Actualización exitosa
            } else {
                System.out.println("No se encontró un torneo con id_t = " + t.getId_t());
                return false; // No se encontró el torneo
            }
        }
        
    } catch (IOException e) {
        System.out.println("Error al serializar el torneo");
        e.printStackTrace();
        return false;
    } catch (SQLException e) {
        System.out.println("Error al actualizar el torneo en la base de datos");
        e.printStackTrace();
        return false;
    }
}

}
