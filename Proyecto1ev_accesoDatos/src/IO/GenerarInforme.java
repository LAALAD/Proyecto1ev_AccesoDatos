/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import Modelo.Jugador;
import Modelo.Torneo;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Usuario
 */
public class GenerarInforme {

    public static File seleccionarFichero() {
        // Crear una instancia de JFileChooser, que es un cuadro de diálogo estándar para seleccionar archivos o directorios.
        JFileChooser fileChooser = new JFileChooser();

        // Mostrar el cuadro de diálogo de selección de archivo. 
        // El método showOpenDialog(null) muestra el cuadro de diálogo para abrir un archivo y no tiene un componente padre (usa null).
        int result = fileChooser.showOpenDialog(null);

        // Comprobar el resultado del cuadro de diálogo.
        // JFileChooser.APPROVE_OPTION indica que el usuario seleccionó un archivo y presionó "Aceptar".
        if (result == JFileChooser.APPROVE_OPTION) {
            // Si el usuario seleccionó un archivo, devolverlo como un objeto File.
            return fileChooser.getSelectedFile();
        } else {
            // Si el usuario canceló el cuadro de diálogo, devolver null.
            return null;
        }
    }

    public static boolean generarInformeTorneo(Torneo t) {
        File f = seleccionarFichero();
        boolean flag = true;
        FileWriter fw = null;//FileWriter es una clase para escribir
        try {
            fw = new FileWriter(f);
            fw.write("TORNEO: " + t.getNombre() + "\t" + "FECHA: " + t.getFecha());
            fw.write("\n" + "\n" + "\n");// "\n" escribe un salto de linea
            fw.write("LISTA DE PARTICIPANTES:");
            fw.write("\n" + "\n");
            fw.write("POSICION" + "\t" + "\t" + "PJ" + "\t" + "\t" + "PG" + "\t" + "\t" + "%VICTORIAS");
            fw.write("\n" + "\n");
            int pos = 0;
            for (Jugador participante : t.getInscritos()) {
                pos++;
                fw.write(pos + ".- " + "\t" + "\t" + participante.getNombre() + "\t" + "\t"
                        + +participante.getPartidasJugadasTorneo() + "\t" + "\t"
                        + +participante.getPartidasGanadasTorneo() + "\t" + "\t"
                        + +participante.calcularWinRate());
                fw.write("\n" + "\n");
            }
        } catch (IOException ex) {
            System.out.println("Error al abrir el fichero");
            flag = false;
            System.out.println("Error al guardar el fichero");
            return flag;
        } catch (NullPointerException ex) {
            flag = false;
            System.out.println("No se ha seleccionado ningun fichero, el registro no se ha guardado");
            return flag;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    System.out.println("Error al cerrar el fichero");
                }
            }
        }
        System.out.println("Registro guardado");
        return flag;
    }

}
