package Operativa;

import DAO_Implementaciones.JugadorDAOImplements;
import DAO_Implementaciones.TorneoDAOImplements;
import IO.ConexionBBDD;
import Modelo.Jugador;
import Modelo.Torneo;
import VistaControlador.Emparejados;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase que gestiona los menús interactivos para la creación, modificación,
 * eliminación y consulta de jugadores, torneos y partidas. Los menús permiten
 * al usuario seleccionar las opciones para realizar operaciones sobre los
 * datos.
 *
 * Incluye menús para gestionar jugadores, torneos y partidas de manera
 * independiente, así como la navegación entre estos menús. También se gestionan
 * las conexiones a la base de datos y el cierre de la aplicación.
 *
 * @author adria
 */
public class Operativa {

    public static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<Torneo> torneos = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    private static JugadorDAOImplements j = new JugadorDAOImplements();
    private static TorneoDAOImplements t = new TorneoDAOImplements();

    /**
     * Muestra el menú de opciones para gestionar los jugadores. Permite crear,
     * eliminar, modificar, buscar y listar jugadores.
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
            System.out.println("6.- Salir");

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
                    imprimirJugadores(j.listarJugadores());
                    break;
                case "6":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("6")); //Mientras seleccione un numero distinto de 6 seguir el bucle
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
            System.out.println("8.- Deserializar Torneo");
            System.out.println("9.- Salir");

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
                    imprimirTorneos(t.listarTorneos()); //sin jugadores inscritos
                    break;
                case "6":
                    imprimirTorneos(jugados());
                    if (!jugados().isEmpty()) {
                        t.top3(seleccionarTorneo(jugados()));
                    }
                    break;
                case "7":
                    imprimirTorneos(jugados());
                    if (!jugados().isEmpty()) {
                        t.mostrarStats(seleccionarTorneo(jugados()));
                    }
                    break;
                case "8":
                    deserializar();
                    break;
                case "9":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("9")); //Mientras seleccione un numero distinto de 8 seguir el bucle
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
        } while (!opcion.equals("5")); //Mientras seleccione un numero distinto de 3 seguir el bucle
    }

    /**
     * Muestra el menú principal que permite navegar entre los menús de
     * Jugadores, Torneos y Partidas. Establece la conexión con la base de datos
     * y carga la lista de jugadores y torneos.
     */
    public static void menu() {
        ConexionBBDD.getConnection();
        jugadores = j.listarJugadores();
        torneos = t.leerBBDDTorneos(jugadores);
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Menu Jugador");
            System.out.println("2.- Menu Torneo");
            System.out.println("3.- Menu Partida");
            System.out.println("4.- Salir");

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
                    JOptionPane.showMessageDialog(null, "¡Gracias por utilizar nuestra aplicación!");
                    ConexionBBDD.desconectarBBDD();
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /**
     * Crea un nuevo jugador con un nombre asignado por el usuario y un ID
     * generado.
     */
    public static void crearJugador() {
        System.out.println("Introduce el nombre del jugador: ");
        String nombre = sc.nextLine();
        int id = asignarId_j();
        jugadores.add(new Jugador(id, nombre));
        j.crearJugador(jugadores.getLast());
    }

    /**
     * Asigna un ID único a un nuevo jugador.
     *
     * @return el ID único.
     */
    public static int asignarId_j() {
        boolean encontrado;
        for (int i = 0; i < jugadores.size(); i++) {
            encontrado = false;
            for (Jugador jugador : jugadores) {
                //System.out.println(jugador.getId_j());
                if (i == jugador.getId_j()) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                return i;
            }
        }
        return jugadores.size();
    }

    /**
     * Asigna un ID único a un nuevo torneo.
     *
     * @return el ID único.
     */
    public static int asignarId_t() {
        boolean encontrado;
        for (int i = 0; i < torneos.size(); i++) {
            encontrado = false;
            for (Torneo torneo : torneos) {
                if (i == torneo.getId_t()) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                return i;
            }
        }
        return torneos.size();
    }

    /**
     * Elimina un jugador de la lista, si existe.
     */
    public static void eliminarJugador() {
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(jugadores);
        System.out.println("Para el jugador a eliminar...");
        Jugador eliminar = seleccionarJugador();
        jugadores.remove(eliminar);
        j.eliminarJugador(eliminar);
    }

    /**
     * Modifica los datos de un jugador (nombre).
     */
    public static void modificarJugador() {
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(jugadores);
        System.out.println("Para el jugador a modificar: ");
        Jugador modif = seleccionarJugador();
        System.out.println("Introduce el nuevo nombre del jugador: ");
        modif.setNombre(sc.nextLine());
        j.modificarJugador(modif);
    }

    /**
     * Busca un jugador en la base de datos por su ID y muestra sus detalles.
     */
    public static void buscarJugador() { // para que querria este metodo //busca en la bbdd
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        System.out.println("Introduzca el id del jugador a buscar:");
        Jugador encontrado = j.buscarJugador(asignarEntero());
        if (encontrado == null) {
            System.out.println("No hay jugadores con el id indicado");
            return;
        }
        imprimirJugador(encontrado);
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
        System.out.println("| ID del Torneo    : " + jugador.getId_j() + "\t \t \t|");
        System.out.println("| Nombre del Torneo: " + jugador.getNombre() + "\t \t|");
        System.out.println("| PJ               : " + jugador.getPartidasJugadas() + "\t \t|");
        System.out.println("| PG               : " + jugador.getPartidasGanadas() + "\t \t|");
        System.out.println("+---------------------------------------+");

    }

    /**
     * Método para solicitar y validar una fecha en el formato "dd-mm-yyyy".
     * Utiliza una expresión regular para asegurarse de que la fecha ingresada
     * siga el formato correcto.
     *
     * @return La fecha introducida por el usuario, si es válida.
     */
    public static String asignarFecha() {
        String fecha;
        do {
            // Solicitar al usuario que ingrese la fecha en el formato adecuado.
            System.out.println("Introduce la fecha del torneo (dd-mm-yyyy):"); // Solicita la fecha al usuario.
            fecha = sc.nextLine(); // Lee la entrada del usuario.
            // Valida que la fecha siga el formato "dd-mm-yyyy".
        } while (!fecha.matches("\\d{2}-\\d{2}-\\d{4}")); //Al escribir \\d, Java sabe que se refiere a la expresión regular para "dígito".
        return fecha; // Retorna la fecha validada.
    }

    /**
     * Método que permite crear un nuevo torneo en el sistema. Asigna un ID
     * único, solicita al usuario el nombre, fecha y número máximo de
     * participantes, y luego agrega el torneo a la lista de torneos si es
     * creado correctamente.
     */
    public static void crearTorneo() {
        // Asignar un ID único para el nuevo torneo.
        int id = asignarId_t();

        // Solicitar al usuario el nombre del torneo.
        System.out.println("Introduce un nombre para el torneo: ");
        String nombre = sc.nextLine().toUpperCase();

        // Llamar a la función asignarFecha() para obtener una fecha válida.
        String fecha = asignarFecha();

        // Solicitar al usuario el número máximo de participantes.
        System.out.println("Introduce el numero maximo de participantes: ");
        int num_max = asignarEntero();

        // Crear un nuevo objeto Torneo con los datos proporcionados.
        Torneo nuevo = new Torneo(id, nombre, fecha, num_max);
        // Intentar crear el torneo y verificar si la creación fue exitosa.
        if (t.crearTorneo(nuevo)) {
            // Si se crea correctamente, agregarlo a la lista de torneos.
            torneos.add(nuevo);
            System.out.println("¡Torneo creado exitosamente!"); // Confirmar al usuario
        } else {
            // Si hay un error al crear el torneo, mostrar un mensaje de error.
            System.out.println("Error al crear el torneo.");
        }
    }

    /**
     * Elimina un torneo del sistema. El método verifica si existen torneos
     * registrados, luego solicita al usuario seleccionar un torneo para
     * eliminar. Una vez seleccionado, el torneo es removido de la lista de
     * torneos y se realiza la eliminación en la base de datos.
     */
    public static void eliminarTorneo() {
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

        // Eliminar el torneo de la base de datos (o almacenamiento persistente).
        if (t.eliminarTorneo(eliminar)) {
            // Confirmar que el torneo fue eliminado (opcional, agregar un mensaje si es necesario).
            System.out.println("Torneo eliminado exitosamente.");
        }

    }

    /**
     * Busca un torneo registrado en el sistema utilizando su ID. El método
     * verifica si existen torneos registrados y solicita al usuario el ID de un
     * torneo para buscarlo en la base de datos. Si el torneo se encuentra, se
     * muestran sus detalles.
     */
    public static void buscarTorneo() { //busca en la bbdd   
        // Verificar si la lista de torneos está vacía
        if (torneos.isEmpty()) {
            // Si no hay torneos registrados, mostrar mensaje y salir del método.
            System.out.println("No existen torneos registrados");
            return;
        }

        // Solicitar al usuario el ID del torneo a buscar
        System.out.println("Indica el id del torneo a buscar:");

        // Obtener el ID del torneo a buscar y Buscar el torneo en la base de datos 
        Torneo encontrado = t.buscarTorneo(asignarEntero());

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
            id_j_aux = asignarEntero(); // Obtener el ID ingresado por el usuario

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
            int id_t_aux = asignarEntero(); // Obtener el ID ingresado por el usuario

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
            id_j_aux = asignarEntero();// Obtener el ID ingresado por el usuario

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
            int id_t_aux = asignarEntero();// Obtener el ID ingresado por el usuario

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
     * Solicita al usuario que ingrese un número entero y lo valida. El método
     * sigue pidiendo una entrada hasta que el usuario ingrese un valor válido
     * (un número entero). Si el usuario ingresa un valor no numérico, se
     * muestra un mensaje de error y se solicita nuevamente.
     *
     * @return El valor entero ingresado por el usuario.
     */
    public static int asignarEntero() {
        int salida; // Variable donde se almacenará el valor ingresado por el usuario
        do {
            try {
                // Solicitar al usuario que ingrese un número entero
                salida = Integer.valueOf(sc.nextLine()); // Intentamos convertir la entrada a un número entero
                return salida;// Si la conversión es exitosa, retornamos el número
            } catch (NumberFormatException e) {
                // Si la entrada no es un número válido (excepción), mostramos un mensaje de error
                System.out.println("Valor invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true); // Repetir el proceso hasta que se ingrese un número entero válido
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
     * Permite inscribir jugadores a un torneo que aún no ha comenzado. El
     * método verifica si existen torneos disponibles y jugadores registrados
     * antes de continuar. Después, el usuario puede seleccionar un torneo y
     * proceder a inscribir jugadores en él hasta que se hayan agotado las
     * plazas o no queden más jugadores por inscribir.
     */
    public static void inscribir() {
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
            System.out.println("Plazas disponibles: " + plazas_disponibles);

            // Permite al usuario seleccionar un jugador para inscribir
            Jugador seleccion_jugador = seleccionarJugador(noInscritos(seleccion_torneo));

            // Inscribe al jugador seleccionado en el torneo
            seleccion_torneo.inscribir(seleccion_jugador);
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
     * Permite jugar un torneo que no haya sido jugado aún. Actualiza los datos
     * de los jugadores inscritos y marca el torneo como jugado.
     */
    public static void jugarTorneo() { //actualizar los datos de los jugadores inscritos
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return; // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo selecionado = seleccionarTorneo(noJugados());

        if (!selecionado.isInscripciones_abiertas()) {  // Si las inscripciones están cerradas, el torneo ya fue jugado
            System.out.println("El torneo ya fue jugado");
        } else if (selecionado.getInscritos().size() <= 1) { // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo
            selecionado.jugar();
            // Guarda el torneo como jugado en la base de datos
            t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación local del torneo esspecífico de los jugadores inscritos
            for (Jugador j : selecionado.getInscritos()) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    /**
     * Permite jugar un torneo que no haya sido jugado aún, utilizando una
     * simulación con dados. Actualiza los datos de los jugadores inscritos y
     * marca el torneo como jugado.
     */
    public static void jugarTorneoDado() {
        // Muestra los torneos no jugados
        imprimirTorneos(noJugados());

        // Verifica si hay torneos disponibles para jugar (ni torneos registrados ni torneos no jugados)
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No hay torneos disponibles para jugar");
            return;  // Sale si no hay torneos para jugar
        }

        // Permite al usuario seleccionar un torneo que no haya sido jugado aún
        Torneo selecionado = seleccionarTorneo(noJugados());

        if (!selecionado.isInscripciones_abiertas()) { // Verifica si el torneo ya fue jugado o si las inscripciones están cerradas
            System.out.println("El torneo ya fue jugado");
        } else if (selecionado.getInscritos().size() <= 1) {  // Si hay menos de 2 jugadores inscritos, no se puede jugar el torneo
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            // Procede a jugar el torneo usando dados
            selecionado.jugarDado();
            // Guarda el torneo como jugado en la base de datos
            t.guardarTorneoJugado(selecionado);
            // Reinicia la puntuación de los jugadores inscritos
            for (Jugador j : selecionado.getInscritos()) {
                j.resetearPuntuacionTorneo();
            }
        }
    }
    
    public static void jugarTorneoPiedraPapelTijera() {
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
            VistaControlador.Emparejados ventana = new Emparejados(seleccionado);
            ventana.setVisible(true);
            seleccionado.jugarPiedraPapelTijera();
            // Guarda el torneo como jugado en la base de datos
            t.guardarTorneoJugado(seleccionado);
            // Reinicia la puntuación de los jugadores inscritos
            for (Jugador j : seleccionado.getInscritos()) {
                j.resetearPuntuacionTorneo();
            }
        }
    }
    
    public static void jugarTorneoBlackJack() {
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
            VistaControlador.Emparejados ventana = new Emparejados(seleccionado);
            ventana.setVisible(true);
            seleccionado.jugarBlackJack();
            // Guarda el torneo como jugado en la base de datos
            t.guardarTorneoJugado(seleccionado);
            // Reinicia la puntuación de los jugadores inscritos
            for (Jugador j : seleccionado.getInscritos()) {
                j.resetearPuntuacionTorneo();
            }
        }
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

        // Se eliminan de la nueva lista 'salida' aquellos jugadores que ya están inscritos en el torneo
        salida.removeAll(t.getInscritos());

        // Se retorna la lista con los jugadores no inscritos
        return salida;
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
     * Permite modificar un torneo previamente registrado. El usuario puede
     * cambiar el nombre, la fecha o el número máximo de jugadores de un torneo.
     * El proceso continúa hasta que el usuario decide salir seleccionando la
     * opción correspondiente.
     */
    public static void modificarTorneo() {
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
                    modif.setFecha(asignarFecha());
                    break;
                case "2":
                    System.out.println("Introduce el nuevo numero maximo de jugadores del torneo: ");
                    modif.setNum_max_jugadores(asignarEntero());
                    break;
                case "3":
                    t.modificarTorneo(modif); // Salimos y guardamos los cambios en el torneo
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /*public static Torneo deserializarTorneoJugado(int idTorneo) throws IOException {
        Torneo torneo = null;

        // Consulta SQL para recuperar el objeto serializado
        String sql = "SELECT torneo_data FROM torneoSerializado WHERE id_t = ?";

        try (PreparedStatement ps = ConexionBBDD.getConnection().prepareStatement(sql);) {
            ps.setInt(1, idTorneo); // Establece el parámetro

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] torneoData = rs.getBytes("torneo_data"); // Recupera el array de bytes

                    // Deserializar el objeto
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(torneoData); ObjectInputStream ois = new ObjectInputStream(bis)) {

                        torneo = (Torneo) ois.readObject(); // Convierte el array de bytes de nuevo a objeto
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error al deserializar el objeto: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No se encontró un torneo con el ID especificado: " + idTorneo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        return torneo; // Devuelve el objeto Torneo deserializado
    }*/
    private static void deserializar() {
        imprimirTorneos(torneos);
        System.out.println("Para el torneo a deserializar: ");
        int id = seleccionarTorneo().getId_t();
        Torneo torneo = t.deserializarTorneoJugado(id);
        System.out.println("Torneo deserializado: ");
        imprimirTorneoCompleto(torneo);
    }

    private static void imprimirTorneoCompleto(Torneo torneo) {
        System.out.println("+---------------------------------------+");
        System.out.println("|           Detalles del Torneo         |");
        System.out.println("+---------------------------------------+");
        System.out.println("| ID del Torneo    : " + torneo.getId_t() + "\t \t \t|");
        System.out.println("| Nombre del Torneo: " + torneo.getNombre() + "\t \t|");
        System.out.println("| Fecha            : " + torneo.getFecha() + "\t \t|");
        System.out.println("| Inscritos            : ");
        imprimirJugadores(torneo.getInscritos());
        System.out.println("+---------------------------------------+");

    }
}
