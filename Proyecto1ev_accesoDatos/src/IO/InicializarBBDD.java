package IO;

import IO.ConexionBBDD;
import java.sql.*;
import Operativa.Operativa;
import Modelo.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase encargada de la inicialización y gestión de la base de datos (BBDD).
 * Realiza operaciones como la creación de la base de datos, tablas, entradas de prueba
 * y la creación de un trigger para la validación de datos.
 * 
 * @author Usuario
 */
public class InicializarBBDD {

    // Nombre de la base de datos a crear.
    private static final String NOMBRE_BBDD = "caravscruz";

    /**
     * Crea la base de datos, selecciona la base de datos creada y genera las tablas necesarias
     * para la gestión de jugadores, torneos y las relaciones entre ellos.
     */
    public static void crearBBDD() {
        String sql_create = "CREATE DATABASE IF NOT EXISTS " + NOMBRE_BBDD;
        String sql_use = "USE " + NOMBRE_BBDD;
        try (Statement s = ConexionBBDD.getConnection().createStatement();){
            //Crear BBDD
            //System.out.println("Creando la base de datos " + NOMBRE_BBDD + "...");
            s.executeUpdate(sql_create);

            // Seleccionar la base de datos recién creada
            //System.out.println("Usando la base de datos " + NOMBRE_BBDD + "...");
            s.executeUpdate(sql_use);

            //Crear Tablas
            s.executeUpdate("CREATE TABLE IF NOT EXISTS jugador (id_j INT, nombre VARCHAR(20), partidas_ganadas DOUBLE, partidas_jugadas DOUBLE, PRIMARY KEY(id_j))");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS torneo (id_t INT, nombre VARCHAR(20), fecha VARCHAR(10), num_max_jugadores INT, inscripciones_abiertas BOOLEAN, PRIMARY KEY(id_t))");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS jugadorXtorneo ( id_j INT, id_t INT, posicion INT, PRIMARY KEY(id_j, id_t), FOREIGN KEY (id_j) REFERENCES jugador(id_j), FOREIGN KEY (id_t) REFERENCES torneo(id_t))");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS torneoSerializado ( id_t INT,  torneo_data BLOB NOT NULL)");
            
            // Insertar entradas de prueba en la base de datos
            entradasPrueba();
            
            // Crear trigger para la validación del número máximo de jugadores
            crearTrigger();
            
        } catch (SQLException e) {
            System.out.println(":>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
    }

     /**
     * Inserta jugadores de prueba en la base de datos si no existen ya.
     * Realiza una comprobación para cada jugador y solo inserta si no está presente.
     */
    public static void entradasPrueba() {
        //si he modificado y he puesto otros jugadores con los ids al arrancar el programa los piso
        // Lista de jugadores de prueba
        ArrayList<Jugador> jugadores_pruebas = new ArrayList<>();
        jugadores_pruebas.add(new Jugador(0, "Adriana"));
        jugadores_pruebas.add(new Jugador(1, "Paola"));
        jugadores_pruebas.add(new Jugador(2, "Pedro"));
        try {
            // Desactivar autocommit para gestionar la transacción manualmente
            ConexionBBDD.getConnection().setAutoCommit(false);

            // Consulta para comprobar si el jugador ya existe
            String sql_check = "SELECT COUNT(*) FROM jugador WHERE id_j = ?"; //cuenta las entradas que hay con un id
            PreparedStatement ps_check = ConexionBBDD.getConnection().prepareStatement(sql_check);

            // SQL para insertar un jugador si no existe
            String sql_insert = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?)";            
            PreparedStatement ps_insert = ConexionBBDD.getConnection().prepareStatement(sql_insert);

            // Recorrer cada jugador de prueba
            for (Jugador jugador : jugadores_pruebas) {
                // Comprobar si el jugador ya existe
                ps_check.setInt(1, jugador.getId_j()); //compruebo el id de cada jugador
                ResultSet rs = ps_check.executeQuery();
                rs.next();
                
                // Si el jugador no existe, insertarlo
                if (rs.getInt(1) == 0) { //si no se ha encontrad ninguna entrada con dicho id, se crea
                    // Si el jugador no existe, lo insertamos
                    ps_insert.setInt(1, jugador.getId_j());
                    ps_insert.setString(2, jugador.getNombre());
                    ps_insert.setDouble(3, jugador.getPartidasGanadas());
                    ps_insert.setDouble(4, jugador.getPartidasJugadas());
                    ps_insert.executeUpdate();
                } else {
                    //System.out.println("Jugador con ID " + jugador.getId_j() + " ya existe. No se inserta.");
                }
            }

            // Cerrar los PreparedStatement después de su uso
            ps_check.close();
            ps_insert.close();
            
            ConexionBBDD.getConnection().commit();
            ConexionBBDD.getConnection().setAutoCommit(true); // si no no me guarda despues nada
        } catch (SQLException e) {
            // En caso de error, hacer rollback y revertir cambios
            if (ConexionBBDD.getConnection() != null) {
                try {
                    System.out.println("Error en la transaccion");
                    ConexionBBDD.getConnection().rollback();
                } catch (SQLException exc) {
                    System.out.println(exc.toString());
                }
            }
        }
    }
    
    
    /**
     * Crea un trigger para asegurarse de que el número máximo de jugadores en un torneo
     * no pueda ser negativo.
     */
    public static void crearTrigger() {
        // SQL para crear el trigger
        String sq = "CREATE TRIGGER IF NOT EXISTS numero_max_jugadores_positivo "
                    + "BEFORE INSERT ON torneo "
                    + "FOR EACH ROW "
                    + "BEGIN "
                    + "    IF NEW.num_max_jugadores < 0 THEN "
                    + "        SIGNAL SQLSTATE '45000' "
                    + "        SET MESSAGE_TEXT = 'El número máximo de jugadores no puede ser negativo'; "
                    + "    END IF; "
                    + "END;";
        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sq);) {
            ps.executeUpdate(); // Ejecutar el trigger
            //System.out.println("Trigger creado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear el trigger: " + e.getMessage());
            //e.printStackTrace();
        }
    }

}

