package xml_test1;

import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

public class CrearXML2 {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();

            Document documento = implementation.createDocument(null, "concesionario", null);
            documento.setXmlVersion("1.0");

            Element coches = documento.createElement("coches");

            //Este ejemplo lee de teclado, pero podría leer de cualquier tipo de fichero/bbdd/o estructura en memoria en general
            Scanner sc = new Scanner(System.in);
            System.out.println("Cuantos coches quiere crear");
            int n = Integer.valueOf(sc.nextLine());

            for (int i = 1; i <= n; i++) {
                System.out.println("Creando el coche nº " + i);
                Element coche = documento.createElement("coche");

                System.out.println("Introduzca Matricula: ");
                Element matricula = documento.createElement("matricula");
                Text textMatricula = documento.createTextNode(sc.nextLine());
                matricula.appendChild(textMatricula);
                coche.appendChild(matricula);

                System.out.println("Introduzca Marca: ");
                Element marca = documento.createElement("marca");
                Text textMarca = documento.createTextNode(sc.nextLine());
                marca.appendChild(textMarca);
                coche.appendChild(marca);

                System.out.println("Introduzca Precio: ");
                Element precio = documento.createElement("precio");
                Text textPrecio = documento.createTextNode(sc.nextLine());
                precio.appendChild(textPrecio);
                coche.appendChild(precio);

                coches.appendChild(coche);
            }

            documento.getDocumentElement().appendChild(coches);

            Source source = new DOMSource(documento);
            Result result = new StreamResult(new File("./FICHEROS/myConcesionario.xml"));

            //Si quiero crear en modo append, puedo hacer esto, pero realmente no me funciona porque me crea en el mismo fichero, un xml y luego otro.
            //Yo leeria el xml, lo cargaria en memoria y luego lo guardiaría de nuevo con los hijos nuevmos.
            //Result result = new StreamResult(new FileOutputStream(new File("./FICHEROS/myConcesionario.xml"), true ));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
