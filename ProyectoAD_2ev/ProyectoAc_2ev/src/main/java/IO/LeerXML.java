/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import logicaNegocio.Jugador;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author DAM2_02
 */
public class LeerXML {

    /**
     * Método para cargar una lista de jugadores desde un fichero XML.
     *
     * @param ruta Ruta del fichero XML a cargar.
     * @return Lista de objetos Jugador obtenidos del XML.
     */
    public static ArrayList<Jugador> leerJugadores() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try {
            // Cargar el fichero XML y parsearlo
            //File file = new File(ruta);
            File file = EscribirXML.seleccionarFichero();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            //doc.getDocumentElement().normalize(); //para que es esto??

            // Se asume que el XML tiene un elemento raíz <jugadores> y varios elementos <jugador>
            NodeList jugadoresList = doc.getElementsByTagName("jugador");
            for (int i = 0; i < jugadoresList.getLength(); i++) {
                Element jugadorElement = (Element) jugadoresList.item(i);

                // Leer los datos de cada jugador desde los nodos <id>, <nombre>, <partidasGanadas> y <partidasJugadas>
                //String idStr = jugadorElement.getElementsByTagName("id").item(0).getTextContent();
                String nombre = jugadorElement.getElementsByTagName("nombre").item(0).getTextContent();
                String partidasGanadasStr = jugadorElement.getElementsByTagName("partidasGanadas").item(0).getTextContent();
                String partidasJugadasStr = jugadorElement.getElementsByTagName("partidasJugadas").item(0).getTextContent();

               
                
                
                // Crear el objeto Jugador (utilizando el constructor que recibe el nombre)
                Jugador jugador = new Jugador(nombre);
                // Asignar el id (opcional, en caso de que se necesite)
                //jugador.setId_j(Integer.parseInt(idStr));
                
                 // Convertir los valores de partidas ganadas y jugadas
                int partidasGanadas = (int) Double.parseDouble(partidasGanadasStr);
                int partidasJugadas = (int) Double.parseDouble(partidasJugadasStr);
                // Asignar estadísticas de partidas
                jugador.setPartidasGanadas(partidasGanadas);
                jugador.setPartidasJugadas(partidasJugadas);

                // Agregar el jugador a la lista
                jugadores.add(jugador);
            }

            System.out.println("XML de jugadores cargado desde: " + file.getPath());
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return jugadores;
    }
}
