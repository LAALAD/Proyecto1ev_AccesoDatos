package xml;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class XML_Leer {
    public static void main(String[] args) {
        try {
            // Crear instancia de DocumentBuilderFactory para configurar el parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crear un DocumentBuilder para leer el documento XML
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Cargar el archivo XML en un objeto Document
            File file = new File("coches.xml"); // Archivo a leer
            Document documento = builder.parse(file);

            // Normalizar el documento XML
            documento.getDocumentElement().normalize();

            // Obtener el elemento raíz
            Element raiz = documento.getDocumentElement();
            System.out.println("Elemento raíz: " + raiz.getNodeName());

            // Obtener todos los elementos "coche"
            NodeList listaCoches = documento.getElementsByTagName("coche");

            // Recorrer la lista de elementos "coche"
            for (int i = 0; i < listaCoches.getLength(); i++) {
                Node nodoCoche = listaCoches.item(i);

                if (nodoCoche.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoCoche = (Element) nodoCoche;

                    // Imprimir atributo "marca"
                    String marca = elementoCoche.getAttribute("marca");
                    System.out.println("Marca: " + marca);

                    // Obtener y mostrar el elemento "matricula"
                    NodeList listaMatricula = elementoCoche.getElementsByTagName("matricula");
                    if (listaMatricula.getLength() > 0) {
                        Element elementoMatricula = (Element) listaMatricula.item(0);
                        System.out.println("Matrícula: " + elementoMatricula.getTextContent());
                    }
                }
            }
        } catch (Exception ex) {
            // Manejo de excepciones y registro de errores
            Logger.getLogger(XML_Leer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
