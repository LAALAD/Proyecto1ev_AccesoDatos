package Operativa;

import DAO_Implementaciones.JugadorDAOImplements;
import DAO_Implementaciones.TorneoDAOImplements;
import IO.ConexionBBDD;
import IO.InicializarBBDD;
import Modelo.Jugador;
import Modelo.Torneo;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Usuario
 */
public class Menu {

    public static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<Torneo> torneos = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    private static JugadorDAOImplements j = new JugadorDAOImplements();
    private static TorneoDAOImplements t = new TorneoDAOImplements();

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

    public static void menuTorneo() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Crear Torneo");
            System.out.println("2.- Eliminar Torneo");
            System.out.println("3.- Modificar Torneo");
            System.out.println("4.- Buscar Torneo");
            System.out.println("5.- Listar Torneos");
            System.out.println("6.- Salir");

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
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("6")); //Mientras seleccione un numero distinto de 6 seguir el bucle
    }

    public static void menuPartida() {
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("1.- Inscribir Jugador a Torneo");
            System.out.println("2.- Jugar Torneo");
            System.out.println("3.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    inscribir();
                    break;
                case "2":
                    jugarTorneo();
                    break;
                case "3":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 3 seguir el bucle
    }

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
                    ConexionBBDD.desconectarBBDD();
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void crearJugador() {
        System.out.println("Introduce el nombre del jugador: ");
        String nombre = sc.nextLine();
        int id = asignarId_j();
        jugadores.add(new Jugador(id, nombre));
        j.crearJugador(jugadores.getLast());
    }

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
        System.out.println(encontrado.toString());
    }

    public static void crearTorneo() {
        int id = asignarId_t();
        System.out.println("Introduce un nombre para el torneo: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce la fecha del torneo(año): ");
        int fecha = asignarEntero();
        System.out.println("Introduce el numero maximo de participantes: ");
        int num_max = asignarEntero();
        Torneo nuevo = new Torneo(id, nombre, fecha, num_max);
        System.out.println("¿Están abiertas las inscripciones al torneo? (s/n)");
        String respuesta;
        do {
            respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                nuevo.setInscripciones_abiertas(true);
                break;
            }
            if (respuesta.equalsIgnoreCase("n")) {
                nuevo.setInscripciones_abiertas(false);
                break;
            }
            System.out.println("Indique s/n");
        } while (true);
        if (t.crearTorneo(nuevo)) {
            torneos.add(nuevo);
        }
    }

    public static void eliminarTorneo() {
        if (torneos.isEmpty()) {
            System.out.println("No existen torneos registrados");
            return;
        }
        imprimirTorneos(torneos);
        System.out.println("Para el torneo a eliminar: ");
        Torneo eliminar = seleccionarTorneo();
        torneos.remove(eliminar);
        t.eliminarTorneo(eliminar);
    }

    public static void buscarTorneo() { //busca en la bbdd   
        if (torneos.isEmpty()) {
            System.out.println("No existen torneos registrados");
            return;
        }
        System.out.println("Indica el id del torneo a buscar:");
        Torneo encontrado = t.buscarTorneo(asignarEntero());
        System.out.println(encontrado.toString());
    }

    public static Jugador seleccionarJugador() {//busca en memoria
        int id_j_aux;
        do {
            System.out.println("Selecciona el id de un jugador: ");
            id_j_aux = asignarEntero();
            for (Jugador it : jugadores) {
                if (it.getId_j() == id_j_aux) {
                    return it;
                }
            }
            System.out.println("Id inexistente...");
        } while (true);

    }

    public static Torneo seleccionarTorneo() { //busca en memoria
        do {
            System.out.println("Selecciona el id de un torneo: ");
            int id_t_aux = asignarEntero();

            for (Torneo it : torneos) {
                if (it.getId_t() == id_t_aux) {
                    return it;
                }
            }
        } while (true);
    }

    public static Jugador seleccionarJugador(ArrayList<Jugador> jugadores) {//busca en memoria
        int id_j_aux;
        do {
            System.out.println("Selecciona el id de un jugador: ");
            id_j_aux = asignarEntero();
            for (Jugador it : jugadores) {
                if (it.getId_j() == id_j_aux) {
                    return it;
                }
            }
            System.out.println("Id no valido...");
        } while (true);

    }

    public static Torneo seleccionarTorneo(ArrayList<Torneo> torneos) { //busca en memoria
        do {
            System.out.println("Selecciona el id de un torneo: ");
            int id_t_aux = asignarEntero();

            for (Torneo it : torneos) {
                if (it.getId_t() == id_t_aux) {
                    return it;
                }
            }
            System.out.println("Id no valido...");
        } while (true);
    }

    public static int asignarEntero() {
        int salida;
        do {
            try {
                salida = Integer.valueOf(sc.nextLine());
                return salida;
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true);
    }

    public static void imprimirJugadores(ArrayList<Jugador> jugadores) {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores disponibles");
            return;
        }
        for (Jugador it : jugadores) {
            System.out.print(it.toString());
            System.out.println("");
        }
    }

    public static void imprimirTorneos(ArrayList<Torneo> torneos) {
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos disponibles");
            return;
        }
        for (Torneo it : torneos) {
            System.out.print(it.toString());
            System.out.println("");
        }
    }

    public static void inscribir() {
        if (torneos.isEmpty() || noJugados().isEmpty()) {
            System.out.println("No existen torneos registrados para inscribir");
            return;
        }
        if (jugadores.isEmpty()) {
            System.out.println("No existen jugadores registrados para inscribir");
            return;
        }

        imprimirTorneos(noJugados());
        Torneo seleccion_torneo = seleccionarTorneo(noJugados());

        imprimirJugadores(noInscritos(seleccion_torneo));
        Jugador seleccion_jugador = seleccionarJugador(noInscritos(seleccion_torneo));

        seleccion_torneo.inscribir(seleccion_jugador);
    }

    public static void jugarTorneo() { //actualizar los datos de los jugadores inscritos
        imprimirTorneos(noJugados());
        if (torneos.isEmpty() || noJugados().isEmpty()) { //haria falta el || o vale con noJugados??
            System.out.println("No hay torneos disponibles para jugar");
            return;
        }

        Torneo selecionado = seleccionarTorneo(noJugados());
        if (!selecionado.isInscripciones_abiertas()) { //seleccionado.isJugado() o guardo el atributo en  la bbdd o uso este otro
            System.out.println("El torneo ya fue jugado");
        } else if (selecionado.getInscritos().size() <= 1) {
            System.out.println("No hay suficientes jugadores inscritos, no se puede jugar el torneo");
        } else {
            selecionado.jugar();
            t.guardarTorneoJugado(selecionado);
            for (Jugador j : selecionado.getInscritos()) {
                j.resetearPuntuacionTorneo();
            }
        }
    }

    public static ArrayList<Jugador> noInscritos(Torneo t) { //cambiar y no crear AL
        //ArrayList<Jugador> salida = jugadores; //asi solo le doy otra referencia no?
        //ArrayList<Jugador> salida = (ArrayList<Jugador>) jugadores.clone();
        ArrayList<Jugador> salida = new ArrayList<>(jugadores);
        salida.removeAll(t.getInscritos());
        return salida;
    }

    /*public static void imprimirNoInscritos(Torneo t) {
        //ArrayList<Jugador> salida = jugadores; //asi solo le doy otra referencia no?
        //ArrayList<Jugador> salida = (ArrayList<Jugador>) jugadores.clone(); no usar el clone xq da 
        ArrayList<Jugador> salida = new ArrayList<>(jugadores);
        salida.removeAll(t.getInscritos());
        
    }*/
    public static ArrayList<Torneo> noJugados() {
        ArrayList<Torneo> salida = new ArrayList<>();
        for (Torneo torneo : torneos) {
            if (torneo.isInscripciones_abiertas()) {
                salida.add(torneo);
            }
        }
        return salida;
    }

    public static void modificarTorneo() {
        if (noJugados().isEmpty()) {
            System.out.println("No existen torneos registrados");
            return;
        }
        imprimirTorneos(noJugados());

        System.out.println("Para el torneo a modificar: ");
        Torneo modif = seleccionarTorneo(noJugados());

        String opcion = "";
        do {
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
                    modif.setFecha(asignarEntero());
                    break;
                case "2":
                    System.out.println("Introduce el nuevo numero maximo de jugadores del torneo: ");
                    modif.setNum_max_jugadores(asignarEntero());
                    break;
                case "3":
                    t.modificarTorneo(modif);
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /*public static void guardarTorneosSerializados() {
        for (Torneo torneo : torneos) {
            TorneoDAOImplements.serializarTorneo(torneo);
        }
    }*/
    /*
        ConexionBBDD.getConnection();
        jugadores = j.listarJugadores();
        torneos = t.leerBBDDTorneos(jugadores);
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("0.- Crear Jugador");
            System.out.println("1.- Eliminar Jugador");
            System.out.println("2.- Modificar Jugador");
            System.out.println("3.- Buscar Jugador");
            System.out.println("4.- Listar Jugadores en BBDD");
            System.out.println("5.- Modificar Torneo");

            System.out.println("6.- Crear Torneo");
            System.out.println("7.- Eliminar Torneo");
            System.out.println("8.- Buscar Torneo");
            System.out.println("9.- Listar Torneos");
            System.out.println("10.- Inscribir Jugador a Torneo");
            System.out.println("11.- Jugar Torneo");
            System.out.println("12.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    crearJugador();
                    break;
                case "1":
                    eliminarJugador();
                    break;
                case "2":
                    modificarJugador();
                    break;
                case "3":
                    buscarJugador();
                    break;
                case "4":
                    imprimirJugadores(j.listarJugadores());
                    //imprimirJugadores(jugadores);
                    break;
                case "5":
                    modificarTorneo();
                    break;
                case "6":
                    crearTorneo();
                    break;
                case "7":
                    eliminarTorneo();
                    break;
                case "8":
                    buscarTorneo();
                    break;
                case "9":
                    imprimirTorneos(t.listarTorneos()); //sin jugadores inscritos
                    //imprimirTorneos(torneos); //con jugadores inscritos
                    break;
                case "10":
                    inscribir();
                    break;
                case "11":
                    jugarTorneo();
                    break;
                case "12":
                    //IO_BBDD.guardarBBDD(jugadores, torneos);
                    guardarTorneosSerializados();
                    ConexionBBDD.desconectarBBDD();
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
            }

        } while (!opcion.equals("12")); //Mientras seleccione un numero distinto de 4 seguir el bucle*/
}
