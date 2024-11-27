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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Implementación del DAO para la entidad Torneo. Permite gestionar operaciones
 * CRUD sobre los torneos, incluyendo la creación, modificación, eliminación y
 * listado de torneos. Además, soporta la serialización y la gestión de
 * jugadores asociados a los torneos.
 */
public class TorneoDAOImplements implements TorneoDAO {

    /**
     * Crea un nuevo torneo en la base de datos. También serializa el objeto
     * Torneo en la base de datos.
     *
     * @param t El torneo a crear.
     * @return true si el torneo fue creado con éxito, false en caso contrario.
     */
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
            //Guardamos el torneo tambien en la tabla de torneos serializados
            serializarTorneo(t);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear torneo" + e.toString());
            //e.printStackTrace();//muestra toda la info de la excepcion en rojo
            return false;
        }
    }

    /**
     * Busca un torneo en la base de datos por su ID.
     *
     * @param id El ID del torneo a buscar.
     * @return El objeto Torneo correspondiente, o null si no se encuentra.
     */
    @Override
    public Torneo buscarTorneo(int id) {
        String sq = "SELECT * FROM torneo WHERE id_t = ?";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.setInt(1, id);
            try (ResultSet resultado = ps.executeQuery();) {
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
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar un torneo" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return null;//arreglar el devolver un null
    }

    /**
     * Modifica un torneo en la base de datos. También actualiza el torneo
     * serializado.
     *
     * @param t El torneo con los nuevos datos.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
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

    /**
     * Guarda un torneo como jugado y actualiza la información de los jugadores
     * asociados.
     *
     * @param t El torneo que ha sido jugado.
     * @return true si el torneo se guardó correctamente, false en caso
     * contrario.
     */
    @Override
    public boolean guardarTorneoJugado(Torneo t) {
        GenerarInforme.generarInformeTorneo(t);
        String sqJxT = "INSERT INTO jugadorXtorneo (id_j, id_t, posicion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sqJxT);) {//update en torneo y insert en jxt
            // Modificar el torneo (actualizar su estado)
            modificarTorneo(t);
            // Ordenar a los jugadores por su posición
            t.ranking();
            // Insertar la posición de cada jugador en la tabla jugadorXtorneo            
            for (int j = 0; j < t.getInscritos().size(); j++) {
                // Establecemos los parámetros
                ps.setInt(1, t.getInscritos().get(j).getId_j());
                ps.setInt(2, t.getId_t());
                ps.setInt(3, j + 1);
                // Ejecutamos la sentencia
                ps.executeUpdate();
            }

            // Actualizar el torneo serializado
            actualizarTorneoSerializado(t);

            // Modificar los jugadores después de jugar el torneo
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

    /**
     * Elimina un torneo de la base de datos, incluyendo los registros
     * relacionados de jugadores. Utiliza una transacción para asegurar que
     * todas las eliminaciones ocurran de manera atómica.
     *
     * @param t El torneo a eliminar.
     * @return true si el torneo fue eliminado correctamente, false en caso
     * contrario.
     */
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

    /**
     * Lista todos los torneos existentes en la base de datos.
     *
     * @return Una lista de torneos.
     */
    @Override
    public ArrayList<Torneo> listarTorneos() {
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        String sq = "SELECT * FROM torneo";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq); ResultSet rsT = ps.executeQuery();) {

            // Procesar todos los torneos y agregarlos a la lista
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

    /**
     * Lee los torneos y sus jugadores inscritos desde la base de datos y los
     * asocia a los objetos Torneo.
     *
     * @param jugadores_entrada La lista de jugadores existentes que serán
     * asociadas a los torneos.
     * @return Lista de torneos con los jugadores inscritos.
     */
    public static ArrayList<Torneo> leerBBDDTorneos(ArrayList<Jugador> jugadores_entrada) { //esto deberia ser mi listar torneos? //o paso el al por parametro o llamo a jugadordaoimplements para lo cual necesito instanciarme la clase?
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        String sq_torneo = "SELECT * FROM torneo";
        String sq_jugadorxtorneo = "SELECT * FROM jugadorXtorneo";

        try (PreparedStatement ps_torneo = ConexionBBDD.getConnection().prepareStatement(sq_torneo); ResultSet rsT = ps_torneo.executeQuery(); PreparedStatement ps_jugadorxtorneo = ConexionBBDD.getConnection().prepareStatement(sq_jugadorxtorneo); ResultSet rsJxT = ps_jugadorxtorneo.executeQuery();) {

            // Primero, obtenemos todos los torneos y los agregamos a la lista torneos_salida
            while (rsT.next()) {
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getString(2), rsT.getString(3), rsT.getInt(4)));
                torneos_salida.getLast().setInscripciones_abiertas(rsT.getBoolean(5));
            }
            // Luego, recorremos la tabla jugadorXtorneo para asociar los jugadores a los torneos
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
        return torneos_salida; // Devolver la lista de torneos con los jugadores inscritos

    }

    /**
     * Serializa un objeto Torneo y lo almacena en la base de datos.
     *
     * @param t El torneo a serializar.
     */
    public void serializarTorneo(Torneo t) {
        // Serializar el objeto Torneo a un archivo
        String sq = "INSERT INTO torneoSerializado (id_t, torneo_data) VALUES (?, ?)";

        try (// Crear el ByteArrayOutputStream y ObjectOutputStream dentro de try-with-resources
                ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
                ObjectOutputStream oos = new ObjectOutputStream(bos); 
                PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {

            
            oos.writeObject(t);// Serializar el objeto Torneo
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

    /**
     * Actualiza el torneo serializado en la base de datos.
     *
     * @param t El torneo con los nuevos datos serializados.
     */
    public boolean actualizarTorneoSerializado(Torneo t) {
        // Consulta SQL para actualizar
        String sql = "UPDATE torneoSerializado SET torneo_data = ? WHERE id_t = ?";

        // Serializar el objeto Torneo actualizado
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos); PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql)) {
            // Serializamos el objeto
            oos.writeObject(t);
            byte[] torneoSerializado = bos.toByteArray(); // Array de bytes

            // Actualizar el objeto serializado en la base de datos
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

    /**
     * Deserializa un objeto Torneo previamente almacenado en la base de datos.
     * Este método recupera un torneo serializado a partir de su ID en la base
     * de datos y lo convierte de nuevo a un objeto Torneo.
     *
     * @param idTorneo El ID del torneo que se desea recuperar.
     * @return El objeto Torneo deserializado, o null si no se encuentra el
     * torneo en la base de datos.
     * @throws IOException Si ocurre un error de entrada/salida durante el
     * proceso de deserialización o consulta de la base de datos.
     */
    public static Torneo deserializarTorneoJugado(int idTorneo){
        Torneo torneo = null; // Variable para almacenar el torneo deserializado

        // Consulta SQL para recuperar el objeto serializado desde la base de datos
        String sql = "SELECT torneo_data FROM torneoSerializado WHERE id_t = ?";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql)) {

            ps.setInt(1, idTorneo); // Establece el parámetro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //Si se encuentra un resultado
                    byte[] torneoData = rs.getBytes("torneo_data"); // Recupera el array de bytes

                    // Deserializa el array de bytes de nuevo al objeto Torneo
                    try (
                            ByteArrayInputStream bis = new ByteArrayInputStream(torneoData);
                            ObjectInputStream ois = new ObjectInputStream(bis)) {

                        torneo = (Torneo) ois.readObject();  // Convierte el array de bytes de nuevo a un objeto Torneo
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error al deserializar el objeto: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    // Si no se encuentra el torneo con el ID especificado, mostramos un mensaje
                    System.out.println("No se encontró un torneo con el ID especificado: " + idTorneo);
                }
            }
        } catch (SQLException e) { // Si ocurre un error al ejecutar la consulta SQL, mostramos el mensaje de error

            System.out.println("Error al recuperar de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        return torneo;  // Devuelve el objeto Torneo deserializado (o null si no se encontró)
    }

    /**
     * Muestra los tres primeros jugadores clasificados de un torneo. Este
     * método consulta la base de datos para obtener los jugadores con las 3
     * mejores posiciones de un torneo que ya ha sido jugado y muestra sus
     * estadísticas.
     *
     * @param torneo El torneo del que se desean mostrar los tres primeros
     * jugadores.
     */
    public void top3(Torneo torneo) {

        // Verificar si el torneo ha sido jugado (si las inscripciones están cerradas)
        if (torneo.isInscripciones_abiertas()) {
            System.out.println("El torneo no ha sido jugado");
            return; // Si no ha sido jugado, termina la ejecución del método
        }

        // Consulta SQL para obtener el top 3 de jugadores, ordenados por su posición
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

                // Iterar sobre los resultados y mostrar la información de cada jugador
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int id_j = rs.getInt("id_j");
                    int id_t = rs.getInt("id_t");
                    int posicion = rs.getInt("posicion");

                    System.out.println("Jugador: " + nombre + ", Jugador ID: " + id_j + ", Torneo ID: " + id_t + ", Posición: " + posicion);
                }
            }
        } catch (SQLException e) {// Mostrar el error en caso de fallar la consulta SQL
            e.printStackTrace();
            System.out.println("No se ha encontrado el torneo.");
        }

    }

    /**
     * Muestra las estadísticas de todos los jugadores de un torneo. Este método
     * consulta la base de datos para obtener las posiciones de todos los
     * jugadores inscritos en un torneo que ya ha sido jugado y muestra sus
     * estadísticas.
     *
     * @param torneo El torneo del que se desean mostrar las estadísticas de los
     * jugadores.
     */
    public void mostrarStats(Torneo torneo) {

        // Verificar si el torneo ha sido jugado (si las inscripciones están cerradas)
        if (torneo.isInscripciones_abiertas()) {
            System.out.println("El torneo no ha sido jugado");
            return;// Si no ha sido jugado, termina la ejecución del método
        }

        // Consulta SQL para obtener todas las posiciones de los jugadores en un torneo
        String sql_return = "SELECT j.nombre,jxt.id_j, jxt.id_t, jxt.posicion "
                + "FROM jugadorxTorneo jxt "
                + "JOIN jugador j ON jxt.id_j = j.id_j "
                + "WHERE jxt.id_t = ? "
                + "ORDER BY jxt.posicion;";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql_return);) {
            ps.setInt(1, torneo.getId_t());
            try (ResultSet resultSet = ps.executeQuery();) {
                System.out.println("Estadisticas");
                // Iterar sobre los resultados y mostrar la información de cada jugador
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    int id_j = resultSet.getInt("id_j");
                    int id_t = resultSet.getInt("id_t");
                    int posicion = resultSet.getInt("posicion");
                    // Mostrar las estadísticas de cada jugador
                    System.out.println(", Posicion: " + posicion + "Jugador: " + nombre + ", Jugador ID: " + id_j + ", Torneo ID: " + id_t);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se ha encontrrado el torneo.");
        }

    }
}
