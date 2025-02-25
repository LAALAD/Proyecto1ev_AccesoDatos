/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectoac_2ev;

import VistaControlador.Principal;
import VistaControlador.MenuMinijuegos;
import VistaControlador.Ventana_Gracias;
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
 *
 * @author Usuario
 */
public class ProyectoAc_2ev {

    private static ControladorLogico control = new ControladorLogico();

    private static ArrayList<Jugador> arbitros = new ArrayList<>();
    private static ArrayList<Torneo> torneos = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Principal p1 = new Principal();
        p1.setVisible(true);

    }

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
        arbitros = control.leerTodosJugadores();
        torneos = control.leerTodosTorneos();
        System.out.println("¿Desea añadir jugadores básicos de prueba? (s/n)");
        String res = sc.nextLine();
        if (res.equalsIgnoreCase("s")) {
            ArrayList<Jugador> jugadores_aux = IO.LeerXML.leerJugadores();
            for (Jugador jugador : jugadores_aux) {
                control.crearJugador(jugador);
                arbitros.add(jugador);
            }
        }
        //ConexionBBDD.getConnection();
        //jugadores = j.listarJugadores();
        //torneos = t.leerBBDDTorneos(jugadores);

        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Menu Jugador");
            System.out.println("2.- Menu Torneo");
            System.out.println("3.- Menu Partida");
            System.out.println("4.- Guardar Jugadores Existentes");
            System.out.println("5.- Salir");

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
                    IO.EscribirXML.guardarJugadores(arbitros);
                    break;
                case "5":
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

        } while (!opcion.equals("5")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /*private static void jugarMiniJuego() {
        System.out.println("¿Desea ser BETA TESTER de nuestro proximo juego? (s/n)");
        String respuesta = sc.nextLine();
        if (respuesta.equals("s")) {
            Minijuego juego = new Minijuego();
            juego.setVisible(true);
        }
    }*/
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
                    imprimirJugadores(arbitros);
                    //imprimirJugadores(j.listarJugadores());
                    break;
                case "6":
                    imprimirJugadores(arbitros);
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
        arbitros.add(new Jugador(nombre));

        //rellena la ficha si quieres, de manera opcional
        System.out.println("Deseas rellenar la ficha del jugador s/n");
        String opcion = sc.nextLine();
        if (opcion.equalsIgnoreCase("s")) {
            //rellena los datos personales del ultimo jugadore creado devolviendo un tipo de dato Datos Personales
            arbitros.getLast().setDatosPersonales(rellenarDatosPersonales());
        }

        control.crearJugador(arbitros.getLast());
    }

    private static DatosPersonales rellenarDatosPersonales() {
        System.out.println("Introduce el apellido: ");
        String apellido = sc.nextLine();

        Date fecha = validarFechaPasada();

        System.out.println("Introduce un email: ");
        String email = sc.nextLine();

        System.out.println("Introduce el telefono: ");
        String telefono = sc.nextLine();
        DatosPersonales dp = new DatosPersonales(1, apellido, fecha, email, telefono);
        return dp;
    }

    //Metodo que guarda en la base de datos los datos personales del jugador
    private static void guardarDatosPersonales(Jugador jugador) {
        DatosPersonales datosP = rellenarDatosPersonales();
        jugador.setDatosPersonales(datosP);
        //control.editarDatosPersonales(datosP); //NO HACE FALTA GRACIAS AL CASCADE
        control.editarJugador(jugador);
    }

    // Método que valida el día y mes de la fecha
    private static boolean esFechaValida(String fechaStr) {
        // Separamos el día, mes y año
        String[] partesFecha = fechaStr.split("/");

        // Comprobamos que la fecha tenga exactamente 3 partes (dd, mm, yyyy)
        if (partesFecha.length != 3) {
            System.out.println("Formato incorrecto. Debe ser dd/MM/yyyy.");
            return false;  // Fecha inválida
        }

        // Intentamos convertir los valores de día, mes y año
        try {
            int dia = Integer.valueOf(partesFecha[0]);
            int mes = Integer.valueOf(partesFecha[1]);
            int ano = Integer.valueOf(partesFecha[2]);

            // Validar que el mes esté entre 1 y 12
            if (mes < 1 || mes > 12) {
                System.out.println("Mes inválido. El mes debe estar entre 01 y 12.");
                return false;  // Fecha inválida
            }

            // Validar que el día esté dentro de los rangos posibles para ese mes
            if (!esDiaValido(dia, mes, ano)) {
                return false;  // Fecha inválida
            }

        } catch (NumberFormatException e) {
            System.out.println("El día, mes o año no es un número válido. Intenta nuevamente.");
            return false;  // Fecha inválida
        }

        // Si pasa todas las validaciones
        return true;
    }

    // Método auxiliar para validar el día según el mes y el año
    private static boolean esDiaValido(int dia, int mes, int ano) {
        // Definir los días máximos por mes
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Si es un año bisiesto, febrero tiene 29 días
        if (esBisiesto(ano)) {
            diasPorMes[1] = 29;  // Febrero (índice 1) tiene 29 días en años bisiestos
        }

        // Comprobar si el día es válido para el mes
        //mes-1 porque el mes 1 enero -> es la posicion 0 del array
        if (dia < 1 || dia > diasPorMes[mes - 1]) { // si el dia es negativo o mas de los particulares de ese mes
            System.out.println("Día inválido. El mes " + mes + " tiene " + diasPorMes[mes - 1] + " días.");
            return false;  // Fecha inválida
        }

        // Si el día es válido para ese mes
        return true;
    }

    // Método para verificar si un año es bisiesto
    private static boolean esBisiesto(int ano) {
        if (ano % 4 == 0) {  // Verificamos si es divisible por 4
            if (ano % 100 == 0) {  // Verificamos si es divisible por 100
                if (ano % 400 == 0) {  // Verificamos si también es divisible por 400
                    return true;  // Si es divisible por 400, es bisiesto
                } else {
                    return false;  // Si no es divisible por 400, no es bisiesto
                }
            }
            return true;  // Si es divisible por 4 pero no por 100, es bisiesto
        }
        return false;  // Si no es divisible por 4, no es bisiesto
    }

    // Método estático que solicita y valida una fecha de nacimiento introducida por el usuario
    private static Date validarFechaPasada() {
        // Declaramos una variable de tipo Date para almacenar la fecha que será validada
        Date fecha = null;

        // Creamos un objeto SimpleDateFormat que se encargará de convertir la cadena de texto en un objeto Date
        // El formato de la fecha esperado es día/mes/año, por ejemplo: 23/02/2025
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Variable booleana que indica si la fecha ingresada es válida. Se inicializa como falsa.
        boolean fechaValida = false;

        // Iniciamos un bucle while que seguirá pidiendo la fecha hasta que el formato sea correcto
        while (!fechaValida) {
            try {
                // Solicitamos la fecha de nacimiento al usuario
                System.out.print("Introduce la fecha de nacimiento (dd/MM/yyyy): ");

                // Leemos la entrada del usuario como una cadena de texto (String)
                String fechaStr = sc.nextLine();

                // Llamamos al método que valida el día y mes
                if (!esFechaValida(fechaStr)) {
                    continue;  // Si la fecha no es válida, volvemos a pedirla
                }

                // Intentamos convertir la cadena leída en un objeto Date usando el formato esperado
                fecha = sdf.parse(fechaStr);

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Comprobar si la fecha ingresada es anterior a la fecha actual
                if (fecha.before(fechaActual)) {
                    // Si la fecha es válida (es anterior a la actual), se establece como válida
                    fechaValida = true;
                } else {
                    // Si la fecha no es anterior a la actual, se informa al usuario
                    System.out.println("La fecha ingresada no puede ser futura. Intenta nuevamente.");
                }
                //Si ocurre un error al intentar convertir la fecha (por ejemplo, si el formato es incorrecto),
                // entra en el bloque catch y muestra un mensaje de error
            } catch (ParseException e) {
                System.out.println("Formato de fecha incorrecto. Intenta nuevamente.");
            }
        }

        // Cuando la fecha es válida, la devolvemos
        return fecha;
    }

    // Método estático que solicita y valida una fecha de nacimiento introducida por el usuario
    private static Date validarFechaFutura() {
        // Declaramos una variable de tipo Date para almacenar la fecha que será validada
        Date fecha = null;

        // Creamos un objeto SimpleDateFormat que se encargará de convertir la cadena de texto en un objeto Date
        // El formato de la fecha esperado es día/mes/año, por ejemplo: 23/02/2025
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Variable booleana que indica si la fecha ingresada es válida. Se inicializa como falsa.
        boolean fechaValida = false;

        // Iniciamos un bucle while que seguirá pidiendo la fecha hasta que el formato sea correcto
        while (!fechaValida) {
            try {
                // Solicitamos la fecha de nacimiento al usuario
                System.out.print("Introduce la fecha (dd/MM/yyyy): ");

                // Leemos la entrada del usuario como una cadena de texto (String)
                String fechaStr = sc.nextLine();

                // Llamamos al método que valida el día y mes
                if (!esFechaValida(fechaStr)) {
                    continue;  // Si la fecha no es válida, volvemos a pedirla
                }

                // Intentamos convertir la cadena leída en un objeto Date usando el formato esperado
                fecha = sdf.parse(fechaStr);

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Comprobar si la fecha ingresada es posterior a la fecha actual
                if (fecha.after(fechaActual)) {
                    // Si la fecha es posterior a la actual, es válida
                    fechaValida = true;
                } else {
                    // Si la fecha no es posterior a la actual, mostramos un mensaje
                    System.out.println("La fecha debe ser posterior a la fecha actual. Intenta nuevamente.");
                }
                //Si ocurre un error al intentar convertir la fecha (por ejemplo, si el formato es incorrecto),
                // entra en el bloque catch y muestra un mensaje de error
            } catch (ParseException e) {
                System.out.println("Formato de fecha incorrecto. Intenta nuevamente.");
            }
        }

        // Cuando la fecha es válida, la devolvemos
        return fecha;
    }

    /**
     * Elimina un jugador de la lista, si existe.
     */
    private static void eliminarJugador() {
        if (arbitros.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(arbitros);
        System.out.println("Para el jugador a eliminar...");
        Jugador eliminar = seleccionarJugador();
        arbitros.remove(eliminar);
        control.eliminarJugador(eliminar.getId_j());
    }

    /**
     * Modifica los datos de un jugador (nombre).
     */
    private static void modificarJugador() {
        if (arbitros.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        imprimirJugadores(arbitros);
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
        if (arbitros.isEmpty()) {
            System.out.println("No existen jugadores registrados");
            return;
        }
        System.out.println("Introduzca el id del jugador a buscar:");
        //Jugador encontrado = j.buscarJugador(asignarEntero());
        Jugador encontrado = control.leerJugador(asignarEntero());
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
            id_j_aux = asignarEntero(); // Obtener el ID ingresado por el usuario

            // Buscar el jugador en la lista de jugadores
            for (Jugador it : arbitros) {
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
        ArrayList<Jugador> salida = new ArrayList<>(arbitros);
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
        Date fecha = validarFechaFutura();

        // Solicitar al usuario el número máximo de participantes.
        System.out.println("Introduce el numero maximo de participantes: ");
        int num_max = asignarEntero();

        // Crear un nuevo objeto Torneo con los datos proporcionados.
        torneos.add(new Torneo(nombre, fecha, num_max));

        outerLoop:// Etiqueta para el bucle exterior
        do {
            torneos.getLast().getArbitros().add(contratarArbitro());
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
        control.crearTorneo(torneos.getLast());
    }

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

    private static void crearArbitro() {

        System.out.println("Introduce el nombre del arbitro: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce el numero de licencia del arbitro: ");
        int numeroLicencia = asignarEntero();

        Arbitro nuevo = new Arbitro(nombre, numeroLicencia);

        control.crearArbitro(nuevo);
    }

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
            eliminar = control.leerArbitro(asignarEntero());

        } while (eliminar == null);
        //arbitros.remove(eliminar);
        control.eliminarArbitro(eliminar.getId());

    }

    private static void modificarArbitro() {
        // Verificamos si existen torneos que no hayan sido jugados
        ArrayList<Arbitro> arbitros = control.leerTodosArbitros();
        if (arbitros.isEmpty()) {
            System.out.println("No existen arbitros registrados");
            return; // Si no hay arbitros disponibles, salimos del método
        }

        // Imprimimos los torneos que no han sido jugados
        imprimirArbitros(arbitros);

        // Pedimos al usuario que seleccione el torneo que desea modificar
        System.out.println("Para el arbitro a modificar: ");
        Arbitro modif = control.leerArbitro(asignarEntero());// Seleccionamos el torneo a modificar

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
                    modif.setNumeroLicencia(asignarEntero());
                    break;
                case "3": // Salimos y guardamos los cambios en el arbitro                    
                    control.editarArbitro(modif);
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

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

    private static Arbitro contratarArbitro() {

        System.out.println("Introduce el nombre del arbitro: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce el numero de licencia del arbitro: ");
        int numeroLicencia = asignarEntero();

        return new Arbitro(nombre, numeroLicencia);
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
                    modif.setNum_max_jugadores(asignarEntero());
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
        Torneo encontrado = control.leerTorneo(asignarEntero());

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
        if (arbitros.isEmpty()) {
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
            e.printStackTrace();
            System.out.println("Error en la transacción. Se ha hecho rollback.");
        } finally {
            // Cerrar el EntityManager
            em.close();
            emf.close();
        }
    }

}
