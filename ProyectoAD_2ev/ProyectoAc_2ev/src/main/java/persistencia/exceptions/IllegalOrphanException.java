package persistencia.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Excepción personalizada para manejar errores relacionados con referencias
 * huérfanas en la base de datos.
 *
 * Esta excepción se lanza cuando una entidad no puede ser eliminada o
 * modificada debido a que otras entidades dependen de ella, lo que resultaría
 * en registros huérfanos en la base de datos.
 */
public class IllegalOrphanException extends Exception {

    private List<String> messages;

    /**
     * Constructor de la excepción que recibe una lista de mensajes de error.
     *
     * @param messages Lista de mensajes de error explicando las razones de la
     * excepción.
     */
    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        } else {
            this.messages = messages;
        }
    }

    /**
     * Obtiene la lista de mensajes de error asociados a la excepción.
     *
     * @return Lista de mensajes explicando el motivo de la excepción.
     */
    public List<String> getMessages() {
        return messages;
    }
}
