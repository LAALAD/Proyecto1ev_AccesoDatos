package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase que representa un torneo en el que los jugadores pueden inscribirse y
 * competir. El torneo tiene un identificador, un nombre, una fecha, un número
 * máximo de jugadores y un estado que indica si las inscripciones están
 * abiertas. El torneo permite inscribir jugadores, jugar partidas y generar un
 * ranking de los jugadores basándose en su desempeño.
 *
 * @author adria
 */
public class Torneo implements Serializable {

    private int id_t;
    private String nombre;
    private String fecha;
    private int num_max_jugadores;
    private boolean inscripciones_abiertas;
    //private boolean jugado = false;
    private ArrayList<Jugador> inscritos = new ArrayList<>();

    /**
     * Constructor de la clase Torneo. Inicializa un torneo con los datos
     * proporcionados.
     *
     * @param id_t El identificador único del torneo.
     * @param nombre El nombre del torneo.
     * @param fecha La fecha en la que se celebra el torneo.
     * @param num_max_jugadores El número máximo de jugadores permitidos en el
     * torneo.
     */
    public Torneo(int id_t, String nombre, String fecha, int num_max_jugadores) {
        this.id_t = id_t;
        this.nombre = nombre;
        this.fecha = fecha;
        this.num_max_jugadores = num_max_jugadores;
        this.inscripciones_abiertas = true; // Las inscripciones están abiertas por defecto
    }

    /**
     * Obtiene el nombre del torneo.
     *
     * @return El nombre del torneo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del torneo.
     *
     * @param nombre El nuevo nombre del torneo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }*/
    /**
     * Establece si las inscripciones al torneo están abiertas o cerradas.
     *
     * @param inscripciones_abiertas El estado de las inscripciones (true si
     * abiertas, false si cerradas).
     */
    public void setInscripciones_abiertas(boolean inscripciones_abiertas) {
        this.inscripciones_abiertas = inscripciones_abiertas;
    }

    /**
     * Verifica si las inscripciones al torneo están abiertas.
     *
     * @return true si las inscripciones están abiertas, false si están
     * cerradas.
     */
    public boolean isInscripciones_abiertas() {
        return inscripciones_abiertas;
    }

    /*public boolean isJugado() {
        return jugado;
    }*/
    /**
     * Obtiene el identificador único del torneo.
     *
     * @return El identificador único del torneo.
     */
    public int getId_t() {
        return id_t;
    }

    /**
     * Obtiene la fecha del torneo.
     *
     * @return La fecha del torneo.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Obtiene el número máximo de jugadores que pueden inscribirse en el
     * torneo.
     *
     * @return El número máximo de jugadores del torneo.
     */
    public int getNum_max_jugadores() {
        return num_max_jugadores;
    }

    /**
     * Obtiene la lista de jugadores inscritos en el torneo.
     *
     * @return La lista de jugadores inscritos.
     */
    public ArrayList<Jugador> getInscritos() {
        return inscritos;
    }

    /**
     * Establece el identificador único del torneo.
     *
     * @param id_t El nuevo identificador único del torneo.
     */
    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    /**
     * Establece la fecha del torneo.
     *
     * @param fecha La nueva fecha del torneo.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el número máximo de jugadores permitidos en el torneo.
     *
     * @param num_max_jugadores El nuevo número máximo de jugadores.
     */
    public void setNum_max_jugadores(int num_max_jugadores) {
        this.num_max_jugadores = num_max_jugadores;
    }

    /**
     * Establece la lista de jugadores inscritos en el torneo.
     *
     * @param inscritos La nueva lista de jugadores inscritos.
     */
    public void setInscritos(ArrayList<Jugador> inscritos) {
        this.inscritos = inscritos;
    }

    /**
     * Devuelve una representación en cadena del torneo, incluyendo su ID y
     * nombre.
     *
     * @return Una cadena con el ID y el nombre del torneo.
     */
    @Override
    public String toString() {
        return "ID: " + id_t + " NOMBRE: " + nombre;
    }

    /**
     * Simula el desarrollo de las partidas de moneda del torneo, jugando una
     * partida entre cada par de jugadores inscritos y luego genera un ranking.
     *
     * Se cierran las inscripciones y se genera un ranking después de las
     * partidas.
     */
    public void jugar() {
        // Se juega una partida entre todos los pares de jugadores inscritos
        for (int i = 0; i < inscritos.size(); i++) {
            for (int j = i + 1; j < inscritos.size(); j++) {
                Partida.jugar(inscritos.get(i), inscritos.get(j));
            }
        }
        // Las inscripciones se cierran después de jugar
        inscripciones_abiertas = false;
        // Se genera el ranking después de las partidas
        ranking();
    }

    /**
     * Simula el desarrollo de las partidas del torneo, pero usando un dado en
     * lugar de una moneda. Juega una partida entre cada par de jugadores
     * inscritos y luego genera un ranking.
     *
     * Se cierran las inscripciones y se genera un ranking después de las
     * partidas.
     */
    public void jugarDado() {
        // Se juega una partida entre todos los pares de jugadores inscritos usando dado
        for (int i = 0; i < inscritos.size(); i++) {
            for (int j = i + 1; j < inscritos.size(); j++) {
                Partida.jugarDado(inscritos.get(i), inscritos.get(j));
            }
        }
        // Las inscripciones se cierran después de jugar
        inscripciones_abiertas = false;
        // Se genera el ranking después de las partidas
        ranking();

    }

    /**
     * Ordena a los jugadores inscritos en el torneo según su rendimiento en las
     * partidas, de manera descendente (del mejor al peor), utilizando el método
     * de comparación definido en la clase Jugador.
     */
    public void ranking() {
        // Se ordenan los jugadores por sus puntuaciones (de mayor a menor)
        Collections.sort(inscritos);
        Collections.reverse(inscritos);// Reversa el orden para que los mejores estén al principio
    }

    /**
     * Permite inscribir a un jugador en el torneo. Verifica que las
     * inscripciones estén abiertas, que el jugador no esté ya inscrito y que
     * haya plazas disponibles en el torneo.
     *
     * @param entrada El jugador que se desea inscribir en el torneo.
     */
    public void inscribir(Jugador entrada) {
        if (!inscripciones_abiertas) { // Verifica si las inscripciones están cerradas
            System.out.println("Las inscripciones a este torneo han sido cerradas");
        } else if (comprobarJugadorInscrito(entrada)) { // Verifica si el jugador ya está inscrito en el torneo
            System.out.println("Ya estás inscrito en este torneo.");
        } else if (inscritos.size() >= num_max_jugadores) { // Verifica si el torneo ha alcanzado el número máximo de jugadores
            System.out.println("No hay plazas disponibles en el torneo");
        } else {
            // Si pasa todas las verificaciones, se inscribe al jugador
            inscritos.add(entrada);
        }
    }

    /**
     * Verifica si un jugador ya está inscrito en el torneo.
     *
     * @param entrada El jugador que se desea comprobar.
     * @return true si el jugador ya está inscrito, false en caso contrario.
     */
    public boolean comprobarJugadorInscrito(Jugador entrada) {
        // Recorre la lista de jugadores inscritos y verifica si el jugador ya está en ella
        for (Jugador it : inscritos) {
            if (it.getId_j() == entrada.getId_j()) {
                return true; // El jugador está inscrito
            }
        }
        return false; // El jugador no está inscrito
    }

}
