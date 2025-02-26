/**
 * Clase ControladorLogico gestiona la lógica del negocio en la aplicación.
 * Se encarga de interactuar con la capa de persistencia a través del ControladorPersitencia.
 */
package logicaNegocio;

import java.util.ArrayList;
import persistencia.ControladorPersitencia;

/**
 * Clase encargada de gestionar las operaciones CRUD para Jugadores, Torneos,
 * Árbitros y sus relaciones en la base de datos.
 */
public class ControladorLogico {

    ControladorPersitencia controlPersis = new ControladorPersitencia();

    // METODOS CRUD JUGADOR
    /**
     * Crea un nuevo jugador en la base de datos.
     * @param jug Objeto Jugador a crear.
     */
    public void crearJugador(Jugador jug) {
        controlPersis.crearJugador(jug);
    }

    /**
     * Elimina un jugador según su ID.
     * @param id Identificador del jugador a eliminar.
     */
    public void eliminarJugador(int id) {
        controlPersis.eliminarJugador(id);
    }

    /**
     * Edita los datos de un jugador existente.
     * @param jug Objeto Jugador con la información actualizada.
     */
    public void editarJugador(Jugador jug) {
        controlPersis.editarJugador(jug);
    }

    /**
     * Obtiene un jugador por su ID.
     * @param id Identificador del jugador.
     * @return Objeto Jugador correspondiente al ID.
     */
    public Jugador leerJugador(int id) {
        return controlPersis.leerJugador(id);
    }

    /**
     * Obtiene la lista de todos los jugadores.
     * @return Lista de jugadores almacenados en la base de datos.
     */
    public ArrayList<Jugador> leerTodosJugadores() {
        return controlPersis.leerTodosJugadores();
    }

    // METODOS CRUD Torneo
    /**
     * Crea un nuevo torneo en la base de datos.
     * @param tor Objeto Torneo a crear.
     */
    public void crearTorneo(Torneo tor) {
        controlPersis.crearTorneo(tor);
    }

    /**
     * Elimina un torneo por su ID.
     * @param id Identificador del torneo a eliminar.
     */
    public void eliminarTorneo(int id) {
        controlPersis.eliminarTorneo(id);
    }

    /**
     * Edita los datos de un torneo existente.
     * @param tor Objeto Torneo con la información actualizada.
     */
    public void editarTorneo(Torneo tor) {
        controlPersis.editarTorneo(tor);
    }

    /**
     * Obtiene un torneo por su ID.
     * @param id Identificador del torneo.
     * @return Objeto Torneo correspondiente al ID.
     */
    public Torneo leerTorneo(int id) {
        return controlPersis.leerTorneo(id);
    }

    /**
     * Obtiene la lista de todos los torneos.
     * @return Lista de torneos almacenados en la base de datos.
     */
    public ArrayList<Torneo> leerTodosTorneos() {
        return controlPersis.leerTodosTorneos();
    }

    // METODOS CRUD TorneoXJugador
    /**
     * Crea una relación entre un torneo y un jugador en la base de datos.
     * @param txj Objeto TorneoXJugador a crear.
     */
    public void crearTorneoXJugador(TorneoXJugador txj) {
        controlPersis.crearTorneoXJugador(txj);
    }

    /**
     * Elimina una relación entre un torneo y un jugador según su ID.
     * @param id Identificador de la relación a eliminar.
     */
    public void eliminarTorneoXJugador(int id) {
        controlPersis.eliminarTorneoXJugador(id);
    }

    /**
     * Edita una relación entre un torneo y un jugador existente.
     * @param txj Objeto TorneoXJugador con la información actualizada.
     */
    public void editarTorneoXJugador(TorneoXJugador txj) {
        controlPersis.editarTorneoXJugador(txj);
    }

    /**
     * Obtiene una relación entre un torneo y un jugador por su ID.
     * @param id Identificador de la relación.
     * @return Objeto TorneoXJugador correspondiente al ID.
     */
    public TorneoXJugador leerTorneoXJugador(int id) {
        return controlPersis.leerTorneoXJugador(id);
    }

    /**
     * Obtiene la lista de todas las relaciones entre torneos y jugadores.
     * @return Lista de TorneoXJugador almacenados en la base de datos.
     */
    public ArrayList<TorneoXJugador> leerTodosTorneoXJugador() {
        return controlPersis.leerTodosTorneoXJugador();
    }
    
    // METODOS CRUD DE ARBITROS
    /**
     * Crea un nuevo árbitro en la base de datos.
     * @param arbitro Objeto Arbitro a crear.
     */
    public void crearArbitro(Arbitro arbitro) {
        controlPersis.crearArbitro(arbitro);
    }

    /**
     * Elimina un árbitro por su ID.
     * @param id Identificador del árbitro a eliminar.
     */
    public void eliminarArbitro(int id) {
        controlPersis.eliminarArbitro(id);
    }

    /**
     * Edita los datos de un árbitro existente.
     * @param arbitro Objeto Arbitro con la información actualizada.
     */
    public void editarArbitro(Arbitro arbitro) {
        controlPersis.editarArbitro(arbitro);
    }

    /**
     * Obtiene un árbitro por su ID.
     * @param id Identificador del árbitro.
     * @return Objeto Arbitro correspondiente al ID.
     */
    public Arbitro leerArbitro(int id) {
        return controlPersis.leerArbitro(id);
    }

    /**
     * Obtiene la lista de todos los árbitros.
     * @return Lista de árbitros almacenados en la base de datos.
     */
    public ArrayList<Arbitro> leerTodosArbitros() {
        return controlPersis.leerTodosArbitros();
    }
}
