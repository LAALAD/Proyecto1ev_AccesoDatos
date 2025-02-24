/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logicaNegocio.Jugador;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author DAM2_02
 */
public class EscribirXML {

    public static void guardarJugadores(ArrayList<Jugador> jugadores) {
        try {

            //obejtos de las clases que se necesitan para trabajar con un xml
            //estrucutura del xml
            /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //
            DocumentBuilder builder = factory.newDocumentBuilder(); //sale del primero, 
            DOMImplementation implementation = builder.getDOMImplementation(); //sale uno del otro*/
            // Crear el documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            DOMImplementation implementation = docBuilder.getDOMImplementation(); //sale uno del otro*/
            Document doc = implementation.createDocument("myNameSpace", "concesionario", null); //namespace, elemento del raiz y el doctype
            doc.setXmlVersion("1.0");

            // Crear el elemento raíz <jugadores>
            Element rootElement = doc.createElement("jugadores");
            doc.appendChild(rootElement);

            // Recorrer la lista de jugadores y crear un nodo <jugador> para cada uno
            for (Jugador jugador : jugadores) {
                Element jugadorElement = doc.createElement("jugador");
                rootElement.appendChild(jugadorElement);

                // Crear y añadir el nodo <id> con el identificador del jugador
                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(jugador.getId_j())));
                jugadorElement.appendChild(idElement);

                // Crear y añadir el nodo <nombre> con el nombre del jugador
                Element nombreElement = doc.createElement("nombre");
                nombreElement.appendChild(doc.createTextNode(jugador.getNombre()));
                jugadorElement.appendChild(nombreElement);

                // Crear y añadir el nodo <partidasGanadas> con el número de partidas ganadas
                Element partidasGanadasElement = doc.createElement("partidasGanadas");
                partidasGanadasElement.appendChild(doc.createTextNode(String.valueOf(jugador.getPartidasGanadas())));
                jugadorElement.appendChild(partidasGanadasElement);

                // Crear y añadir el nodo <partidasJugadas> con el número de partidas jugadas
                Element partidasJugadasElement = doc.createElement("partidasJugadas");
                partidasJugadasElement.appendChild(doc.createTextNode(String.valueOf(jugador.getPartidasJugadas())));
                jugadorElement.appendChild(partidasJugadasElement);
            }

            // Configurar el transformador para escribir el documento en un fichero
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);            
            StreamResult result = new StreamResult(seleccionarFichero());
            //StreamResult result = new StreamResult(new File(ruta));
            
            transformer.transform(source, result);

            //System.out.println("XML de jugadores guardado en: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para guardar un jugador en un fichero XML.
     *
     * @param jugador Jugador a guardar.
     * @param ruta Ruta del fichero donde se guardará el XML.
     */
    public static void guardarJugador(Jugador jugador) {
        try {
            File file = seleccionarFichero();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;
            Element rootElement;

            // Si el fichero existe, se parsea el documento existente
            if (file.exists()) {
                doc = docBuilder.parse(file);
                rootElement = doc.getDocumentElement();
            } else {
                // Si no existe, se crea un nuevo documento con el elemento raíz <jugadores>
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("jugadores");
                doc.appendChild(rootElement);
            }

            // Crear el nodo <jugador> y sus elementos hijos
            Element jugadorElement = doc.createElement("jugador");
            rootElement.appendChild(jugadorElement);

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(jugador.getId_j())));
            jugadorElement.appendChild(idElement);

            Element nombreElement = doc.createElement("nombre");
            nombreElement.appendChild(doc.createTextNode(jugador.getNombre()));
            jugadorElement.appendChild(nombreElement);

            Element partidasGanadasElement = doc.createElement("partidasGanadas");
            partidasGanadasElement.appendChild(doc.createTextNode(String.valueOf(jugador.getPartidasGanadas())));
            jugadorElement.appendChild(partidasGanadasElement);

            Element partidasJugadasElement = doc.createElement("partidasJugadas");
            partidasJugadasElement.appendChild(doc.createTextNode(String.valueOf(jugador.getPartidasJugadas())));
            jugadorElement.appendChild(partidasJugadasElement);

            // Configurar el transformador para escribir el documento actualizado en el fichero
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            //System.out.println("Jugador añadido correctamente en: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public static File seleccionarFichero() {
        // Crear una instancia de JFileChooser, que es un cuadro de diálogo estándar para seleccionar archivos o directorios.
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto", "txt"));

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
}
