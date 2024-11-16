package Operativa;


import IO.ConexionBBDD;
import Modelo.Jugador;
import IO.InicializarBBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author adria
 */
public class ejercicio_clase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = 
        ConexionBBDD.getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("Cuantos jugadores quiere crear? ");
        int num_jugadores = Integer.valueOf(sc.nextLine());
        String nombre;
        int pg;
        int id;
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 0; i < num_jugadores; i++) {
            System.out.println("Introduce un id: ");
            id = Integer.valueOf(sc.nextLine());
            System.out.println("Introduce un nombre: ");
            nombre = sc.nextLine();
            System.out.println("Introduce el numero de partidas ganadas: ");
            pg = Integer.valueOf(sc.nextLine());
            jugadores.add(new Jugador(id, nombre));
            jugadores.get(i).setPartidasGanadas(pg);
        }

        try {
            try {
                con.setAutoCommit(false);

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

            } catch (SQLException ex) {
                System.out.println("Muy mal >:>" + ex.toString());
                ex.printStackTrace();//muestra toda la info de la excepcion en rojo
            }

            String sq2 = "INSERT INTO jugador (id_j, nombre, partidas_ganadas, partidas_jugadas) VALUES (23, 'Sara', h, 10)";
            PreparedStatement ps2 = con.prepareStatement(sq2);
            
//            ps2.setInt(1, 23);
//            ps2.setString(2, "Ana");
//            ps2.setDouble(3, 40.0);
//            ps2.setDouble(4, 41.0);

            ps2.executeUpdate();
            ps2.close();

            
            con.commit();
            
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.out.println("Error en la transaccion");
                    con.rollback();
                } catch (SQLException exc) {
                    System.out.println(exc.toString());
                }
            }
        }

        
        ConexionBBDD.desconectarBBDD();
    }

}
