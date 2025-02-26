/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectoac_2ev;

import VistaControlador.Principal;
import VistaControlador.MenuMinijuegos;
import VistaControlador.Ventana_Gracias;
import static com.mycompany.proyectoac_2ev.Validaciones.validarFechaFutura;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import logicaNegocio.*;

/**
 * Clase principal del proyecto ProyectoAc_2ev.
 *
 * Sirve como punto de entrada para la ejecución del programa.
 */
public class ProyectoAc_2ev {

    private static ControladorLogico control = new ControladorLogico();

    private static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<Torneo> torneos = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    /**
     * Método principal que inicia la aplicación.
     *
     * Crea una instancia de la clase `Principal` y la hace visible.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta
     * aplicación).
     */
    public static void main(String[] args) {
        Principal p1 = new Principal();
        p1.setVisible(true);

    }

    /**
     * Inicia la API del sistema, ejecutando las entradas de prueba y mostrando
     * el menú principal.
     *
     * Llama a los métodos `entradasPrueba()` y `menu()` para configurar y
     * mostrar la interfaz del usuario.
     */
    public static void iniciarApi() {
        entradasPrueba();
        menu();
    }

    /**
     * Muestra el menú principal que permite navegar entre los menús de
     * Jugadores, Torneos y Partidas. Establece la conexión con la base de datos
     * y carga la lista de jugadores y torneos.
     */
    public static void menu() {
        jugadores = control.leerTodosJugadores();
        torneos = control.leerTodosTorneos();
        System.out.println("¿Desea añadir jugadores básicos de prueba? (s/n)");
        String res = sc.nextLine();
        if (res.equalsIgnoreCase("s")) {
            ArrayList<Jugador> jugadores_aux = IO.LeerXML.leerJugadores();
            if (!jugadores_aux.isEmpty()) { //si se ha encontrado algún jugador
                int entradas_encontradas = 0;
                for (Jugador jugador : jugadores_aux) {
                    control.crearJugador(jugador);
                    jugadores.add(jugador);
                    entradas_encontradas++;
                }
                System.out.println("¡Se han añadido " + entradas_encontradas + " jugadores exitosamente!");
            }
        }

        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Menu Jugador");
            System.out.println("2.- Menu Torneo");
            System.out.println("3.- Menu Partida");
            System.out.println("4.- Menu Arbitro");
            System.out.println("5.- Guardar Jugadores Existentes");
            System.out.println("6.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    menuJugador();
                    break;
                case "2":
                    menuTorneo();
                    break;
                case "3":
                    menuPartida();
                    break;
                case "4":
                    menuArbitro();
                    break;
                case "5":
                    IO.EscribirXML.guardarJugadores(jugadores);
                    break;
                case "6":
                    System.out.println("¿Desea ser BETA TESTER de nuestro proximo juego? (s/n)");
                    String respuesta = sc.nextLine();
                    if (respuesta.equalsIgnoreCase("s")) {
                        VistaControlador.MenuMinijuegos minijuego = new MenuMinijuegos();
                        minijuego.setLocationRelativeTo(null);
                        minijuego.setVisible(true);

                    } else {
                        Ventana_Gracias gracias = new Ventana_Gracias();
                        gracias.setVisible(true);
                        //JOptionPane.showMessageDialog(null, "¡Gracias por utilizar nuestra aplicación!");
                    }
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("6")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /*private static void jugarMiniJuego() {
        System.out.println("¿Desea ser BETA TESTER de nuestro proximo juego? (s/n)");
        String respuesta = sc.nextLine();
        if (respuesta.equals("s")) {
            Minijuego juego = new Minijuego();
            juego.setVisible(true);
        }
    }*/
    /**
     * Muestra el menú de opciones para gestionar los jugador. Permite crear,
     * eliminar, modificar, buscar, listar jugadores.
     */
    public static void menuJugador() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Crear Jugador");
            System.out.println("2.- Eliminar Jugador");
            System.out.println("3.- Modificar Jugador");
            System.out.println("4.- Buscar Jugador");
            System.out.println("5.- Listar Jugadores en BBDD");
            System.out.println("6.- Añadir Datos Personales del Jugador");
            System.out.println("7.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    crearJugador();
                    break;
                case "2":
                    eliminarJugador();
                    break;
                case "3":
                    modificarJugador();
                    break;
                case "4":
                    buscarJugador();
                    break;
                case "5":
                    imprimirJugadores(jugadores);
                    //imprimirJugadores(j.listarJugadores());
                    break;
                case "6":
                    imprimirJugadores(jugadores);
                    Jugador jugador = seleccionarJugador();
                    guardarDatosPersonales(jugador);
                    break;
                case "7":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("7")); //Mientras seleccione un numero distinto de 6 seguir el bucle
    }

    /**
     * Muestra el menú de opciones para gestionar los torneos. Permite crear,
     * eliminar, modificar, buscar, listar torneos y mostrar estadísticas.
     */
    public static void menuTorneo() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Crear Torneo");
            System.out.println("2.- Eliminar Torneo");
            System.out.println("3.- Modificar Torneo");
            System.out.println("4.- Buscar Torneo");
            System.out.println("5.- Listar Torneos");
            System.out.println("6.- Mostrar Top 3 Torneo");
            System.out.println("7.- Mostrar Estadísticas Torneo");
            System.out.println("8.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    crearTorneo();
                    break;
                case "2":
                    eliminarTorneo();
                    break;
                case "3":
                    modificarTorneo();
                    break;
                case "4":
                    buscarTorneo();
                    break;
                case "5":
                    imprimirTorneos(torneos);
                    //imprimirTorneos(t.listarTorneos()); //sin jugadores inscritos
                    break;
                case "6":
                    imprimirTorneos(jugados());
                    if (!jugados().isEmpty()) {//si hay algun torneo jugado
                        seleccionarTorneo(jugados()).top3();//muestro el top3
                    }
                    break;
                case "7":
                    imprimirTorneos(jugados());
                    if (!jugados().isEmpty()) {
                        seleccionarTorneo(jugados()).stats();
                    }
                    //ª
                    break;
                case "8":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("8")); //Mientras seleccione un numero distinto de 8 seguir el bucle
    }

    /**
     * Muestra el menú de opciones para gestionar las partidas. Permite
     * inscribir jugadores a torneos y jugar torneos con diferentes modalidades.
     */
    public static void menuPartida() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Inscribir Jugador a Torneo");
            System.out.println("2.- Jugar Torneo Moneda");
            System.out.println("3.- Jugar Torneo Dado");
            System.out.println("4.- Jugar Torneo Piedra, Papel o Tijera");
            System.out.println("5.- Jugar Torneo Black Jack");
            System.out.println("6.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    inscribir();
                    break;
                case "2":
                    jugarTorneo();
                    break;
                case "3":
                    jugarTorneoDado();
                    break;
                case "4":
                    jugarTorneoPiedraPapelTijera();
                    break;
                case "5":
                    jugarTorneoBlackJack();
                    break;
                case "6":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }
        } while (!opcion.equals("6")); //Mientras seleccione un numero distinto de 3 seguir el bucle
    }

    /**
     * Crea un nuevo jugador con un nombre asignado por el usuario y un ID
     * generado.
     */
    private static void crearJugador() {
        System.out.println("Introduce el nombre del jugador: ");
        String nombre = sc.nextLine();
        jugadores.add(new Jugador(nombre));

        //rellena la ficha si quieres, de manera opcional
        System.out.println("Deseas rellenar la ficha del jugador s/n");
        String opcion = sc.nextLine();
        if (opcion.equalsIgnoreCase("s")) {
            //rellena los datos personales del ultimo jugadore creado devolviendo un tipo de dato Datos Personales
            jugadores.getLast().setDatosPersonales(rellenarDatosPersonales());
        }

        control.crearJugador(jugadores.getLast());
    }

    /**
     * Solicita al usuario que ingrese datos personales y los almacena en un
     * objeto `DatosPersonales`.
     *
     * Se solicitan el apellido, fecha de nacimiento, email y número de
     * teléfono. La fecha debe ser válida y anterior a la fecha actual.
     *
     * @return Un objeto `DatosPersonales` con los datos ingresados por el
     * usuario.
     */
    private static DatosPersonales rellenarDatosPersonales() {
        System.out.println("Introduce el apellido: ");
        String apellido = sc.nextLine();

        Date fecha = Validaciones.validarFechaPasada();

        System.out.println("Introduce un email: ");
        String email = sc.nextLine();

        System.out.println("Introduce el telefono: ");
        String telefono = sc.nextLine();
        DatosPersonales dp = new DatosPersonales(apellido, fecha, email, telefono);
        //control.crearDatosPersonales(dp);
        return dp;
    }

    //Metodo que guarda en la base de datos los datos personales del jugador
    /**
     * Asigna los datos personales ingresados a un jugador y los guarda en la
     * base de datos.
     *
     * Se llama a `rellenarDatosPersonales()` para obtener los datos ingresados
     * por el usuario y luego se asocian al jugador. La actualización del
     * jugador se realiza automáticamente en la base de datos gracias a la
     * configuración de `CascadeType`.
     *
     * @param jugador El objeto `Jugador` al que se le asignarán los datos
     * personales.
     */
    private static void guardarDatosPersonales(Jugador jugador) {
        DatosPersonales datosP = rellenarDatosPersonales();
        jugador.setDatosPersonales(datosP);

        //control.editarDatosPersonales(datosP); //NO HACE FALTA GRACIAS AL CASCADE
        control.editarJugador(jugador);
    }

    /**
     * Elimina un jugador de la lista, si existe.
     */
    private static void eliminarJugador() {
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(jugadores);
        System.out.println("Para el jugador a eliminar...");
        Jugador eliminar = seleccionarJugador();
        jugadores.remove(eliminar);
        control.eliminarJugador(eliminar.getId_j());
    }

    /**
     * Modifica los datos de un jugador (nombre).
     */
    private static void modificarJugador() {
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(jugadores);
        System.out.println("Para el jugador a modificar: ");
        Jugador modif = seleccionarJugador();
        System.out.println("Introduce el nuevo nombre del jugador: ");
        modif.setNombre(sc.nextLine());
        control.editarJugador(modif);
        //j.modificarJugador(modif);
    }

    /**
     * Busca un jugador en la base de datos por su ID y muestra sus detalles.
     */
    private static void buscarJugador() {
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        System.out.println("Introduzca el id del jugador a buscar:");
        //Jugador encontrado = j.buscarJugador(asignarEntero());
        Jugador encontrado = control.leerJugador(Validaciones.asignarEntero());
        if (encontrado == null) {
            System.out.println("No hay jugadores con el id indicado");
            return;
        }
        imprimirJugador(encontrado);
    }

    /**
     * Permite seleccionar un jugador de la lista de jugadores registrados en
     * memoria. El método solicita al usuario que ingrese el ID de un jugador y
     * verifica si el jugador existe en la lista. Si el jugador existe, lo
     * devuelve; de lo contrario, vuelve a solicitar la entrada del usuario
     * hasta que se ingrese un ID válido.
     *
     * @return El objeto Jugador correspondiente al ID seleccionado por el
     * usuario.
     */
    public static Jugador seleccionarJugador() {//busca en memoria
        int id_j_aux;

        // Bucle para solicitar un ID de jugador válido
        do {
            System.out.println("Selecciona el id de un jugador: ");
            id_j_aux = Validaciones.asignarEntero(); // Obtener el ID ingresado por el usuario

            // Buscar el jugador en la lista de jugadores
            for (Jugador it : jugadores) {
                // Si el ID coincide con algún jugador en la lista, devolver ese jugador
                if (it.getId_j() == id_j_aux) {
                    return it; // Jugador encontrado, se devuelve el objeto
                }
            }
            // Si no se encuentra el jugador con el ID proporcionado, mostrar mensaje de error
            System.out.println("Id inexistente...");
        } while (true);  // El bucle continúa hasta que se ingresa un ID válido

    }

    /**
     * Permite seleccionar un torneo de la lista de torneos registrados en
     * memoria. El método solicita al usuario que ingrese el ID de un torneo y
     * verifica si el torneo existe en la lista. Si el torneo existe, lo
     * devuelve; de lo contrario, vuelve a solicitar la entrada del usuario
     * hasta que se ingrese un ID válido.
     *
     * @return El objeto Torneo correspondiente al ID seleccionado por el
     * usuario.
     */
    public static Torneo seleccionarTorneo() { //busca en memoria
        // Bucle para solicitar un ID de torneo válido
        do {
            // Solicitar al usuario que ingrese un ID de torneo
            System.out.println("Selecciona el id de un torneo: ");
            int id_t_aux = Validaciones.asignarEntero(); // Obtener el ID ingresado por el usuario

            // Buscar el torneo en la lista de torneos
            for (Torneo it : torneos) {
                // Si el ID coincide con algún torneo en la lista, devolver ese torneo
                if (it.getId_t() == id_t_aux) {
                    return it; // Torneo encontrado, se devuelve el objeto
                }
            }
            System.out.println("Id no valido...");
            // Si no se encuentra el torneo con el ID proporcionado, se continuará el bucle
        } while (true);// El bucle continúa hasta que se ingresa un ID válido
    }

    /**
     * Permite seleccionar un jugador de la lista de jugadores proporcionada. El
     * método solicita al usuario que ingrese el ID de un jugador y verifica si
     * el jugador existe en la lista. Si el jugador existe, lo devuelve; de lo
     * contrario, vuelve a solicitar la entrada del usuario hasta que se ingrese
     * un ID válido.
     *
     * @param jugadores Lista de jugadores donde buscar el jugador por su ID.
     * @return El objeto Jugador correspondiente al ID seleccionado por el
     * usuario.
     */
    public static Jugador seleccionarJugador(ArrayList<Jugador> jugadores) {//busca en memoria
        int id_j_aux;
        // Bucle para solicitar un ID de jugador válido
        do {
            // Solicitar al usuario que ingrese un ID de jugador
            System.out.println("Selecciona el id de un jugador: ");
            id_j_aux = Validaciones.asignarEntero();// Obtener el ID ingresado por el usuario

            // Buscar el jugador en la lista de jugadores
            for (Jugador it : jugadores) {
                // Si el ID coincide con algún jugador en la lista, devolver ese jugador
                if (it.getId_j() == id_j_aux) {
                    return it;// Jugador encontrado, se devuelve el objeto
                }
            }
            // Si no se encuentra el jugador con el ID proporcionado, se continuará el bucle
            System.out.println("Id no valido...");
        } while (true);  // El bucle continúa hasta que se ingresa un ID válido
    }

    /**
     * Permite seleccionar un torneo de una lista de torneos proporcionada. El
     * método solicita al usuario que ingrese el ID de un torneo y verifica si
     * el torneo existe en la lista. Si el torneo existe, lo devuelve; de lo
     * contrario, vuelve a solicitar la entrada del usuario hasta que se ingrese
     * un ID válido.
     *
     * @param torneos Lista de torneos donde buscar el torneo por su ID.
     * @return El objeto Torneo correspondiente al ID seleccionado por el
     * usuario.
     */
    public static Torneo seleccionarTorneo(ArrayList<Torneo> torneos) { //busca en memoria
        // Bucle para solicitar un ID de torneo válido
        do {
            // Solicitar al usuario que ingrese un ID de torneo
            System.out.println("Selecciona el id de un torneo: ");
            int id_t_aux = Validaciones.asignarEntero();// Obtener el ID ingresado por el usuario

            // Buscar el torneo en la lista de torneos
            for (Torneo it : torneos) {
                // Si el ID coincide con algún torneo en la lista, devolver ese torneo
                if (it.getId_t() == id_t_aux) {
                    return it; // Torneo encontrado, se devuelve el objeto
                }
            }
            // Si no se encuentra el torneo con el ID proporcionado, se continuará el bucle
            System.out.println("Id no valido...");
        } while (true); // El bucle continúa hasta que se ingresa un ID válido
    }

    /**
     * Imprime en consola la lista de jugadores con su ID y nombre. Si la lista
     * de jugadores está vacía, se muestra un mensaje indicando que no hay
     * jugadores disponibles. Si la lista contiene jugadores, se imprimen sus
     * IDs y nombres en formato tabular.
     *
     * @param jugadores La lista de jugadores a imprimir.
     */
    public static void imprimirJugadores(ArrayList<Jugador> jugadores) {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores disponibles");
            return;
        }

        System.out.println("|---------------------------|");
        System.out.println("| ID \t|\t NOMBRE");
        System.out.println("|---------------------------|");
        for (Jugador it : jugadores) {
            System.out.println("| " + it.getId_j() + " \t|\t " + it.getNombre());
            System.out.println("|---------------------------|");
        }
    }

    /**
     * Imprime los detalles de un jugador específico.
     *
     * @param jugador el jugador cuyo detalle se imprime.
     */
    public static void imprimirJugador(Jugador jugador) {
        System.out.println("+---------------------------------------+");
        System.out.println("|         Detalles del Jugador          |");
        System.out.println("+---------------------------------------+");
        System.out.println("| ID del Jugador    : " + jugador.getId_j() + "\t \t \t|");
        System.out.println("| Nombre del Jugador: " + jugador.getNombre() + "\t \t|");
        System.out.println("| PJ               : " + jugador.getPartidasJugadas() + "\t \t|");
        System.out.println("| PG               : " + jugador.getPartidasGanadas() + "\t \t|");
        System.out.println("+---------------------------------------+");

    }

    /**
     * Imprime en consola la lista de torneos con su ID y nombre. Si la lista de
     * torneos está vacía, se muestra un mensaje indicando que no hay torneos
     * disponibles. Si la lista contiene torneos, se imprimen sus IDs y nombres
     * en formato tabular.
     *
     * @param torneos La lista de torneos a imprimir.
     */
    public static void imprimirTorneos(ArrayList<Torneo> torneos) {
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos disponibles");
            return;
        }

        System.out.println("|---------------------------|");
        System.out.println("| ID \t|\t NOMBRE");
        System.out.println("|---------------------------|");
        for (Torneo it : torneos) {
            System.out.println("| " + it.getId_t() + " \t|\t " + it.getNombre());
            System.out.println("|---------------------------|");
        }
    }

    /**
     * Imprime los detalles de un torneo específico.
     *
     * @param torneo El torneo cuyo detalle se va a imprimir.
     */
    public static void imprimirTorneo(Torneo torneo) {
        System.out.println("+---------------------------------------+");
        System.out.println("|           Detalles del Torneo         |");
        System.out.println("+---------------------------------------+");
        System.out.println("| ID del Torneo    : " + torneo.getId_t() + "\t \t \t|");
        System.out.println("| Nombre del Torneo: " + torneo.getNombre() + "\t \t|");
        System.out.println("| Fecha            : " + torneo.getFecha() + "\t \t|");
        System.out.println("+---------------------------------------+");

    }

    /**
     * Devuelve una lista de torneos que aún no han sido jugados. Un torneo se
     * considera "no jugado" si las inscripciones están abiertas, lo que indica
     * que aún no ha tenido lugar.
     *
     * @return Una lista de objetos {@link Torneo} que aún no han sido jugados.
     */
    public static ArrayList<Torneo> noJugados() {
        // Se crea una nueva lista para almacenar los torneos no jugados
        ArrayList<Torneo> salida = new ArrayList<>();

        // Se recorre la lista de torneos para verificar si cada torneo tiene inscripciones abiertas
        for (Torneo torneo : torneos) {
            if (torneo.isInscripciones_abiertas()) { // Si las inscripciones están abiertas, se agrega el torneo a la lista 'salida'
                salida.add(torneo);
            }
        }
        return salida;
    }

    /**
     * Devuelve una lista de torneos que ya han sido jugados. Un torneo se
     * considera "jugado" si las inscripciones ya no están abiertas, lo que
     * indica que el torneo ya se ha llevado a cabo.
     *
     * @return Una lista de objetos {@link Torneo} que ya han sido jugados.
     */
    public static ArrayList<Torneo> jugados() {
        // Se crea una nueva lista para almacenar los torneos que ya han sido jugados
        ArrayList<Torneo> salida = new ArrayList<>();

        // Se recorre la lista de torneos para verificar si cada torneo ha sido jugado
        for (Torneo torneo : torneos) {
            if (!torneo.isInscripciones_abiertas()) { // Si las inscripciones ya no están abiertas, se considera que el torneo ya fue jugado
                salida.add(torneo);
            }
        }
        return salida;
    }

    /**
     * Devuelve una lista de jugadores que no están inscritos en un torneo
     * específico. Esta función crea una nueva lista que contiene todos los
     * jugadores registrados en el sistema, pero excluye aquellos que ya están
     * inscritos en el torneo dado.
     *
     * @param t El torneo para el que se desea obtener la lista de jugadores no
     * inscritos.
     * @return Una lista de objetos {@link Jugador} que no están inscritos en el
     * torneo.
     */
    public static ArrayList<Jugador> noInscritos(Torneo t) { //cambiar y no crear AL
        //ArrayList<Jugador> salida = jugadores; //asi solo le doy otra referencia no?
        //ArrayList<Jugador> salida = (ArrayList<Jugador>) jugadores.clone();

        // Se crea una nueva lista para no modificar la lista original de jugadores
        ArrayList<Jugador> salida = new ArrayList<>(jugadores);
        ArrayList<Jugador> jugadores_inscritos = new ArrayList<>();
        for (TorneoXJugador txj : t.getInscritos()) {
            jugadores_inscritos.add(txj.getJugador());
        }

        // Se eliminan de la nueva lista 'salida' aquellos jugadores que ya están inscritos en el torneo
        salida.removeAll(jugadores_inscritos);

        // Se retorna la lista con los jugadores no inscritos
        return salida;
    }

    /**
     * Método que permite crear un nuevo torneo en el sistema. Asigna un ID
     * único, solicita al usuario el nombre, fecha y número máximo de
     * participantes, y luego agrega el torneo a la lista de torneos si es
     * creado correctamente.
     */
    private static void crearTorneo() {
        // Solicitar al usuario el nombre del torneo.
        System.out.println("Introduce un nombre para el torneo: ");
        String nombre = sc.nextLine().toUpperCase();

        // Llamar a la función asignarFecha() para obtener una fecha válida.
        //Date fecha = asignarFecha();
        Date fecha = Validaciones.validarFechaFutura();

        // Solicitar al usuario el número máximo de participantes.
        System.out.println("Introduce el numero maximo de participantes: ");
        int num_max = Validaciones.asignarEntero();

        // Crear un nuevo objeto Torneo con los datos proporcionados.
        torneos.add(new Torneo(nombre, fecha, num_max));

        control.crearTorneo(torneos.getLast());

        outerLoop:// Etiqueta para el bucle exterior
        do {
            //torneos.getLast().getArbitros().add(contratarArbitro());
            contratarArbitro(torneos.getLast());

            // Pregunta al usuario si desea contratar más arbitros
            String respuesta;
            while (true) {
                System.out.println("¿Desea contratar más arbitros? (s/n)");
                respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    break; // Continuar con el siguiente arbitro
                } else if (respuesta.equalsIgnoreCase("n")) {
                    break outerLoop; // Rompe el bucle 'outerLoop' (el bucle principal)
                } else {
                    System.out.println("Indique s/n"); // Mensaje solo si es inválido
                }
            }
        } while (true);
    }

    /**
     * Muestra el menú de gestión de árbitros y permite al usuario realizar
     * distintas acciones.
     *
     * Este método proporciona opciones para crear, editar, eliminar y listar
     * árbitros, interactuando con la base de datos según la selección del
     * usuario.
     */
    private static void menuArbitro() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Crear Arbitro");
            System.out.println("2.- Eliminar Arbitro");
            System.out.println("3.- Modificar Arbitro");
            System.out.println("4.- Listar Arbitros");
            System.out.println("5.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    crearArbitro();
                    break;
                case "2":
                    eliminarArbitro();
                    break;
                case "3":
                    modificarArbitro();
                    break;
                case "4":
                    imprimirArbitros(control.leerTodosArbitros());
                    break;
                case "5":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }
        } while (!opcion.equals("5")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /**
     * Crea un nuevo árbitro solicitando su nombre y número de licencia al
     * usuario.
     *
     * Los datos ingresados se validan y se almacenan en la base de datos.
     */
    private static void crearArbitro() {

        System.out.println("Introduce el nombre del arbitro: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce el numero de licencia del arbitro: ");
        int numeroLicencia = Validaciones.asignarEntero();

        Arbitro nuevo = new Arbitro(nombre, numeroLicencia);

        control.crearArbitro(nuevo);
    }

    /**
     * Elimina un árbitro de la base de datos.
     *
     * Se muestra la lista de árbitros disponibles y se solicita al usuario que
     * seleccione uno para eliminar. Si no hay árbitros registrados, se muestra
     * un mensaje de error.
     */
    private static void eliminarArbitro() {
        ArrayList<Arbitro> arbitros = control.leerTodosArbitros();
        if (arbitros.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirArbitros(arbitros);
        System.out.println("Para el arbitro a eliminar...");
        Arbitro eliminar;
        do {
            eliminar = control.leerArbitro(Validaciones.asignarEntero());

        } while (eliminar == null);
        //arbitros.remove(eliminar);
        control.eliminarArbitro(eliminar.getId());

    }

    /**
     * Permite al usuario seleccionar un árbitro de la base de datos ingresando
     * su ID.
     *
     * Se solicita un ID válido hasta que se encuentre un árbitro registrado con
     * dicho ID.
     *
     * @return El objeto `Arbitro` seleccionado por el usuario.
     */
    private static Arbitro seleccionarArbitro() {
        int id_aux;
        // Bucle para solicitar un ID de jugador válido
        do {
            // Solicitar al usuario que ingrese un ID de jugador
            System.out.println("Selecciona el id de un arbitro: ");
            id_aux = Validaciones.asignarEntero();// Obtener el ID ingresado por el usuario

            // Buscar el jugador en la lista de jugadores
            for (Arbitro it : control.leerTodosArbitros()) {
                // Si el ID coincide con algún jugador en la lista, devolver ese jugador
                if (it.getId() == id_aux) {
                    return it;// Arbitro encontrado, se devuelve el objeto
                }
            }
            // Si no se encuentra el jugador con el ID proporcionado, se continuará el bucle
            System.out.println("Id no valido...");
        } while (true);  // El bucle continúa hasta que se ingresa un ID válido

    }

    /**
     * Permite modificar los datos de un árbitro registrado en la base de datos.
     *
     * Se muestra una lista de árbitros disponibles y el usuario puede
     * seleccionar uno para modificar. Se le permite cambiar el nombre y el
     * número de licencia del árbitro. Al finalizar, los cambios se guardan en
     * la base de datos.
     */
    private static void modificarArbitro() {
        ArrayList<Arbitro> arbitros = control.leerTodosArbitros();
        if (arbitros.isEmpty()) {
            System.out.println("No existen arbitros registrados");
            return; // Si no hay arbitros disponibles, salimos del método
        }

        // Imprimimos los torneos que no han sido jugados
        imprimirArbitros(arbitros);

        // Pedimos al usuario que seleccione el torneo que desea modificar
        System.out.println("Para el arbitro a modificar: ");
        Arbitro modif = seleccionarArbitro();// Seleccionamos el torneo a modificar

        String opcion = "";
        do {
            // Mostramos el menú de opciones para modificar el torneo
            System.out.println("Elija una opcion");
            System.out.println("1.- Modificar nombre");
            System.out.println("2.- Modificar numero de licencia");
            System.out.println("3.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("Introduce el nuevo nombre del arbitro: ");
                    modif.setNombre(sc.nextLine());
                    break;
                case "2":
                    System.out.println("Introduce el nuevo numero de licencia del arbitro: ");
                    modif.setNumeroLicencia(Validaciones.asignarEntero());
                    break;
                case "3": // Salimos y guardamos los cambios en el arbitro                    
                    control.editarArbitro(modif);
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /**
     * Imprime en la consola la lista de árbitros disponibles.
     *
     * Muestra los ID, nombres y números de licencia de todos los árbitros
     * registrados. Si no hay árbitros disponibles, se muestra un mensaje
     * informativo.
     *
     * @param arbitros Lista de árbitros a imprimir.
     */
    private static void imprimirArbitros(ArrayList<Arbitro> arbitros) {
        if (arbitros.isEmpty()) {
            System.out.println("No hay arbitros disponibles");
            return;
        }

        System.out.println("|---------------------------|");
        System.out.println("| ID \t|\t NOMBRE \t|\t LICENCIA");
        System.out.println("|---------------------------|");
        for (Arbitro it : arbitros) {
            System.out.println("| " + it.getId() + " \t|\t " + it.getNombre() + " \t|\t " + it.getNumeroLicencia());
            System.out.println("|---------------------------|");
        }
    }

    /**
     * Permite la contratación de un árbitro para un torneo.
     *
     * El usuario puede seleccionar un árbitro ya registrado o agregar uno nuevo
     * al torneo. Si el árbitro ya está contratado para el evento, se muestra un
     * mensaje de advertencia. Los cambios en la lista de árbitros del torneo se
     * guardan en la base de datos.
     *
     * @param torneo El torneo en el cual se va a contratar un árbitro.
     */
    private static void contratarArbitro(Torneo torneo) {
        String opcion = "";
        do {
            // Mostramos el menú de opciones para modificar el torneo
            System.out.println("Elija una opcion");
            System.out.println("1.- Seleccionar arbitro registrado");
            System.out.println("2.- Añadir nuevo arbitro");
            System.out.println("3.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    Arbitro seleccionado = seleccionarArbitro();
                    Boolean encontrado = false;
                    for (Arbitro contratado : torneo.getArbitros()) {
                        if (seleccionado.getId() == contratado.getId()) {
                            System.out.println("El arbitro seleccionado ya está contratado para este evento");
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        torneo.getArbitros().add(seleccionado);
                        control.editarTorneo(torneo);
                    }
                    break;
                case "2":

                    System.out.println("Introduce el nombre del arbitro: ");
                    String nombre = sc.nextLine();
                    System.out.println("Introduce el numero de licencia del arbitro: ");
                    int numeroLicencia = Validaciones.asignarEntero();
                    torneo.getArbitros().add(new Arbitro(nombre, numeroLicencia));
                    control.editarTorneo(torneo);
                    break;
                case "3": // Salimos y guardamos los cambios en el arbitro     
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle

    }

    /**
     * Elimina un torneo del sistema. El método verifica si existen torneos
     * registrados, luego solicita al usuario seleccionar un torneo para
     * eliminar. Una vez seleccionado, el torneo es removido de la lista de
     * torneos y se realiza la eliminación en la base de datos.
     */
    private static void eliminarTorneo() {
        // Verificar si la lista de torneos está vacía
        if (torneos.isEmpty()) {
            // Si no hay torneos registrados, mostrar mensaje y salir del método.
            System.out.println("No existen torneos registrados");
            return;// Termina el método si no hay torneos para eliminar.
        }

        // Mostrar todos los torneos disponibles para que el usuario seleccione uno.
        imprimirTorneos(torneos);

        // Solicitar al usuario el torneo que desea eliminar.
        System.out.println("Para el torneo a eliminar: ");
        Torneo eliminar = seleccionarTorneo();// Seleccionar el torneo por ID.

        // Eliminar el torneo seleccionado de la lista de torneos en memoria.
        torneos.remove(eliminar);
        control.eliminarTorneo(eliminar.getId_t());

        // Eliminar el torneo de la base de datos (o almacenamiento persistente).
        /*if (t.eliminarTorneo(eliminar)) {
            // Confirmar que el torneo fue eliminado (opcional, agregar un mensaje si es necesario).
            System.out.println("Torneo eliminado exitosamente.");
        }*/
    }

    /**
     * Permite modificar un torneo previamente registrado. El usuario puede
     * cambiar el nombre, la fecha o el número máximo de jugadores de un torneo.
     * El proceso continúa hasta que el usuario decide salir seleccionando la
     * opción correspondiente.
     */
    private static void modificarTorneo() {
        // Verificamos si existen torneos que no hayan sido jugados
        if (noJugados().isEmpty()) {
            System.out.println("No existen torneos registrados");
            return; // Si no hay torneos disponibles, salimos del método
        }

        // Imprimimos los torneos que no han sido jugados
        imprimirTorneos(noJugados());

        // Pedimos al usuario que seleccione el torneo que desea modificar
        System.out.println("Para el torneo a modificar: ");
        Torneo modif = seleccionarTorneo(noJugados());// Seleccionamos el torneo a modificar

        String opcion = "";
        do {
            // Mostramos el menú de opciones para modificar el torneo
            System.out.println("Elija una opcion");
            System.out.println("0.- Modificar nombre");
            System.out.println("1.- Modificar fecha");
            System.out.println("2.- Modificar numero maximo de jugadores");
            System.out.println("3.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    System.out.println("Introduce el nuevo nombre del torneo: ");
                    modif.setNombre(sc.nextLine());
                    break;
                case "1":
                    System.out.println("Introduce la nueva fecha del torneo: ");
                    //modif.setFecha(asignarFecha());
                    modif.setFecha(new Date());
                    break;
                case "2":
                    System.out.println("Introduce el nuevo numero maximo de jugadores del torneo: ");
                    modif.setNum_max_jugadores(Validaciones.asignarEntero());
                    break;
                case "3": // Salimos y guardamos los cambios en el torneo
                    //t.modificarTorneo(modif);
                    control.editarTorneo(modif);
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /**
     * Busca un torneo registrado en el sistema utilizando su ID. El método
     * verifica si existen torneos registrados y solicita al usuario el ID de un
     * torneo para buscarlo en la base de datos. Si el torneo se encuentra, se
     * muestran sus detalles.
     */
    private static void buscarTorneo() {
        // Verificar si la lista de torneos está vacía
        if (torneos.isEmpty()) {
            // Si no hay torneos registrados, mostrar mensaje y salir del método.
            System.out.println("No existen torneos registrados");
            return;
        }

        // Solicitar al usuario el ID del torneo a buscar
        System.out.println("Indica el id del torneo a buscar:");

        // Obtener el ID del torneo a buscar y Buscar el torneo en la base de datos 
        //Torneo encontrado = t.buscarTorneo(asignarEntero());
        Torneo encontrado = control.leerTorneo(Validaciones.asignarEntero());

        // Verificar si el torneo fue encontrado
        if (encontrado != null) {
            // Si el torneo es encontrado, imprimir sus detalles
            imprimirTorneo(encontrado);
        } else {
            // Si no se encuentra el torneo, mostrar mensaje de error
            System.out.println("Torneo no encontrado con el ID indicado ");
        }
    }

    /**
     * Permite inscribir jugadores a un torneo que aún no ha comenzado. El
     * método verifica si existen torneos disponibles y jugadores registrados
     * antes de continuar. Después, el usuario puede seleccionar un torneo y
     * proceder a inscribir jugadores en él hasta que se hayan agotado las
     * plazas o no queden más jugadores por inscribir.
     */
    private static void inscribir() {
        // Verifica si existen torneos registrados y si hay torneos no jugados disponibles
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No existen torneos registrados para inscribir");
            return; // Sale si no hay torneos disponibles
        }

        // Verifica si existen jugadores registrados
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados para inscribir");
            return; // Sale si no hay jugadores disponibles
        }

        // Muestra los torneos que no han comenzado (no jugados)
        imprimirTorneos(noJugados());
        Torneo seleccion_torneo = seleccionarTorneo(noJugados());// Permite al usuario seleccionar un torneo

        // Calcula las plazas disponibles en el torneo seleccionado
        int plazas_disponibles = seleccion_torneo.getNum_max_jugadores() - seleccion_torneo.getInscritos().size();

        outerLoop:// Etiqueta para el bucle exterior
        do {
            // Muestra los jugadores que aún no están inscritos en el torneo
            imprimirJugadores(noInscritos(seleccion_torneo));
            if (noInscritos(seleccion_torneo).isEmpty()) {
                break;
            }
            System.out.println("Plazas disponibles: " + plazas_disponibles);

            // Permite al usuario seleccionar un jugador para inscribir
            Jugador seleccion_jugador = seleccionarJugador(noInscritos(seleccion_torneo));

            // Inscribe al jugador seleccionado en el torneo
            seleccion_torneo.inscribir(seleccion_jugador);

            TorneoXJugador inscripcion_asociada = new TorneoXJugador(seleccion_torneo, seleccion_jugador);

            seleccion_torneo.getInscritos().add(inscripcion_asociada);

            control.crearTorneoXJugador(inscripcion_asociada);
            System.out.println("Inscrito jugador " + seleccion_jugador.getNombre());

            // Actualiza el número de plazas disponible
            plazas_disponibles = seleccion_torneo.getNum_max_jugadores() - seleccion_torneo.getInscritos().size();

            // Si ya no hay más plazas disponibles, se sale del bucle
            if (plazas_disponibles <= 0) {
                System.out.println("No quedan plazas disponibles");
                break;
            }

            // Si no quedan más jugadores por inscribir, se termina el proceso
            if (noInscritos(seleccion_torneo).size() <= 0) {
                System.out.println("No hay más jugadores disponibles para inscribir...");
                break;
            }

            // Pregunta al usuario si desea inscribir más jugadores
            String respuesta;
            while (true) {
                System.out.println("¿Desea inscribir más jugadores? (s/n)");
                respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    break; // Continuar con el siguiente jugador
                } else if (respuesta.equalsIgnoreCase("n")) {
                    break outerLoop; // Rompe el bucle 'outerLoop' (el bucle principal)
                } else {
                    System.out.println("Indique s/n"); // Mensaje solo si es inválido
                }
            }
        } while (true);

    }

    /**
     * Permite jugar un torneo de cara o cruz que no haya sido jugado aún.
     * Actualiza los datos de los jugadores inscritos y marca el torneo como
     * jugado.
     */
    private static void jugarTorneo() {
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return; // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo seleccionado = seleccionarTorneo(noJugados());

        if (!seleccionado.isInscripciones_abiertas()) {  // Si las inscripciones están cerradas, el torneo ya fue jugado
            System.out.println("El torneo ya fue jugado");
        } else if (seleccionado.getInscritos().size() <= 1) { // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo
            seleccionado.jugar();
            // Guarda el torneo como jugado en la base de datos
            control.editarTorneo(seleccionado);
            for (TorneoXJugador inscrito : seleccionado.getInscritos()) {
                //control.editarTorneoXJugador(inscrito);//esto no hace falta por el orphan y el cascade de torneo
                control.editarJugador(inscrito.getJugador());
            }

            //t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación local del torneo esspecífico de los jugadores inscritos
            for (Jugador j : jugadores_inscritos(seleccionado)) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    /**
     * Permite jugar un torneo de dados que no haya sido jugado aún, utilizando
     * una simulación con dados. Actualiza los datos de los jugadores inscritos
     * y marca el torneo como jugado.
     */
    private static void jugarTorneoDado() {
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return;  // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo seleccionado = seleccionarTorneo(noJugados());

        if (!seleccionado.isInscripciones_abiertas()) { // Verifica si el torneo ya fue jugado o si las inscripciones están cerradas
            System.out.println("El torneo ya fue jugado");
        } else if (seleccionado.getInscritos().size() <= 1) {  // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo usando dados
            seleccionado.jugarDado();
            // Guarda el torneo como jugado en la base de datos
            control.editarTorneo(seleccionado);
            for (TorneoXJugador inscrito : seleccionado.getInscritos()) {
                //control.editarTorneoXJugador(inscrito);//esto no hace falta por el orphan y el cascade de torneo
                control.editarJugador(inscrito.getJugador());
            }

            //t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación local del torneo esspecífico de los jugadores inscritos
            for (Jugador j : jugadores_inscritos(seleccionado)) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    /**
     * Permite jugar un torneo de piedra papel o tijera que no haya sido jugado
     * aún, utilizando una simulación con dados. Actualiza los datos de los
     * jugadores inscritos y marca el torneo como jugado.
     */
    private static void jugarTorneoPiedraPapelTijera() {
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return;  // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo seleccionado = seleccionarTorneo(noJugados());

        if (!seleccionado.isInscripciones_abiertas()) { // Verifica si el torneo ya fue jugado o si las inscripciones están cerradas
            System.out.println("El torneo ya fue jugado");
        } else if (seleccionado.getInscritos().size() <= 1) {  // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo usando piedrapapeltijera
            //VistaControlador.IniciarTorneoPPT ventana = new IniciarTorneoPPT(seleccionado);
            //ventana.setVisible(true);
            seleccionado.jugarPiedraPapelTijera();
            // Guarda el torneo como jugado en la base de datos
            control.editarTorneo(seleccionado);
            for (TorneoXJugador inscrito : seleccionado.getInscritos()) {
                //control.editarTorneoXJugador(inscrito);//esto no hace falta por el orphan y el cascade de torneo
                control.editarJugador(inscrito.getJugador());
            }

            //t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación local del torneo esspecífico de los jugadores inscritos
            for (Jugador j : jugadores_inscritos(seleccionado)) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    /**
     * Permite jugar un torneo de BlackJack que no haya sido jugado aún,
     * utilizando una simulación con dados. Actualiza los datos de los jugadores
     * inscritos y marca el torneo como jugado.
     */
    private static void jugarTorneoBlackJack() {
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return;  // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo seleccionado = seleccionarTorneo(noJugados());

        if (!seleccionado.isInscripciones_abiertas()) { // Verifica si el torneo ya fue jugado o si las inscripciones están cerradas
            System.out.println("El torneo ya fue jugado");
        } else if (seleccionado.getInscritos().size() <= 1) {  // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo usando dados
            //VistaControlador.IniciarTorneoPPT ventana = new IniciarTorneoPPT(seleccionado);
            //ventana.setVisible(true);
            seleccionado.jugarBlackJack();
            // Guarda el torneo como jugado en la base de datos
            control.editarTorneo(seleccionado);
            for (TorneoXJugador inscrito : seleccionado.getInscritos()) {
                //control.editarTorneoXJugador(inscrito);//esto no hace falta por el orphan y el cascade de torneo
                control.editarJugador(inscrito.getJugador());
            }

            //t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación local del torneo esspecífico de los jugadores inscritos
            for (Jugador j : jugadores_inscritos(seleccionado)) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    /**
     * Obtiene la lista de jugadores inscritos en un torneo.
     *
     * Este método recorre la lista de inscripciones de un torneo y extrae los
     * jugadores que están inscritos en él.
     *
     * @param torneo El torneo del cual se quieren obtener los jugadores
     * inscritos.
     * @return Una lista de jugadores inscritos en el torneo.
     */
    private static ArrayList<Jugador> jugadores_inscritos(Torneo torneo) {
        ArrayList<Jugador> salida = new ArrayList<>();
        for (TorneoXJugador inscrito : torneo.getInscritos()) {
            salida.add(inscrito.getJugador());
        }
        return salida;
    }

    /**
     * Inserta torneos de prueba en la base de datos.
     */
    public static void entradasPrueba() {
        // Crear un EntityManagerFactory y un EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_proyecto");

        EntityManager em = emf.createEntityManager();

        // Iniciar una transacción
        EntityTransaction transaction = em.getTransaction();

        try {
            // Iniciar la transacción
            transaction.begin();

            // Operaciones de base de datos
            Torneo torneo1 = new Torneo("Campeonato Nacional", new Date(), 20);
            Torneo torneo2 = new Torneo("Campeonato Nacional II", new Date(), 20);
            //Torneo torneo3 = new Torneo("Campeonato Nacional II", (new SimpleDateFormat("dd/MM/yyyy")).parse("22/2"), 9);
            //int error=10/0;

            // Guardar el torneo en la base de datos
            em.persist(torneo1);
            em.persist(torneo2);
            //em.persist(torneo3);

            // Si todo va bien, hacer commit
            transaction.commit();
            System.out.println("Transacción exitosa!");

        } catch (Exception e) {
            // En caso de error, hacer rollback
            if (transaction.isActive()) {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println("Error en la transacción. Se ha hecho rollback.");
        } finally {
            // Cerrar el EntityManager
            em.close();
            emf.close();
        }
    }

}
