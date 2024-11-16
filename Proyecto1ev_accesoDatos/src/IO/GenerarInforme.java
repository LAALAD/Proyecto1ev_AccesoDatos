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
        JFileChooser fileChooser = new JFileChooser();

        // Establecer un filtro para archivos (por ejemplo, solo mostrar archivos .txt)
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        //fileChooser.setFileFilter(filter);
        //int result;
        //do {
        // Mostrar el diálogo de selección de archivo (para abrir un archivo)
        //   result = fileChooser.showOpenDialog(null);
        // } while (result != JFileChooser.APPROVE_OPTION);
        // Obtener el archivo seleccionado
        //return fileChooser.getSelectedFile();

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
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
            fw.write("POSICION"  + "\t" + "\t" + "PJ" + "\t"+ "\t" + "PG" + "\t" + "\t" + "%VICTORIAS");
            fw.write("\n" + "\n");
            int pos = 0;
            for (Jugador participante : t.getInscritos()) {
                pos++;                
                fw.write(pos + ".- " + "\t" + "\t"  + participante.getNombre()  + "\t" + "\t" +
                        + participante.getPartidasJugadasTorneo() +  "\t" + "\t" +
                        + participante.getPartidasGanadasTorneo() +  "\t" + "\t" +
                        + participante.calcularWinRate());
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
