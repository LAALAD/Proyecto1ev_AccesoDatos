package xml_test1;

import java.io.File;
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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class EscribirXML {

    public static void main(String[] args) {
        try {
            //obejtos de las clases que se necesitan para trabajar con un xml
            //estrucutura del xml
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //
            DocumentBuilder builder = factory.newDocumentBuilder(); //sale del primero, 
            DOMImplementation implementation = builder.getDOMImplementation(); //sale uno del otro

            //el namespace es ... una direccion unica? parecido a una URI, identificar las etiquetas de uno y otro
            Document documento = implementation.createDocument("myNameSpace", "concesionario", null); //namespace, elemento del raiz y el doctype
            documento.setXmlVersion("1.0");

            //formar documento
            //etiquetas del xml
            Element coches = documento.createElement("coches");
            
            Element coche = documento.createElement("coche");
            Element matricula = documento.createElement("matricula");
            //text es el contenido de las etiquetas
            Text textMatricula = documento.createTextNode("1111AAAA");

            //jerarquia, el append es para los hijos de la etiqueta                  
            matricula.appendChild(textMatricula);
            coche.appendChild(matricula);

            Element marca = documento.createElement("marca");
            Text textmarca = documento.createTextNode("AUDI");
            marca.appendChild(textmarca);
            coche.appendChild(marca);

            Element precio = documento.createElement("precio");
            Text textprecio = documento.createTextNode("3000");
            precio.appendChild(textprecio);
            coche.appendChild(precio);
            //ultima linea para armar la estrucutura 
            coches.appendChild(coche);

            //
            documento.getDocumentElement().appendChild(coches);

            Source source = new DOMSource(documento);
            Result result = new StreamResult(new File("myConcesionario.xml")); //no es como un File, asiq se debe crear la carpeta previamente

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            
            //ací ce mete la zanjrí.
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar sangría
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Nivel de sangría
            
            transformer.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
