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
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author adria
 */
public class TorneoDAOImplements implements TorneoDAO {

    @Override
    public boolean crearTorneo(Torneo t) {
        String sq = "INSERT INTO torneo (id_t, nombre, fecha, num_max_jugadores, inscripciones_abiertas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getFecha());
            ps.setInt(4, t.getNum_max_jugadores());
            ps.setBoolean(5, t.isInscripciones_abiertas());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            serializarTorneo(t);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear torneo" + e.toString());
            //e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    @Override
    public Torneo buscarTorneo(int id) {
        String sq = "SELECT * FROM torneo WHERE id_t = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, id);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {//si se ha encontrado algun jugador
                // Recuperar los datos del jugador
                int id_t = resultado.getInt("id_t");
                String nombre = resultado.getString("nombre");
                String fecha = resultado.getString("fecha");
                int num_max_j = resultado.getInt("num_max_jugadores");
                boolean i_abiertas = resultado.getBoolean("inscripciones_abiertas");

                // Crear el objeto Torneo
                Torneo salida = new Torneo(id_t, nombre, fecha, num_max_j);
                salida.setInscripciones_abiertas(i_abiertas);
                return salida;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar un torneo" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return null;//arreglar el devolver un null
    }

    @Override
    public boolean modificarTorneo(Torneo t) {
        String sq = "UPDATE torneo SET id_t = ?, nombre=?, fecha = ?, num_max_jugadores = ?, inscripciones_abiertas = ? WHERE id_t = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getFecha());
            ps.setInt(4, t.getNum_max_jugadores());
            ps.setBoolean(5, t.isInscripciones_abiertas());
            ps.setInt(6, t.getId_t());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            actualizarTorneoSerializado(t);
            // Cerrar el PreparedStatement después de su uso
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar un torneo" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }

    }

    @Override
    public boolean guardarTorneoJugado(Torneo t) {
        GenerarInforme.generarInformeTorneo(t);
        String sqJxT = "INSERT INTO jugadorXtorneo (id_j, id_t, posicion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sqJxT);) {//update en torneo y insert en jxt
            modificarTorneo(t);
            t.ranking();//ordeno inscritos para guardar las posiciones
            for (int j = 0; j < t.getInscritos().size(); j++) {
                // Establecemos los parámetros
                ps.setInt(1, t.getInscritos().get(j).getId_j());
                ps.setInt(2, t.getId_t());
                ps.setInt(3, j + 1);
                // Ejecutamos la sentencia
                ps.executeUpdate();
            }

            actualizarTorneoSerializado(t);

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
        String sq_jugadorxtorneo = "DELETE FROM jugadorXtorneo WHERE id_t = ?";
        String sq_torneo = "DELETE FROM torneo WHERE id_t = ?";
        String sq_torneoSerializado = "DELETE FROM torneoSerializado WHERE id_t = ?";

        // Obtienes la conexión desde el Singleton
        try {
            //No se usa try with resources porque no nos interesa cerrar la conexion al final
            Connection conn = ConexionBBDD.getConnection();
            // Desactivamos el autocommit para manejar la transacción
            conn.setAutoCommit(false);

            try (PreparedStatement ps_jugadorXtorneo = conn.prepareStatement(sq_jugadorxtorneo); PreparedStatement ps_torneo = conn.prepareStatement(sq_torneo); PreparedStatement ps_torneoSerializado = conn.prepareStatement(sq_torneoSerializado)) {
                // Ejecutar la eliminación de registros en la tabla jugadorXtorneo
                ps_jugadorXtorneo.setInt(1, t.getId_t());
                ps_jugadorXtorneo.executeUpdate();

                // Ejecutar la eliminación del torneo
                ps_torneo.setInt(1, t.getId_t());
                ps_torneo.executeUpdate();

                // Ejecutar la eliminación de torneo serializado
                ps_torneoSerializado.setInt(1, t.getId_t());
                ps_torneoSerializado.executeUpdate();

                // Si todo fue bien, confirmamos la transacción
                conn.commit();
                ConexionBBDD.getConnection().setAutoCommit(true); // si no no me guarda despues nada
                return true;

            } catch (SQLException e) {
                // Si ocurre un error, hacemos rollback para revertir los cambios
                conn.rollback();
                System.out.println("Error al eliminar torneo: " + e.toString());
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            // Error al gestionar la conexión o la transacción
            System.out.println("Error al conectar o gestionar la transacción: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Torneo> listarTorneos() {
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        String sq = "SELECT * FROM torneo";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ResultSet rsT = ps.executeQuery();

            while (rsT.next()) {
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getString(2), rsT.getString(3), rsT.getInt(4)));
                torneos_salida.getLast().setInscripciones_abiertas(rsT.getBoolean(5));
            }

        } catch (SQLException e) {
            System.out.println("Error " + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return torneos_salida;
    }

    public static ArrayList<Torneo> leerBBDDTorneos(ArrayList<Jugador> jugadores_entrada) { //esto deberia ser mi listar torneos? //o paso el al por parametro o llamo a jugadordaoimplements para lo cual necesito instanciarme la clase?
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        String sq_torneo = "Select * from torneo";
        String sq_jugadorxtorneo = "Select * from jugadorXtorneo"; //intentar hacer todo con un slect mas complejo?

        try (PreparedStatement ps_torneo = ConexionBBDD.getConnection().prepareStatement(sq_torneo); ResultSet rsT = ps_torneo.executeQuery(); PreparedStatement ps_jugadorxtorneo = ConexionBBDD.getConnection().prepareStatement(sq_jugadorxtorneo); ResultSet rsJxT = ps_jugadorxtorneo.executeQuery();) {

            while (rsT.next()) {
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getString(2), rsT.getString(3), rsT.getInt(4)));
                torneos_salida.getLast().setInscripciones_abiertas(rsT.getBoolean(5));
            }

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
        } catch (SQLException e) {
            System.out.println("error >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return torneos_salida;

    }

    public void serializarTorneo(Torneo t) {
        // Serializar el objeto Torneo a un archivo
        String sq = "INSERT INTO torneoSerializado (id_t, torneo_data) VALUES (?, ?)";

        try (
                // Crear el ByteArrayOutputStream y ObjectOutputStream dentro de try-with-resources
                ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos); PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            // Serializar el objeto Torneo
            oos.writeObject(t);
            byte[] torneoSerializado = bos.toByteArray(); // Convertir el objeto en un array de bytes

            // Establecer los parámetros del PreparedStatement
            ps.setInt(1, t.getId_t());
            ps.setBytes(2, torneoSerializado);

            // Ejecutar la consulta para insertar los datos
            ps.executeUpdate();

        } catch (IOException e) {
            // Manejar la excepción de serialización
            System.out.println("Error de serialización: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            // Manejar la excepción de base de datos
            System.out.println("Error al guardar en la base de datos");
            e.printStackTrace();
        }

    }

    public boolean actualizarTorneoSerializado(Torneo t) {
        // Serializar el objeto Torneo a un array de bytes
        // Consulta SQL para actualizar
        String sql = "UPDATE torneoSerializado SET torneo_data = ? WHERE id_t = ?";

        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos); PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql)) {
            // Serializamos el objeto
            oos.writeObject(t);
            byte[] torneoSerializado = bos.toByteArray(); // Array de bytes

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

    public void top3(Torneo torneo) {

        if (torneo.isInscripciones_abiertas()) {
            System.out.println("El torneo no ha sido jugado");
            return;
        }

        String sql_return = "SELECT j.nombre,jxt.id_j, jxt.id_t, jxt.posicion "
                + "FROM jugadorxtorneo jxt "
                + "JOIN jugador j ON jxt.id_j = j.id_j "
                + "WHERE jxt.id_t = ? "
                + "ORDER BY jxt.posicion LIMIT 3;";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql_return);) {
            // Establecer el ID del torneo en la consulta antes del resultset
            ps.setInt(1, torneo.getId_t());
            try (ResultSet rs = ps.executeQuery()) {
                //no lo hago en el mismo try xq antes hay que ejecutar el ps.setInt..
                System.out.println("Top 3 jugadores del torneo:");

                // Procesar los resultados
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int id_j = rs.getInt("id_j");
                    int id_t = rs.getInt("id_t");
                    int posicion = rs.getInt("posicion");

                    System.out.println("Jugador: " + nombre + ", Jugador ID: " + id_j + ", Torneo ID: " + id_t + ", Posición: " + posicion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se ha encontrado el torneo.");
        }

    }

    public void mostrarStats(Torneo torneo) {

        if (torneo.isInscripciones_abiertas()) {
            System.out.println("El torneo no ha sido jugado");
            return;
        }
        String sql_return = "SELECT j.nombre,jxt.id_j, jxt.id_t, jxt.posicion "
                + "FROM jugadorxTorneo jxt "
                + "JOIN jugador j ON jxt.id_j = j.id_j "
                + "WHERE jxt.id_t = ? "
                + "ORDER BY jxt.posicion;";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql_return);) {
            ps.setInt(1, torneo.getId_t());
            try (ResultSet resultSet = ps.executeQuery();) {
                System.out.println("Estadisticas");
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    int id_j = resultSet.getInt("id_j");
                    int id_t = resultSet.getInt("id_t");
                    int posicion = resultSet.getInt("posicion");

                    System.out.println("Jugador: " + nombre + ", Jugador ID: " + id_j + ", Torneo ID: " + id_t + ", Posicion: " + posicion);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se ha encontrrado el torneo.");
        } 

    }

    /*
    @Override
    public boolean crearTorneo(Torneo t) {
        String sq = "INSERT INTO torneo (id_t, nombre, fecha, num_max_jugadores, inscripciones_abiertas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            // Establecemos los parámetros
            ps.setInt(1, t.getId_t());
            ps.setString(2, t.getNombre());
            ps.setInt(3, t.getFecha());
            ps.setInt(4, t.getNum_max_jugadores());
            ps.setBoolean(5, t.isInscripciones_abiertas());
            // Ejecutamos la sentencia
            ps.executeUpdate();
            serializarTorneo(t);
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
            actualizarTorneoSerializado(t);
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
            ps.close();

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
            actualizarTorneoSerializado(t);
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

    public void serializarTorneo(Torneo t) {
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
        } catch (SQLException e) {
            System.out.println("Error al guardar en la base de datos");
            e.printStackTrace();
        }

    }

    public boolean actualizarTorneoSerializado(Torneo t) {
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
    }*/
}
