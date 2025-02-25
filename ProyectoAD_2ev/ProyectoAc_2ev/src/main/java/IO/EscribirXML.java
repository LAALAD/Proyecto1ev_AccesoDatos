/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import java.io.File;
import java.text.SimpleDateFormat;
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
import org.w3c.dom.DOMException;
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
            // Crear el documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            DOMImplementation implementation = docBuilder.getDOMImplementation(); //sale uno del otro*/
            Document doc = implementation.createDocument("myNameSpace", "MisDatos", null); //namespace, elemento del raiz y el doctype
            doc.setXmlVersion("1.0");

            // Crear el elemento raíz <jugadores>
            Element misJugadores = doc.createElement("jugadores");

            // Recorrer la lista de jugadores y crear un nodo <jugador> para cada uno
            for (Jugador jugador : jugadores) {
                Element jugadorElement = doc.createElement("jugador");
                //rootElement.appendChild(jugadorElement);

                // Crear y añadir el nodo <id> con el identificador del jugador
                Element idElement = doc.createElement("id");
                //text es el contenido de las etiquetas
                Text textId = doc.createTextNode(String.valueOf(jugador.getId_j()));
                idElement.appendChild(textId);
                jugadorElement.appendChild(idElement);

                // Crear y añadir el nodo <nombre> con el nombre del jugador
                Element nombreElement = doc.createElement("nombre");
                Text textNombre = doc.createTextNode(jugador.getNombre());
                nombreElement.appendChild(textNombre);
                jugadorElement.appendChild(nombreElement);

                // Crear y añadir el nodo <partidasGanadas> con el número de partidas ganadas
                Element partidasGanadasElement = doc.createElement("partidasGanadas");
                Text textpartidasGanadas = doc.createTextNode(String.valueOf(jugador.getPartidasGanadas()));
                partidasGanadasElement.appendChild(textpartidasGanadas);
                jugadorElement.appendChild(partidasGanadasElement);

                // Crear y añadir el nodo <partidasJugadas> con el número de partidas jugadas
                Element partidasJugadasElement = doc.createElement("partidasJugadas");
                Text textpartidasJugadas = doc.createTextNode(String.valueOf(jugador.getPartidasJugadas()));
                partidasJugadasElement.appendChild(textpartidasJugadas);
                jugadorElement.appendChild(partidasJugadasElement);
                // Añade los datos personales en caso de q existan
                if (jugador.getDatosPersonales() != null) {
                    Element datosPersonalesElement = doc.createElement("datosPersonales");

                    // Apellido
                    Element apellidoElement = doc.createElement("apellido");
                    Text textApellido = doc.createTextNode(jugador.getDatosPersonales().getApellido());
                    apellidoElement.appendChild(textApellido);
                    datosPersonalesElement.appendChild(apellidoElement);

                    // Fecha de nacimiento
                    Element fechaNacimientoElement = doc.createElement("fechaNacimiento");
                    Text textFechaNacimiento = doc.createTextNode((new SimpleDateFormat("dd/MM/yyyy")).format(jugador.getDatosPersonales().getFechaNacimiento())); 
                    //.format para transformarse desde un date | .parse para transformarse desde un string
                    fechaNacimientoElement.appendChild(textFechaNacimiento);
                    datosPersonalesElement.appendChild(fechaNacimientoElement);

                    // Email
                    Element emailElement = doc.createElement("email");
                    Text textEmail = doc.createTextNode(jugador.getDatosPersonales().getEmail());
                    emailElement.appendChild(textEmail);
                    datosPersonalesElement.appendChild(emailElement);

                    // Teléfono
                    Element telefonoElement = doc.createElement("telefono");
                    Text textTelefono = doc.createTextNode(jugador.getDatosPersonales().getTelefono());
                    telefonoElement.appendChild(textTelefono);
                    datosPersonalesElement.appendChild(telefonoElement);

                    jugadorElement.appendChild(datosPersonalesElement);
                }

                //ultima linea para armar la estrucutura 
                misJugadores.appendChild(jugadorElement);
            }
            //
            doc.getDocumentElement().appendChild(misJugadores);

            Source source = new DOMSource(doc);
            //Result result = new StreamResult(new File("myJugadores.xml")); //no es como un File, asiq se debe crear la carpeta previamente

            File fichero = seleccionarFichero();
            //este if en caso de que el usuario le de a cancelar no explote
            if (fichero != null) {
                Result result = new StreamResult(fichero);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();

                //ací ce mete la zanjrí.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar sangría
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Nivel de sangría

                transformer.transform(source, result);
            }

        } catch (IllegalArgumentException | ParserConfigurationException | TransformerException | DOMException e) {
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
