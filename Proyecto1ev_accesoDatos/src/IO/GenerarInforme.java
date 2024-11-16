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
 * Clase para generar un informe en formato de texto de un torneo y sus
 * participantes. El informe se guarda en un archivo que el usuario selecciona
 * mediante un cuadro de diálogo.
 *
 * @author Usuario
 */
public class GenerarInforme {

    /**
     * Abre un cuadro de diálogo para seleccionar un archivo y devuelve el
     * archivo seleccionado.
     *
     * @return El archivo seleccionado por el usuario, o null si no se
     * seleccionó ningún archivo.
     */
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

    /**
     * Genera un informe detallado de un torneo y lo guarda en un archivo
     * seleccionado por el usuario.
     *
     * @param t El torneo del cual se generará el informe.
     * @return true si el informe se generó y guardó correctamente, false si
     * ocurrió un error.
     */
    public static boolean generarInformeTorneo(Torneo t) {
        // Llamamos a la función para seleccionar el archivo donde se guardará el informe.
        File f = seleccionarFichero();
        boolean flag = true; // Bandera que indica si el proceso fue exitoso.
        
        // Usamos try-with-resources para que el FileWriter se cierre automáticamente.
        try (FileWriter fw = new FileWriter(f);){ // Crear un FileWriter para escribir en el archivo seleccionado 

            // Escribir el encabezado del informe con el nombre y la fecha del torneo.            
            fw.write("TORNEO: " + t.getNombre() + "\t" + "FECHA: " + t.getFecha());
            fw.write("\n" + "\n" + "\n");// "\n" escribe un salto de linea
            fw.write("LISTA DE PARTICIPANTES:");
            fw.write("\n" + "\n");

            // Escribir los encabezados de las columnas de la tabla de participantes.
            fw.write("POSICION" + "\t" + "NOMBRE" + "\t" + "\t" + "PJ" + "\t" + "\t" + "PG" + "\t" + "\t" + "%VICTORIAS");
            fw.write("\n" + "\n");

            // Recorrer la lista de participantes del torneo.
            int pos = 0;
            for (Jugador participante : t.getInscritos()) {
                pos++; // Incrementar la posición del participante.
                // Escribir la información de cada participante en el archivo.
                fw.write(pos + ".- " + "\t" + "\t" + participante.getNombre() + "\t" + "\t"
                        + +participante.getPartidasJugadasTorneo() + "\t" + "\t"
                        + +participante.getPartidasGanadasTorneo() + "\t" + "\t"
                        + +participante.calcularWinRate());
                fw.write("\n" + "\n");
            }
        } catch (IOException ex) {
            // Manejo de excepciones si ocurre un error al abrir o escribir en el archivo.
            System.out.println("Error al abrir el fichero");
            flag = false;
            System.out.println("Error al guardar el fichero");
            return flag;
        } catch (NullPointerException ex) {
            // Si el usuario no seleccionó ningún archivo, mostrar un mensaje.
            flag = false;
            System.out.println("No se ha seleccionado ningun fichero, el registro no se ha guardado");
            return flag;
        } 
        System.out.println("Registro guardado");
        return flag;
    }

}
