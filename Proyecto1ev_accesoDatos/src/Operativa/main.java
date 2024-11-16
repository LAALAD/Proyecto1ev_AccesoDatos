package Operativa;

import IO.InicializarBBDD;

/**
 * Clase principal de la aplicación que inicia la base de datos y despliega el
 * menú de opciones.
 */
public class main {

    /**
     * Método principal que inicializa la base de datos y muestra el menú para
     * interactuar con el usuario.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Crear la base de datos
        InicializarBBDD.crearBBDD();
        // Llamar al método de menú para que el usuario seleccione opciones
        Operativa.menu();
    }

}
