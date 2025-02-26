package persistencia.exceptions;

/**
 * Excepción personalizada para manejar errores cuando una entidad no existe en
 * la base de datos.
 *
 * Se lanza cuando se intenta acceder, modificar o eliminar un registro que no
 * está presente en la base de datos.
 */
public class NonexistentEntityException extends Exception {

    /**
     * Constructor que recibe un mensaje de error y la causa de la excepción.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause La causa original de la excepción.
     */
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que recibe solo un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public NonexistentEntityException(String message) {
        super(message);
    }
}
