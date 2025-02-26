/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import static IO.EscribirXML.seleccionarFichero;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import logicaNegocio.DatosPersonales;
import logicaNegocio.Jugador;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Clase encargada de leer archivos XML y extraer información almacenada en
 * ellos. Utiliza la API DOM para analizar y procesar documentos XML.
 */
public class LeerXML {

    /**
     * Método para cargar una lista de jugadores desde un fichero XML.
     *
     * @return Lista de objetos Jugador obtenidos del XML.
     */
    public static ArrayList<Jugador> leerJugadores() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try {
            // Cargar el fichero XML y parsearlo
            //File file = new File(ruta);
            File file = EscribirXML.seleccionarFichero();
            if (file != null) { //si se ha seleccionado un fichero
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
                    // Leer datos personales si existen
                    NodeList datosPersonalesList = jugadorElement.getElementsByTagName("datosPersonales");
                    if (datosPersonalesList.getLength() > 0) {
                        Element datosPersonalesElement = (Element) datosPersonalesList.item(0);

                        String apellido = datosPersonalesElement.getElementsByTagName("apellido").item(0).getTextContent();
                        String fechaNacimientoStr = datosPersonalesElement.getElementsByTagName("fechaNacimiento").item(0).getTextContent();
                        Date fechaNacimiento = (new SimpleDateFormat("dd/MM/yyyy")).parse(fechaNacimientoStr);
                        String email = datosPersonalesElement.getElementsByTagName("email").item(0).getTextContent();
                        String telefono = datosPersonalesElement.getElementsByTagName("telefono").item(0).getTextContent();

                        DatosPersonales datosPersonales = new DatosPersonales(apellido, fechaNacimiento, email, telefono);
                        jugador.setDatosPersonales(datosPersonales);

                    }

                    // Agregar el jugador a la lista
                    jugadores.add(jugador);
                }

                System.out.println("XML de jugadores cargado desde: " + file.getPath());
            } else {
                System.out.println("No se ha seleccionado ningún fichero");
            }
            //pARSER ES POR LA FECHA DE DATOS PERSONALES
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException | ParseException e) {
            System.out.println("Algo sucedió y no se pudo recuperar la información de los jugadores");
            System.out.println("Compruebe que el fichero seleccionnado está correctamente estructurado");
            //e.printStackTrace();
        }
        return jugadores;
    }
}
