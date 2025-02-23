package logicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa un torneo en el que los jugadores pueden inscribirse y
 * competir. El torneo tiene un identificador, un nombre, una fecha, un número
 * máximo de jugadores y un estado que indica si las inscripciones están
 * abiertas. El torneo permite inscribir jugadores, jugar partidas y generar un
 * ranking de los jugadores basándose en su desempeño.
 *
 * @author adria
 */
@Entity
public class Torneo implements Serializable {

    @Id //Id -->PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK autogenerada por la BBDD por secuencias (auto o secuence)
    private int id_t;
    @Basic
    private String nombre;
    @Temporal(TemporalType.DATE) //Tipo fecha de tipo date (aaaa/mm/dd), podria ser tb timeStamp
    private Date fecha;
    @Basic
    private int num_max_jugadores;
    @Basic
    private boolean inscripciones_abiertas;
    //private boolean jugado = false;
    //private ArrayList<Jugador> inscritos = new ArrayList<>();

    // Relación 1:N con TorneoXJugador
    /*'cascade = CascadeType.ALL' propaga todas las operaciones de persistencia 
    (insertar, actualizar, eliminar) a las entidades relacionadas automáticamente.
    'orphanRemoval = true' elimina automáticamente las relaciones huérfanas 
    (es decir, las entradas en TorneoXJugador) si un jugador es eliminado de la lista de inscritos.*/
    
    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<TorneoXJugador> inscritos = new ArrayList<>();

    public Torneo() {
    }

    /**
     * Constructor de la clase Torneo. Inicializa un torneo con los datos
     * proporcionados.
     *
     * @param nombre El nombre del torneo.
     * @param fecha La fecha en la que se celebra el torneo.
     * @param num_max_jugadores El número máximo de jugadores permitidos en el
     * torneo.
     */
    public Torneo(String nombre, Date fecha, int num_max_jugadores) {
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
    public Date getFecha() {
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
    public ArrayList<TorneoXJugador> getInscritos() {
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
    public void setFecha(Date fecha) {
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
    public void setInscritos(ArrayList<TorneoXJugador> inscritos) {
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
                Partida.jugar(inscritos.get(i).getJugador(), inscritos.get(j).getJugador());
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
                Partida.jugarDado(inscritos.get(i).getJugador(), inscritos.get(j).getJugador());
            }
        }
        // Las inscripciones se cierran después de jugar
        inscripciones_abiertas = false;
        // Se genera el ranking después de las partidas
        ranking();

    }

    public void jugarPiedraPapelTijera() {
        // Se juega una partida entre todos los pares de jugadores inscritos usando dado
        for (int i = 0; i < inscritos.size(); i++) {
            for (int j = i + 1; j < inscritos.size(); j++) {
                Partida.jugarPiedraPapelTijera(inscritos.get(i).getJugador(), inscritos.get(j).getJugador());
            }
        }
        // Las inscripciones se cierran después de jugar
        inscripciones_abiertas = false;
        // Se genera el ranking después de las partidas
        ranking();

    }

    public void jugarBlackJack() {
        // Se juega una partida entre todos los pares de jugadores inscritos usando dado

        Partida.jugarBlackJack(inscritos);

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
        // Creamos una lista para almacenar solo los jugadores
        ArrayList<Jugador> participantes = new ArrayList<>();

        // Extraemos a los jugadores de los objetos TorneoXJugador
        for (TorneoXJugador torneoXJugador : inscritos) {
            participantes.add(torneoXJugador.getJugador()); // Añadimos solo al jugador
        }

        Collections.sort(participantes);
        System.out.println(participantes);
        Collections.reverse(participantes);
        System.out.println("reverse "+ participantes);

        for (int i = 0; i < participantes.size(); i++) {
            Jugador participante = participantes.get(i);
            for (TorneoXJugador torneoXJugador : inscritos) {
                Jugador jugador = torneoXJugador.getJugador();
                if (participante.getId_j() == jugador.getId_j()) {
                    torneoXJugador.setPosicion(i + 1);
                }
            }
        }

    }

    /*
    public void ranking() {
        // Se ordenan los jugadores por sus puntuaciones (de mayor a menor)
        Collections.sort(inscritos);
        Collections.reverse(inscritos);// Reversa el orden para que los mejores estén al principio
    }*/
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
            //inscritos.add(new TorneoXJugador(this, entrada));
            // Si pasa todas las verificaciones, se inscribe al jugador
            //inscritos.add(entrada);
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
        //for (Jugador it : inscritos) {
        for (TorneoXJugador it : inscritos) {
            if (it.getJugador().getId_j() == entrada.getId_j()) {
                return true; // El jugador está inscrito
            }
        }
        return false; // El jugador no está inscrito
    }

    public void top3() {
        System.out.println("TOP 3: ");
        for (int i = 0; i < inscritos.size(); i++) {
            System.out.println(inscritos.get(i).getPosicion() + ".- " + inscritos.get(i).getJugador().getNombre());
            if (i >= 3) { //si estoy en la cuarta posicion ya no quiero mostrarlo
                return;
            }
        }
    }
}
