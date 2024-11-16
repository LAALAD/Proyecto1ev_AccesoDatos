/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author adria
 */
public class ConexionBBDD {

    private static Connection con = null;

    private ConexionBBDD() {
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                //Conectar BBDD
                String cadena_conexion = "jdbc:mysql://localhost:3306/";
                //String nombre_BBDD = "caraVScruz";
                String usuario = "root";
                String contrasenia = null;
                con = DriverManager.getConnection(cadena_conexion , usuario, contrasenia);
            } catch (Exception e) {
                System.out.println("Muy mal >:>" + e.toString());
                e.printStackTrace();//muestra toda la info de la excepcion en rojo
            }
        }
        return con;
    }

    /*public static String getNOMBRE_BBDD() {
        return NOMBRE_BBDD;
    }*/
    
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
