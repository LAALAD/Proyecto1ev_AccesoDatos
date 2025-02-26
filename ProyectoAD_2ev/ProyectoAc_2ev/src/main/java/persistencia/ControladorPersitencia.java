package persistencia;
//Se rencarga de recibir las peticiones desde la capa lógica y delega en la capa de persistencia correspondiente (JPA Controler)

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logicaNegocio.Arbitro;
import logicaNegocio.DatosPersonales;
import logicaNegocio.Jugador;
import logicaNegocio.Torneo;
import logicaNegocio.TorneoXJugador;
import persistencia.exceptions.NonexistentEntityException;

/**
 * Clase que actúa como intermediaria entre la capa de lógica y la capa de
 * persistencia. Gestiona las operaciones CRUD sobre las entidades del sistema y
 * delega en los controladores JPA.
 *
 * Se encarga de la persistencia de jugadores, torneos, relaciones entre torneos
 * y jugadores, datos personales y árbitros.
 *
 * @author Jorge
 */
public class ControladorPersitencia {

    JugadorJpaController jugJpa = new JugadorJpaController();
    TorneoJpaController torJpa = new TorneoJpaController();
    TorneoXJugadorJpaController txjJpa = new TorneoXJugadorJpaController();
    DatosPersonalesJpaController dpJpa = new DatosPersonalesJpaController();
    ArbitroJpaController arbJpa = new ArbitroJpaController();

    //Jugador
    /**
     * Crea un nuevo jugador en la base de datos.
     *
     * @param jug Objeto {@link Jugador} a crear.
     */
    public void crearJugador(Jugador jug) {
        jugJpa.create(jug);
    }

    /**
     * Elimina un jugador de la base de datos por su ID.
     *
     * @param id Identificador del jugador a eliminar.
     */
    public void eliminarJugador(int id) {
        try {
            jugJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el Jugador: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Edita un jugador existente en la base de datos.
     *
     * @param jug Objeto {@link Jugador} con la información actualizada.
     */
    public void editarJugador(Jugador jug) {
        try {
            jugJpa.edit(jug);
        } catch (Exception ex) {
            System.out.println("Error al editar el Jugador: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca un jugador en la base de datos por su ID.
     *
     * @param id Identificador del jugador.
     * @return Objeto {@link Jugador} encontrado, o `null` si no existe.
     */
    public Jugador leerJugador(int id) {
        return jugJpa.findJugador(id);
    }

    /**
     * Obtiene todos los jugadores almacenados en la base de datos.
     *
     * @return Lista de todos los jugadores.
     */
    public ArrayList<Jugador> leerTodosJugadores() {
        //El casting explícito no funciona, hay que usar el constructor de ArrayList.
        //return (ArrayList<Jugador>) jugJpa.findJugadorEntities();

        List<Jugador> aux = jugJpa.findJugadorEntities();
        ArrayList<Jugador> sol = new ArrayList<Jugador>(aux);
        return sol;
    }

    //Torneo
    /**
     * Crea un nuevo torneo en la base de datos.
     *
     * @param tor Objeto {@link Torneo} a crear.
     */
    public void crearTorneo(Torneo tor) {
        torJpa.create(tor);
    }

    /**
     * Elimina un torneo de la base de datos por su ID.
     *
     * @param id Identificador del torneo a eliminar.
     */
    public void eliminarTorneo(int id) {
        try {
            torJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el Torneo: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Edita un torneo existente en la base de datos.
     *
     * @param tor Objeto {@link Torneo} con la información actualizada.
     */
    public void editarTorneo(Torneo tor) {
        try {
            torJpa.edit(tor);
        } catch (Exception ex) {
            System.out.println("Error al editar el Torneo: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca un torneo en la base de datos por su ID.
     *
     * @param id Identificador del torneo.
     * @return Objeto {@link Torneo} encontrado, o `null` si no existe.
     */
    public Torneo leerTorneo(int id) {
        return torJpa.findTorneo(id);
    }

    /**
     * Obtiene todos los torneos almacenados en la base de datos.
     *
     * @return Lista de todos los torneos.
     */
    public ArrayList<Torneo> leerTodosTorneos() {
        List<Torneo> aux = torJpa.findTorneoEntities();
        ArrayList<Torneo> sol = new ArrayList<Torneo>(aux);
        return sol;
    }

    //TorneoXJugador
    /**
     * Crea una nueva relación entre un torneo y un jugador en la base de datos.
     *
     * @param txj Objeto {@link TorneoXJugador} a crear.
     */
    public void crearTorneoXJugador(TorneoXJugador txj) {
        txjJpa.create(txj);
    }

    /**
     * Elimina una relación entre un torneo y un jugador de la base de datos por
     * su ID.
     *
     * @param id Identificador de la relación a eliminar.
     */
    public void eliminarTorneoXJugador(int id) {
        try {
            txjJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el TorneoXJugador: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Edita una relación existente entre un torneo y un jugador en la base de
     * datos.
     *
     * @param txj Objeto {@link TorneoXJugador} con la información actualizada.
     */
    public void editarTorneoXJugador(TorneoXJugador txj) {
        try {
            txjJpa.edit(txj);
        } catch (Exception ex) {
            System.out.println("Error al editar la TorneoXJugador: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca una relación entre un torneo y un jugador en la base de datos por
     * su ID.
     *
     * @param id Identificador de la relación TorneoXJugador.
     * @return Objeto {@link TorneoXJugador} correspondiente al ID
     * proporcionado, o `null` si no existe.
     */
    public TorneoXJugador leerTorneoXJugador(int id) {
        return txjJpa.findTorneoXJugador(id);
    }

    /**
     * Obtiene la lista de todas las relaciones entre torneos y jugadores
     * almacenadas en la base de datos.
     *
     * @return Lista de objetos {@link TorneoXJugador} representando todas las
     * inscripciones en torneos.
     */
    public ArrayList<TorneoXJugador> leerTodosTorneoXJugador() {
        List<TorneoXJugador> aux = txjJpa.findTorneoXJugadorEntities();
        ArrayList<TorneoXJugador> sol = new ArrayList<TorneoXJugador>(aux);
        return sol;
    }

    //DATOS PERSONALES
    /**
     * Crea un nuevo registro de datos personales en la base de datos.
     *
     * @param dp Objeto {@link DatosPersonales} a crear.
     */
    public void crearDatosPersonales(DatosPersonales dp) {
        dpJpa.create(dp);
    }

    /**
     * Elimina un registro de datos personales de la base de datos por su ID.
     *
     * @param id Identificador de los datos personales a eliminar.
     */
    public void eliminarDatosPersonales(int id) {
        try {
            dpJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el DatosPersonales: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Edita un registro existente de datos personales en la base de datos.
     *
     * @param dp Objeto {@link DatosPersonales} con la información actualizada.
     */
    public void editarDatosPersonales(DatosPersonales dp) {
        try {
            dpJpa.edit(dp);
        } catch (Exception ex) {
            System.out.println("Error al editar el DatosPersonales: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca un registro de datos personales en la base de datos por su ID.
     *
     * @param id Identificador de los datos personales.
     * @return Objeto {@link DatosPersonales} correspondiente al ID
     * proporcionado, o `null` si no existe.
     */
    public DatosPersonales leerDatosPersonales(int id) {
        return dpJpa.findDatosPersonales(id);
    }

    /**
     * Obtiene la lista de todos los registros de datos personales almacenados
     * en la base de datos.
     *
     * @return Lista de objetos {@link DatosPersonales} con la información
     * personal almacenada.
     */
    public ArrayList<DatosPersonales> leerTodosDatosPersonales() {
        List<DatosPersonales> aux = dpJpa.findDatosPersonalesEntities();
        ArrayList<DatosPersonales> sol = new ArrayList<DatosPersonales>(aux);
        return sol;
    }

    //ARBITROS
    /**
     * Crea un nuevo árbitro en la base de datos.
     *
     * @param arbitro Objeto {@link Arbitro} a crear.
     */
    public void crearArbitro(Arbitro arbitro) {
        arbJpa.create(arbitro);
    }

    /**
     * Elimina un árbitro de la base de datos por su ID.
     *
     * @param id Identificador del árbitro a eliminar.
     */
    public void eliminarArbitro(int id) {
        try {
            arbJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el Arbitro: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Edita un árbitro existente en la base de datos.
     *
     * @param arbitro Objeto {@link Arbitro} con la información actualizada.
     */
    public void editarArbitro(Arbitro arbitro) {
        try {
            arbJpa.edit(arbitro);
        } catch (Exception ex) {
            System.out.println("Error al editar el Arbitro: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca un árbitro en la base de datos por su ID.
     *
     * @param id Identificador del árbitro.
     * @return Objeto {@link Arbitro} correspondiente al ID proporcionado, o
     * `null` si no existe.
     */
    public Arbitro leerArbitro(int id) {
        return arbJpa.findArbitro(id);
    }

    /**
     * Obtiene la lista de todos los árbitros almacenados en la base de datos.
     *
     * @return Lista de objetos {@link Arbitro} registrados en la base de datos.
     */
    public ArrayList<Arbitro> leerTodosArbitros() {
        List<Arbitro> aux = arbJpa.findArbitrosEntities();
        ArrayList<Arbitro> sol = new ArrayList<Arbitro>(aux);
        return sol;
    }
}
