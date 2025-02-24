package xml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class XML_Escribir {
    public static void main(String[] args) {
        try {
            // Crear instancia de DocumentBuilderFactory para configurar el parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // Crear un DocumentBuilder para construir el documento XML
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Obtener implementaci�n DOM para manipular el modelo de documento
            DOMImplementation implementation = builder.getDOMImplementation();

            // Crear el documento XML con un elemento ra�z llamado "fichero"
            Document documento = implementation.createDocument(null, "fichero", null);
            documento.setXmlVersion("1.0"); // Establecer la versi�n XML

            // Crear elemento principal "coches"
            Element coches = documento.createElement("coches");
            documento.getDocumentElement().appendChild(coches); // Agregar "coches" como hijo del elemento ra�z

            // Crear elemento "coche" y agregarlo a "coches"
            Element coche = documento.createElement("coche");
            coches.appendChild(coche);

            // Crear elemento "matricula" con un nodo de texto
            Element matricula = documento.createElement("matricula");
            Text textMatricula = documento.createTextNode("1111AAAA"); // Nodo de texto con valor de matr�cula
            matricula.appendChild(textMatricula); // Agregar el texto al elemento "matricula"
            coche.appendChild(matricula); // Agregar "matricula" como hijo de "coche"

            // Crear atributo "marca" con valor "Volvo" y asociarlo a "coche"
            Attr marca = documento.createAttribute("marca"); // Crear atributo "marca"
            marca.setValue("Volvo"); // Establecer el valor del atributo
            coche.setAttributeNode(marca); // Asignar el atributo al elemento "coche"

            // Configurar Transformer para guardar el XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Configurar salida con sangr�a para que sea legible
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar sangr�a
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Nivel de sangr�a

            // Especificar fuente y destino del archivo XML
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(new File("coches.xml")); // Archivo de salida

            // Transformar y guardar el contenido del documento XML
            transformer.transform(source, result);

            // Mensaje de �xito
            System.out.println("Archivo XML creado correctamente.");
        } catch (ParserConfigurationException | TransformerException ex) {
            // Manejo de excepciones y registro de errores
            Logger.getLogger(XML_Escribir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
