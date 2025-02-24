
package xml_test1;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CocheHandler extends DefaultHandler {

    private StringBuilder value;

    public CocheHandler() {
        value = new StringBuilder();
    }

    //Código que se ejecuta antes de procesar la etiqueta
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.value.setLength(0);

        //Gestionar atributos de las etiquetas
        if (qName.equals("coche")) {
            String modelo= attributes.getValue("modelo");
            if(modelo != null){
              System.out.println("Atributo: "+attributes.getQName(0)+ ",Valor: "+modelo);  
            }
        }
    }

    //El que escribe los valores procesados
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.value.append(ch, start, length);
    }

    //Código que se ejecuta tras procesar la etiqueta
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Este Switch es una manera de filtrar los nodos de meta_informacion
        switch (qName) {
            case "coche":
                //Salto de línea entre coches.
                System.out.println("");
                break;
            case "matricula":
                System.out.println("Matricula: " + this.value.toString());
                break;
            case "marca":
                System.out.println("Marca: " + this.value.toString());
                break;
            case "precio":
                System.out.println("Precio: " + this.value.toString());
                break;
            default:
            //System.out.println("XXXXXXXXXXXXXX");  
        }
    }

}
