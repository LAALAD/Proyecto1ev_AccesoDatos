package persistencia.exceptions;

/**
 * Excepción personalizada para manejar errores cuando se intenta crear una
 * entidad que ya existe en la base de datos.
 *
 * Se lanza cuando se intenta persistir un registro con una clave primaria
 * duplicada o un identificador único ya existente.
 */
public class PreexistingEntityException extends Exception {

    /**
     * Constructor que recibe un mensaje de error y la causa de la excepción.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause La causa original de la excepción.
     */
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que recibe solo un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public PreexistingEntityException(String message) {
        super(message);
    }
}
