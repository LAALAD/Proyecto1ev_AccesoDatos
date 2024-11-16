package Operativa;


import IO.ConexionBBDD;
import Modelo.Torneo;
import Modelo.Jugador;
import IO.InicializarBBDD;
import java.sql.SQLException;
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
public class Operativa {

    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String DORADO = "\u001B[33m";
    private static final String PLATEADO = "\u001B[37m";
    private static final String COBRE = "\u001B[35m";

    private static ArrayList<Jugador> jugadores = new ArrayList<>();
    private static ArrayList<Torneo> torneos = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);

    public static void menu() {
        ConexionBBDD.getConnection();
        jugadores = InicializarBBDD.leerBBDDJugadores();
        torneos = InicializarBBDD.leerBBDDTorneos(jugadores);
        String opcion = "";
        do {
            System.out.println("Elija una opcion");
            System.out.println("0.- Crear Jugador");
            System.out.println("1.- Crear Torneo");
            System.out.println("2.- Inscribir");
            System.out.println("3.- Jugar Torneo");
            System.out.println("4.- Listar Jugadores");
            System.out.println("5.- Listar Torneos");
            System.out.println("6.- Crear Jugadores");
            System.out.println("7.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    crearJugador();
                    break;
                case "1":
                    crearTorneo();
                    break;
                case "2":
                    inscribir();
                    break;
                case "3":
                    jugarTorneo();
                    break;
                case "4":
                    imprimirJugadores();
                    break;
                case "5":
                    imprimirTorneos();
                    break;
                case "6":

                    break;
                case "7":
                    InicializarBBDD.guardarBBDD(jugadores, torneos);

                    ConexionBBDD.desconectarBBDD();
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }

        } while (!opcion.equals("7")); //Mientras seleccione un numero distinto de 4 seguir el bucle

    }

    public static void crearJugador() {
        System.out.println("Introduce el nombre del jugador: ");
        jugadores.add(new Jugador(jugadores.size(), sc.nextLine()));
    }

    public static void crearTorneo() {
        System.out.println("Introduce :");
        int fecha = Integer.valueOf(sc.nextLine());
        int n_max = Integer.valueOf(sc.nextLine());
        torneos.add(new Torneo(torneos.size(), fecha, n_max));
    }

    public static void inscribir() {
        Torneo seleccion_torneo = seleccionarTorneo();
        Jugador seleccion_jugador = seleccionarJugador();
        seleccion_torneo.inscribir(seleccion_jugador);
    }

    public static Torneo seleccionarTorneo() {
        imprimirTorneos();
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

    public static Jugador seleccionarJugador() {
        imprimirJugadores();
        int id_j_aux;
        do {
            System.out.println("Selecciona el id de un jugador: ");
            id_j_aux = Integer.valueOf(sc.nextLine());
            for (Jugador it : jugadores) {
                if (it.getId_j() == id_j_aux) {
                    return it;
                }
            }
            System.out.println("Id inexistente...");
        } while (true);

    }

    public static void imprimirJugadores() {
        for (Jugador it : jugadores) {
            System.out.print(it.toString());
            System.out.println("");
        }
    }

    public static void imprimirTorneos() {
        for (Torneo it : torneos) {
            System.out.print(it.toString());
            System.out.println("");
        }
    }

    public static void imprimirLindo(ArrayList<Jugador> jugadores) {
        int puesto = 0;
        System.out.println(VERDE + "PUESTO\tPARTICIPANTE\tVICTORIAS\tWINRATE" + RESET);
        for (Jugador it : jugadores) {
            puesto++;
            switch (puesto) {
                case 1:
                    System.out.println(DORADO + puesto + "\t" + it.getNombre() + "\t" + "\t" + it.getPartidasGanadas() + "\t" + "\t" + (it.getPartidasGanadas() / it.getPartidasJugadas()) + RESET);
                    break;
                case 2:
                    System.out.println(PLATEADO + puesto + "\t" + it.getNombre() + "\t" + "\t" + it.getPartidasGanadas() + "\t" + "\t" + (it.getPartidasGanadas() / it.getPartidasJugadas()) + RESET);
                    break;
                case 3:
                    System.out.println(COBRE + puesto + "\t" + it.getNombre() + "\t" + "\t" + it.getPartidasGanadas() + "\t" + "\t" + (it.getPartidasGanadas() / it.getPartidasJugadas()) + RESET);
                    break;
                default:
                    System.out.println(puesto + "\t" + it.getNombre() + "\t" + "\t" + it.getPartidasGanadas() + "\t" + "\t" + (it.getPartidasGanadas() / it.getPartidasJugadas()));
                    break;
            }
//            System.out.println(puesto + "\t" + it.getNombre() + "\t" + "\t" + it.getPartidasGanadas() + "\t" + "\t" + (it.getPartidasGanadas() / it.getPartidasJugadas()));
            //System.out.println("");
        }
    }

    public static void jugarTorneo() {

    }

    public void comprobarDatosCargados() {
        for (Jugador it : jugadores) {
            System.out.println(it.toString());
        }
        for (Torneo it : torneos) {
            System.out.println(it.toString());
        }
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

}
