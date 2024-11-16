package Operativa;

import IO.ConexionBBDD;
import IO.InicializarBBDD;
import Modelo.Torneo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.*;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author adria
 */
public class main {

    public static void main(String[] args) throws IOException {
        InicializarBBDD.crearBBDD();
        Menu.menu();
    }

    public static Torneo deserializarTorneoJugado(int idTorneo) throws IOException {
        Torneo torneo = null;

        // Consulta SQL para recuperar el objeto serializado
        String sql = "SELECT torneo_data FROM torneoSerializado WHERE id_t = ?";

        try (Connection conn = ConexionBBDD.getConnection(); // Obtiene la conexión
                 PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTorneo); // Establece el parámetro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] torneoData = rs.getBytes("torneo_data"); // Recupera el array de bytes

                    // Deserializar el objeto
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(torneoData); ObjectInputStream ois = new ObjectInputStream(bis)) {

                        torneo = (Torneo) ois.readObject(); // Convierte el array de bytes de nuevo a objeto
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error al deserializar el objeto: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No se encontró un torneo con el ID especificado: " + idTorneo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        return torneo; // Devuelve el objeto Torneo deserializado
    }
}
