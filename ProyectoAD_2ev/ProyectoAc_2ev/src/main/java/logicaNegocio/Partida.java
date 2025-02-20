package logicaNegocio;

import logicaNegocio.Jugador;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase abstracta que representa una partida entre dos jugadores. Contiene
 * métodos estáticos para jugar una partida con una moneda o con un dado. Las
 * partidas se juegan entre dos jugadores y se registra el resultado,
 * actualizando las puntuaciones globales y de torneo de los jugadores
 * involucrados.
 *
 * Los juegos son entre dos jugadores seleccionados previamente, donde cada uno
 * tiene que lanzar un objeto (moneda o dado) y el primero que gane dos rondas
 * se lleva la victoria.
 *
 * @author adria
 */
public abstract class Partida {

    private Jugador p1;
    private Jugador p2;

    /**
     * Método estático que simula una partida entre dos jugadores utilizando una
     * moneda. Cada jugador lanza una moneda y el primero que gane dos rondas se
     * lleva la victoria. El resultado se muestra por consola y las puntuaciones
     * de los jugadores se actualizan globalmente y en el torneo.
     *
     * @param p1 El primer jugador.
     * @param p2 El segundo jugador.
     */
    public static void jugar(Jugador p1, Jugador p2) {
        //Resetear variables locales del torneo
        System.out.println("==============================");
        System.out.println("Iniciando partida entre");

        System.out.println(p1.getNombre() + " VS " + p2.getNombre());

        // No es necesario, pero por claridad, el jugador 1 selecciona cara o cruz
        p1.seleccionarCara();
        //System.out.println(p1.toString());
        //System.out.println(p2.toString());
        if (p1.getSeleccion() == 2) { //false == 2
            p2.setSeleccion(1);
        }

        int contp1 = 0;  // Contador de victorias del jugador 1
        int contp2 = 0;  // Contador de victorias del jugador 2
        int rondas = 0;  // Contador de rondas jugadas

        // El juego continúa hasta que un jugador gane 2 rondas
        while (contp1 < 2 && contp2 < 2) {
            System.out.println("Ronda: " + rondas);

            // Se lanza la moneda y se compara si coincide con la selección de cada jugador
            if (p1.getSeleccion() == p1.cogerMoneda().lanzar()) {
                contp1++; // Si el jugador 1 gana, aumenta su contador
            } else {
                contp2++;// Si el jugador 2 gana, aumenta su contador
            }
            rondas++; // Incrementa el número de rondas jugadas
        }

        // Se determina el ganador y se actualizan las puntuaciones
        System.out.println("El ganador de la partida ha sido: ");
        if (contp1 >= 2) { //Si ha ganado p1
            //Actualiza puntuacion Global
            System.out.println(p1.getNombre());
            p1.actualizarPuntuacion(true);
            p2.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p1.actualizarPuntuacionTorneo(true);
            p2.actualizarPuntuacionTorneo(false);
        } else { //Si ha ganado p2
            //Actualiza puntuacion Global
            System.out.println(p2.getNombre());
            p2.actualizarPuntuacion(true);
            p1.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p2.actualizarPuntuacionTorneo(true);
            p1.actualizarPuntuacionTorneo(false);
        }

        System.out.println("Fin de partida");
        System.out.println("==============================");

    }

    /**
     * Método estático que simula una partida entre dos jugadores utilizando un
     * dado. Cada jugador lanza un dado y el primero que gane dos rondas se
     * lleva la victoria. Si ambos jugadores sacan el mismo número, se repite el
     * lanzamiento del dado hasta que haya un desempate. El resultado se muestra
     * por consola y las puntuaciones de los jugadores se actualizan globalmente
     * y en el torneo.
     *
     * @param p1 El primer jugador.
     * @param p2 El segundo jugador.
     */
    public static void jugarDado(Jugador p1, Jugador p2) {
        //Resetear variables locales del torneo
        System.out.println("==============================");
        System.out.println("Iniciando partida entre");

        System.out.println(p1.getNombre() + " VS " + p2.getNombre());

        int contp1 = 0;  // Contador de victorias del jugador 1
        int contp2 = 0;  // Contador de victorias del jugador 2
        int rondas = 0;  // Contador de rondas jugadas

        // El juego continúa hasta que un jugador gane 2 rondas
        while (contp1 < 2 && contp2 < 2) {
            System.out.println("Ronda: " + rondas);

            // El jugador 1 lanza el dado y muestra su resultado
            p1.setSeleccion(p1.cogerDado().lanzar());
            System.out.println("El jugador " + p1.getNombre() + " ha sacado: " + p1.getSeleccion());

            // El jugador 2 lanza el dado hasta que no saquen el mismo número
            do {
                p2.setSeleccion(p2.cogerDado().lanzar());
                System.out.println("El jugador " + p2.getNombre() + " ha sacado: " + p2.getSeleccion());
                // Si ambos jugadores sacan el mismo valor, se repite el lanzamiento
                if (p1.getSeleccion() == p2.getSeleccion()) {
                    System.out.println("Lanzando dado de nuevo para desempatar...");
                }
            } while (p1.getSeleccion() == p2.getSeleccion());

            // El jugador que saque el número mayor gana la ronda
            if (p1.getSeleccion() > p2.getSeleccion()) {
                contp1++;
            } else {
                contp2++;
            }
            rondas++; // Incrementa el número de rondas jugadas
        }

        // Se determina el ganador y se actualizan las puntuaciones
        System.out.println("El ganador de la partida ha sido: ");
        if (contp1 >= 2) {
            //Actualiza puntuacion Global
            System.out.println(p1.getNombre());
            p1.actualizarPuntuacion(true);
            p2.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p1.actualizarPuntuacionTorneo(true);
            p2.actualizarPuntuacionTorneo(false);
        } else {
            //Actualiza puntuacion Global
            System.out.println(p2.getNombre());
            p2.actualizarPuntuacion(true);
            p1.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p2.actualizarPuntuacionTorneo(true);
            p1.actualizarPuntuacionTorneo(false);
        }

        System.out.println("Fin de partida");
        System.out.println("==============================");
    }

    public static void jugarPiedraPapelTijera(Jugador p1, Jugador p2) {

        //Resetear variables locales del torneo
        System.out.println("==============================");
        System.out.println("Iniciando partida entre");

        System.out.println(p1.getNombre() + " VS " + p2.getNombre());

        int contp1 = 0;  // Contador de victorias del jugador 1
        int contp2 = 0;  // Contador de victorias del jugador 2
        int rondas = 0;  // Contador de rondas jugadas

        // El juego continúa hasta que un jugador gane 2 rondas
        while (contp1 < 2 && contp2 < 2) {
            System.out.println("Ronda: " + rondas);
            System.out.println(p1.getNombre() + " elija:");
            p1.seleccionarPiedraPapelTijera();
            System.out.println(p2.getNombre() + " elija:");
            p2.seleccionarPiedraPapelTijera();

            // Se compara si
            if (p1.getSeleccion() == p2.getSeleccion()) {
                rondas++;
                System.out.println("Ronda empatada");
                continue;
            }
            switch (p1.getSeleccion()) {
                case 1:
                    if (p2.getSeleccion() == 3) {
                        System.out.println("Ronda ganada: " + p1.getNombre());
                        contp1++;
                    } else {
                        System.out.println("Ronda ganada: " + p2.getNombre());
                        contp2++;
                    }
                    break;
                case 2:
                    if (p2.getSeleccion() == 1) {
                        System.out.println("Ronda ganada: " + p1.getNombre());
                        contp1++;
                    } else {
                        System.out.println("Ronda ganada: " + p2.getNombre());
                        contp2++;
                    }

                    break;
                case 3:
                    if (p2.getSeleccion() == 2) {
                        System.out.println("Ronda ganada: " + p1.getNombre());
                        contp1++;
                    } else {
                        System.out.println("Ronda ganada: " + p2.getNombre());
                        contp2++;
                    }
                    break;
            }

            rondas++; // Incrementa el número de rondas jugadas
        }

        // Se determina el ganador y se actualizan las puntuaciones
        System.out.println("El ganador de la partida ha sido: ");
        if (contp1 >= 2) { //Si ha ganado p1
            //Actualiza puntuacion Global
            System.out.println(p1.getNombre());
            p1.actualizarPuntuacion(true);
            p2.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p1.actualizarPuntuacionTorneo(true);
            p2.actualizarPuntuacionTorneo(false);
        } else { //Si ha ganado p2
            //Actualiza puntuacion Global
            System.out.println(p2.getNombre());
            p2.actualizarPuntuacion(true);
            p1.actualizarPuntuacion(false);
            //Actualiza puntuacion Torneo
            p2.actualizarPuntuacionTorneo(true);
            p1.actualizarPuntuacionTorneo(false);
        }

        System.out.println("Fin de partida");
        System.out.println("==============================");
    }

    public static void jugarBlackJack(ArrayList<Jugador> jugadores) {
        // Inicializar el mazo de cartas
        Baraja mazo = new Baraja();
        mazo.barajar();

        System.out.println("==============================");
        System.out.println("Iniciando partida de Blackjack");

        // Contadores de victorias por jugador
        HashMap<Jugador, Integer> victorias = new HashMap<>();
        for (Jugador jugador : jugadores) {
            victorias.put(jugador, 0);
        }

        // El juego continúa hasta que un jugador gane 2 rondas
        boolean partidaEnCurso = true;
        while (partidaEnCurso) {
            System.out.println("""
                               Nueva ronda iniciada
                               """);

            // Repartir dos cartas iniciales a cada jugador
            for (Jugador jugador : jugadores) {
                jugador.resetMano();
                jugador.recibirCarta(mazo.tomarCarta());
                jugador.recibirCarta(mazo.tomarCarta());
            }

            // Fase de juego para cada jugador
            for (Jugador jugador : jugadores) {
                System.out.println(jugador.getNombre() + " comienza con: " + jugador.mostrarMano());

                boolean turnoActivo = true;
                while (turnoActivo) {
                    int puntajeActual = jugador.calcularPuntaje();
                    if (puntajeActual > 21) {
                        System.out.println(jugador.getNombre() + " se ha pasado con " + puntajeActual + " puntos.");
                        turnoActivo = false;
                        break;
                    }

                    System.out.println(jugador.getNombre() + ", tu puntaje es " + puntajeActual);
                    System.out.println("¿Deseas pedir carta (1) o plantarte (2)?");

                    int eleccion = jugador.decidir(); // Método que cada jugador implementa para decidir
                    if (eleccion == 1) {
                        jugador.recibirCarta(mazo.tomarCarta());
                        System.out.println(jugador.getNombre() + " pide carta y ahora tiene: " + jugador.mostrarMano());
                    } else {
                        System.out.println(jugador.getNombre() + " se planta con: " + puntajeActual + " puntos.");
                        turnoActivo = false;
                    }
                }
            }

            // Determinar al ganador de la ronda
            Jugador ganadorRonda = null;
            int mejorPuntaje = 0;
            for (Jugador jugador : jugadores) {
                int puntaje = jugador.calcularPuntaje();
                if (puntaje <= 21 && puntaje > mejorPuntaje) {
                    ganadorRonda = jugador;
                    mejorPuntaje = puntaje;
                } else if (puntaje == mejorPuntaje && ganadorRonda != null) {
                    ganadorRonda = null; // Empate
                }
            }

            if (ganadorRonda != null) {
                System.out.println("El ganador de esta ronda es " + ganadorRonda.getNombre() + " con " + mejorPuntaje + " puntos.");
                victorias.put(ganadorRonda, victorias.get(ganadorRonda) + 1);
            } else {
                System.out.println("La ronda terminó en empate.");
            }

            // Comprobar si alguien ha ganado 2 rondas
            for (Jugador jugador : jugadores) {
                if (victorias.get(jugador) >= 2) {
                    System.out.println("\nEl ganador del torneo es: " + jugador.getNombre() + "\n");
                    partidaEnCurso = false;
                    break;
                }
            }
        }

        System.out.println("Fin de la partida");
        System.out.println("==============================");
    }
}
