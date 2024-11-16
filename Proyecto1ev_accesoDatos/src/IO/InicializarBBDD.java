package IO;

import IO.ConexionBBDD;
import java.sql.*;
import Operativa.Menu;
import Modelo.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Usuario
 */
public class InicializarBBDD {

//    private static Connection con = null;
    private static final String NOMBRE_BBDD = "caravscruz";

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

            entradasPrueba();
            
            crearTrigger();
            
        } catch (Exception e) {
            System.out.println(":>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
    }

    public static void entradasPrueba() {
        //si he modificado y he puesto otros jugadores con los ids al arrancar el programa los piso
        ArrayList<Jugador> jugadores_pruebas = new ArrayList<>();
        jugadores_pruebas.add(new Jugador(0, "Adriana"));
        jugadores_pruebas.add(new Jugador(1, "Paola"));
        jugadores_pruebas.add(new Jugador(2, "Pedro"));
        try {
            ConexionBBDD.getConnection().setAutoCommit(false);

            // Consulta para comprobar si el jugador ya existe
            String sql_check = "SELECT COUNT(*) FROM jugador WHERE id_j = ?"; //cuenta las entradas que hay con un id
            PreparedStatement ps_check = ConexionBBDD.getConnection().prepareStatement(sql_check);

            // SQL para insertar un jugador si no existe
            String sql_insert = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?)";            
            PreparedStatement ps_insert = ConexionBBDD.getConnection().prepareStatement(sql_insert);

            for (Jugador jugador : jugadores_pruebas) {
                // Comprobar si el jugador ya existe
                ps_check.setInt(1, jugador.getId_j()); //compruebo el id de cada jugador
                ResultSet rs = ps_check.executeQuery();
                rs.next();
                //int count = rs.getInt(1);
                //if (count == 0) {
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
public static void crearTrigger() {
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
            ps.executeUpdate();
            //System.out.println("Trigger creado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear el trigger: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    /*public static void entradasPrueba() {
        //si he modificado y he puesto otros jugadores con los ids al arrancar el programa los piso
        ArrayList<Jugador> jugadores_pruebas = new ArrayList<>();
        jugadores_pruebas.add(new Jugador(0, "Adriana"));
        jugadores_pruebas.add(new Jugador(1, "Paola"));
        jugadores_pruebas.add(new Jugador(2, "Pedro"));
        try {
            ConexionBBDD.getConnection().setAutoCommit(false);

            String sql = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), partidas_ganadas=VALUES(partidas_ganadas), partidas_jugadas=VALUES(partidas_jugadas)";
            PreparedStatement ps1 = ConexionBBDD.getConnection().prepareStatement(sql);
            for (int i = 0; i < jugadores_pruebas.size(); i++) {
                // Establecemos los parámetros
                ps1.setInt(1, jugadores_pruebas.get(i).getId_j());
                ps1.setString(2, jugadores_pruebas.get(i).getNombre());
                ps1.setDouble(3, jugadores_pruebas.get(i).getPartidasGanadas());
                ps1.setDouble(4, jugadores_pruebas.get(i).getPartidasJugadas());
                // Ejecutamos la sentencia
                ps1.executeUpdate();

            }
            // Cerrar el PreparedStatement después de su uso
            ps1.close();

            /*String sq2 = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (23, 'Sara', h, 10)";
            PreparedStatement ps2 = ConexionBBDD.getConnection().prepareStatement(sq2);
            
            ps2.executeUpdate();
            ps2.close();
        ConexionBBDD.getConnection().commit();
        ConexionBBDD.getConnection().setAutoCommit(true);
    }
    catch (SQLException e) {
            if (ConexionBBDD.getConnection() != null) {
            try {
                System.out.println("Error en la transaccion");
                ConexionBBDD.getConnection().rollback();
            } catch (SQLException exc) {
                System.out.println(exc.toString());
            }
        }
    }*/
}

/*como lo primero que hago es conectar la bbdd cuando la llamo desde el resto 
    de metodos esta connection ya no es null no?*/
 /* public static Connection conectarBBDD() {
        Scanner sc = new Scanner(System.in);

//        Connection con = null;
        try {
            //Conectar BBDD
            String cadena_conexion = "jdbc:mysql://localhost:3306/";
            String nombre_BBDD = "caraVScruz";
            String usuario = "root";
            String contrasenia = null;
            con = DriverManager.getConnection(cadena_conexion + nombre_BBDD, usuario, contrasenia);
        } catch (Exception e) {
            System.out.println("Muy mal >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return con;
    }
    
    public static void desconectarBBDD() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar conexion");
            System.out.println(ex.toString());
        }
    }*/
 /*public static ArrayList<Jugador> leerBBDDJugadores() {
        ArrayList<Jugador> jugadores_salida = new ArrayList<>();
        try {

            //Creamos 
            Statement stm = con.createStatement();

            //Query en string
            String myQueryJ = "Select * from jugador";
            ResultSet rsJ = stm.executeQuery(myQueryJ);
            System.out.println("id" + "\t" + "nombre" + "\t" + "PG" + "\t" + "PJ media");
            while (rsJ.next()) {
                System.out.println(rsJ.getInt(1) + "\t" + rsJ.getString(2) + "\t" + rsJ.getInt(3) + "\t" + rsJ.getInt(4));
                jugadores_salida.add(new Jugador(rsJ.getInt(1), rsJ.getString(2)));
                jugadores_salida.getLast().setPartidasGanadas(rsJ.getInt(3));
                jugadores_salida.getLast().setPartidasJugadas(rsJ.getInt(4));
            }
        } catch (Exception e) {
            System.out.println("Muy mal >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return jugadores_salida;
    }*/

 /*public static ArrayList<Torneo> leerBBDDTorneos(ArrayList<Jugador> jugadores_entrada) {
        ArrayList<Torneo> torneos_salida = new ArrayList<>();
        try {
            //Creamos 
            Statement stm = con.createStatement();
            String myQueryT = "Select * from torneo";
            ResultSet rsT = stm.executeQuery(myQueryT);
            System.out.println("id" + "\t" + "fecha" + "\t" + "num_max_jugadores");
            while (rsT.next()) {
                System.out.println(rsT.getInt(1) + "\t" + rsT.getInt(2) + "\t" + rsT.getInt(3));
                torneos_salida.add(new Torneo(rsT.getInt(1), rsT.getInt(2), rsT.getInt(3)));
            }

            String myQueryJxT = "Select * from jugadorXtorneo";
            ResultSet rsJxT = stm.executeQuery(myQueryJxT);
            System.out.println("id_j" + "\t" + "id_t" + "\t" + "posicion");
            while (rsJxT.next()) {
                System.out.println(rsJxT.getInt(1) + "\t" + rsJxT.getInt(2) + "\t" + rsJxT.getInt(3));
                for (Torneo torneo : torneos_salida) {
                    if (torneo.getId_t() == rsJxT.getInt(2)) {
                        for (Jugador jugador : jugadores_entrada) {
                            if (jugador.getId_j() == rsJxT.getInt(1)) {
                                torneo.inscribir(jugador);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Muy mal >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
        return torneos_salida;

    }*/

 /*public static void guardarBBDD(ArrayList<Jugador> jugadores, ArrayList<Torneo> torneos) {
        try {
            //Insertar Datos
            // Sentencia SQL con placeholders
            String sql = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), partidas_ganadas=VALUES(partidas_ganadas), partidas_jugadas=VALUES(partidas_jugadas)";
            PreparedStatement ps1 = con.prepareStatement(sql);
            for (int i = 0; i < jugadores.size(); i++) {
                // Establecemos los parámetros
                ps1.setInt(1, jugadores.get(i).getId_j());
                ps1.setString(2, jugadores.get(i).getNombre());
                ps1.setDouble(3, jugadores.get(i).getPartidasGanadas());
                ps1.setDouble(4, jugadores.get(i).getPartidasJugadas());
                // Ejecutamos la sentencia
                ps1.executeUpdate();
            }
            // Cerrar el PreparedStatement después de su uso
            ps1.close();

            String sq2 = "INSERT INTO torneo (id_t, fecha, num_max_jugadores) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE fecha=VALUES(fecha), num_max_jugadores=VALUES(num_max_jugadores)";
            PreparedStatement ps2 = con.prepareStatement(sq2);
            for (int i = 0; i < torneos.size(); i++) {
                // Establecemos los parámetros
                ps2.setInt(1, torneos.get(i).getId_t());
                ps2.setInt(2, torneos.get(i).getFecha());
                ps2.setInt(3, torneos.get(i).getNum_max_jugadores());
                // Ejecutamos la sentencia
                ps2.executeUpdate();
            }
            // Cerrar el PreparedStatement después de su uso
            ps2.close();

            String sq3 = "INSERT INTO jugadorXtorneo (id_j, id_t, posicion) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE posicion=VALUES(posicion)";
            PreparedStatement ps3 = con.prepareStatement(sq3);

            for (int i = 0; i < torneos.size(); i++) {
                torneos.get(i).ranking();//ordeno inscritos para guardar las posiciones
                for (int j = 0; j < torneos.get(i).getInscritos().size(); j++) {
                    // Establecemos los parámetros
                    ps3.setInt(1, torneos.get(i).getInscritos().get(j).getId_j());
                    ps3.setInt(2, torneos.get(i).getId_t());
                    ps3.setInt(3, j + 1);
                    // Ejecutamos la sentencia
                    ps3.executeUpdate();
                }
            }
            // Cerrar el PreparedStatement después de su uso
            ps3.close();

        } catch (Exception e) {
            System.out.println("Muy mal >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        }
    }*/
