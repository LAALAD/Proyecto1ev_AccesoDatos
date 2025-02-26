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

    /**
     * Método estático que simula una partida de piedra papel o tijera entre dos
     * jugadores. Cada jugador elige piedra papel o tijera y el primero que gane
     * dos rondas se lleva la victoria. Si ambos jugadores sacan lo mismo, se
     * repite la ronda. El resultado se muestra por consola y las puntuaciones
     * de los jugadores se actualizan globalmente y en el torneo.
     *
     * @param p1 El primer jugador.
     * @param p2 El segundo jugador.
     */
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

    /**
     * Determina si un jugador ha ganado el torneo tras alcanzar 2 victorias y
     * actualiza las puntuaciones de todos los jugadores.
     *
     * <p>
     * El juego sigue hasta que un jugador consiga al menos 2 victorias. Una vez
     * que esto ocurre, se le declara ganador, se imprimen mensajes de victoria
     * y se actualizan las puntuaciones de los jugadores.</p>
     *
     * @param jugadores Lista de jugadores que participan en el torneo.
     * @param victorias Mapa que asocia cada jugador con la cantidad de rondas
     * ganadas.
     * @param partidaEnCurso Booleano que controla si la partida sigue activa.
     */
    public static void jugarBlackJack(ArrayList<TorneoXJugador> jugadoresXt) {
        // Inicializar el mazo de cartas
        Baraja mazo = new Baraja();
        mazo.barajar(); // Se baraja la baraja antes de empezar el juego

        // Crear la lista de jugadores a partir de los datos del torneo
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (TorneoXJugador j : jugadoresXt) {
            jugadores.add(j.getJugador());
        }

        System.out.println("==============================");
        System.out.println("Iniciando partida de Blackjack");

        // Contadores para llevar el número de rondas ganadas por cada jugador
        HashMap<Jugador, Integer> victorias = new HashMap<>();
        for (Jugador jugador : jugadores) {
            victorias.put(jugador, 0); //introduzco todos los jugadores con sus puntuaciones iniciales a 0
        }

        // El juego continúa hasta que un jugador gane 2 rondas
        boolean partidaEnCurso = true; // Variable de control para la partida
        while (partidaEnCurso) {
            System.out.println("---------------------------");
            System.out.println("""
                               Nueva ronda iniciada
                               """);
            //System.out.println("==========>" + mazo.cartasRestantes());

            // Verifica si hay suficientes cartas en el mazo, si no, lo repone y baraja
            //suficientes cartas son 2 cartas por jugador
            if (mazo.cartasRestantes() < jugadores.size() * 2) {//si no quedan cartas (minimo dos por jugador)
                //Se introduce nueva baraja
                //System.out.println("==========>" + mazo.cartasRestantes());
                System.out.println("No quedan cartas en el mazo.");
                System.out.println("Reponiendo y barajando la baraja...");
                mazo = new Baraja();
                mazo.barajar();
            }

            // Se reparten dos cartas iniciales a cada jugador
            for (Jugador jugador : jugadores) {
                jugador.resetMano();//quito las cartas de la ronda anterior
                jugador.recibirCarta(mazo.tomarCarta());
                jugador.recibirCarta(mazo.tomarCarta());

            }

            // Fase de turnos de cada jugador
            for (Jugador jugador : jugadores) {
                System.out.println(jugador.getNombre() + " comienza con: " + jugador.mostrarMano());

                boolean turnoActivo = true;
                while (turnoActivo) {//mientras sea mi turno
                    int puntajeActual = jugador.calcularPuntaje();//calculo el valor de mis cartas 

                    // Si el jugador supera los 21 puntos, pierde automáticamente su turno y pasa al siguiente
                    if (puntajeActual > 21) {
                        System.out.println(jugador.getNombre() + " se ha pasado con " + puntajeActual + " puntos.");
                        turnoActivo = false;
                        break;
                    }
                    System.out.println(jugador.getNombre() + ", tu puntaje es " + puntajeActual);

                    // Si aún hay cartas en el mazo, el jugador puede decidir si pedir otra carta o plantarse
                    if (mazo.cartasRestantes() > 0) {//si quedan cartas
                        System.out.println("¿Deseas pedir carta (1) o plantarte (2)?");

                        int eleccion = jugador.decidir(); // Método que cada jugador implementa para decidir automatico para pruebas
                        if (eleccion == 1) {//pide carta
                            jugador.recibirCarta(mazo.tomarCarta());
                            System.out.println(jugador.getNombre() + " pide carta y ahora tiene: " + jugador.mostrarMano());
                        } else {//se planta
                            System.out.println(jugador.getNombre() + " se planta con: " + puntajeActual + " puntos.");
                            turnoActivo = false;//acaba el turno
                        }
                    } else { // Si no hay cartas en el mazo, se repone y se baraja una nueva baraja
                        System.out.println("No quedan cartas en el mazo.");
                        System.out.println(jugador.getNombre() + " se planta con: " + puntajeActual + " puntos.");
                        turnoActivo = false; // Evita que siga pidiendo cartas
                        System.out.println("Reponiendo y barajando la baraja...");
                        mazo = new Baraja();
                        mazo.barajar();
                    }
                } //acaba el turno
            }//salta al siguiente jugador

            // Determinar al ganador de la ronda
            Jugador ganadorRonda = null;//variable para guardar el jugador ganador  que se ira actualizando cuando encuentre uno mejor
            int mejorPuntaje = 0; //variable para guardar el puntaje que se ira actualizando cuando encuentre una mejor
            // calculo el puntaje mas alto
            for (Jugador jugador : jugadores) { //comparo todos los jugadores
                int puntaje = jugador.calcularPuntaje(); // guardo el puntaje del jugador evaluado actualmente

                // Se verifica quién tiene el mejor puntaje sin pasarse de 21
                if (puntaje <= 21 && puntaje > mejorPuntaje) { //Si se encuentra uno mejor
                    //se actualiza el mejor registrado
                    ganadorRonda = jugador;
                    mejorPuntaje = puntaje;
                } else if (puntaje == mejorPuntaje && ganadorRonda != null) {
                    // Si otro jugador tiene el mismo puntaje máximo que el ganador actual,
                    // significa que hay un empate y se anula la victoria de esta ronda.
                    ganadorRonda = null; // En caso de empate, no hay ganador
                }
            }

            // Se registra la victoria si hay un ganador de la ronda
            if (ganadorRonda != null) { //si se ha registrado algun ganador
                System.out.println("El ganador de esta ronda es " + ganadorRonda.getNombre() + " con " + mejorPuntaje + " puntos.");
                victorias.put(ganadorRonda, victorias.get(ganadorRonda) + 1);//actualizo sus victorias
            } else { //si no se registro a nadie es que hubo empate
                System.out.println("La ronda terminó en empate.");
            }

            // Se revisa si algún jugador ha ganado 2 rondas para finalizar la partida
            for (Jugador jugador : jugadores) {
                if (victorias.get(jugador) >= 2) {
                    //si para la clave(jugador) en su valor hay un numero mayor o igual a 2 ese jugador gana la ronda
                    System.out.println("\nEl ganador del torneo es: " + jugador.getNombre() + "\n");

                    System.out.println("El ganador de la partida ha sido: ");
                    System.out.println(jugador.getNombre());

                    // Se actualizan las puntuaciones de los jugadores al final del torneo
                    for (Jugador j : jugadores) {
                        if (j.equals(jugador)) {
                            // Solo el ganador obtiene puntos
                            j.actualizarPuntuacion(true);
                            j.actualizarPuntuacionTorneo(true);
                        } else {
                            // Los demás jugadores no suman puntos
                            j.actualizarPuntuacion(false);
                            j.actualizarPuntuacionTorneo(false);
                        }
                    }

                    // Termina el torneo / partida
                    partidaEnCurso = false;// Se finaliza la partida porque ya hay un ganador
                    break;// Se interrumpe el bucle porque ya se ha encontrado al ganador
                }
            }
        }

        System.out.println("Fin de la partida");
        System.out.println("==============================");
    }
}
