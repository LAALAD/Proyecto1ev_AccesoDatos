/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos. Utiliza el
 * patrón Singleton para asegurar que solo haya una conexión activa.
 *
 * <p>
 * Esta clase proporciona métodos para obtener una conexión a la base de datos y
 * para cerrarla de manera controlada.</p>
 *
 * @author adria
 */
public class ConexionBBDD {

    // Instancia estática que mantiene la conexión activa.
    private static Connection con = null;

    // Constructor privado para evitar instanciación externa
    private ConexionBBDD() {
    }

    /**
     * Método para obtener la conexión a la base de datos. Si no existe una
     * conexión activa, se crea una nueva.
     *
     * <p>
     * Este método implementa el patrón Singleton, asegurando que solo se crea
     * una única instancia de la conexión durante toda la ejecución de la
     * aplicación.</p>
     *
     * @return La conexión a la base de datos.
     */
    public static Connection getConnection() {
        if (con == null) {
            try {                
                // Cadena de conexión para conectarse a MySQL.
                String cadena_conexion = "jdbc:mysql://localhost:3306/";
                // Configuración de la base de datos (en este caso, se usa una base de datos local sin nombre definido).
                String usuario = "root";
                String contrasenia = null;
                
                // Establecer la conexión con la base de datos.
                con = DriverManager.getConnection(cadena_conexion, usuario, contrasenia);
            } catch (SQLException e) {
                // En caso de error, se imprime el detalle de la excepción.
                System.out.println("Error al conectar a la base de datos: " + e.toString());
                e.printStackTrace();//muestra toda la info de la excepcion en rojo
            } catch (Exception e){ // por si no se encuentra el jdbc u otras excepciones
                System.out.println("Error: " + e.toString());
                e.printStackTrace();//muestra toda la info de la excepcion en rojo
            }
        }
        return con;
    }

    
    /**
     * Método para desconectar la base de datos. Cierra la conexión activa si 
     * existe una.
     * 
     * <p>Este método asegura que se liberen los recursos de la conexión a la 
     * base de datos cuando ya no se necesiten.</p>
     */
    public static void desconectarBBDD() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar conexion");
            System.out.println(ex.toString());
        }
    }
}
