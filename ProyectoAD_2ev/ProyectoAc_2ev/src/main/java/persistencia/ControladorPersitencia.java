package persistencia;
//Se rencarga de recibir las peticiones desde la capa lógica y delega en la capa de persistencia correspondiente (JPA Controler)

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logicaNegocio.DatosPersonales;
import logicaNegocio.Jugador;
import logicaNegocio.Torneo;
import logicaNegocio.TorneoXJugador;
import persistencia.exceptions.NonexistentEntityException;

public class ControladorPersitencia {

    JugadorJpaController jugJpa = new JugadorJpaController();
    TorneoJpaController torJpa = new TorneoJpaController();
    TorneoXJugadorJpaController txjJpa = new TorneoXJugadorJpaController();
    DatosPersonalesJpaController dpJpa = new DatosPersonalesJpaController();

    //Jugador
    public void crearJugador(Jugador jug) {
        jugJpa.create(jug);
    }

    public void eliminarJugador(int id) {
        try {
            jugJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el Jugador: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarJugador(Jugador jug) {
        try {
            jugJpa.edit(jug);
        } catch (Exception ex) {
            System.out.println("Error al editar el Jugador: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Jugador leerJugador(int id) {
        return jugJpa.findJugador(id);
    }

    public ArrayList<Jugador> leerTodosJugadores() {
        //El casting explícito no funciona, hay que usar el constructor de ArrayList.
        //return (ArrayList<Jugador>) jugJpa.findJugadorEntities();

        List<Jugador> aux = jugJpa.findJugadorEntities();
        ArrayList<Jugador> sol = new ArrayList<Jugador>(aux);
        return sol;
    }

    //Torneo
    public void crearTorneo(Torneo tor) {
        torJpa.create(tor);
    }

    public void eliminarTorneo(int id) {
        try {
            torJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el Torneo: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarTorneo(Torneo tor) {
        try {
            torJpa.edit(tor);
        } catch (Exception ex) {
            System.out.println("Error al editar el Torneo: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Torneo leerTorneo(int id) {
        return torJpa.findTorneo(id);
    }

    public ArrayList<Torneo> leerTodosTorneos() {
        List<Torneo> aux = torJpa.findTorneoEntities();
        ArrayList<Torneo> sol = new ArrayList<Torneo>(aux);
        return sol;
    }

    //TorneoXJugador
    public void crearTorneoXJugador(TorneoXJugador txj) {
        txjJpa.create(txj);
    }

    public void eliminarTorneoXJugador(int id) {
        try {
            txjJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el TorneoXJugador: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarTorneoXJugador(TorneoXJugador txj) {
        try {
            txjJpa.edit(txj);
        } catch (Exception ex) {
            System.out.println("Error al editar la TorneoXJugador: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TorneoXJugador leerTorneoXJugador(int id) {
        return txjJpa.findTorneoXJugador(id);
    }

    public ArrayList<TorneoXJugador> leerTodosTorneoXJugador() {
        List<TorneoXJugador> aux = txjJpa.findTorneoXJugadorEntities();
        ArrayList<TorneoXJugador> sol = new ArrayList<TorneoXJugador>(aux);
        return sol;
    }
    
     

    //DATOS PERSONALES
    public void crearDatosPersonales(DatosPersonales dp) {
        dpJpa.create(dp);
    }

    public void eliminarDatosPersonales(int id) {
        try {
            dpJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al borrar el DatosPersonales: el id introducido no existe.");
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarDatosPersonales(DatosPersonales dp) {
        try {
            dpJpa.edit(dp);
        } catch (Exception ex) {
            System.out.println("Error al editar el DatosPersonales: " + ex.toString());
            Logger.getLogger(ControladorPersitencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DatosPersonales leerDatosPersonales(int id) {
        return dpJpa.findDatosPersonales(id);
    }

    public ArrayList<DatosPersonales> leerTodosDatosPersonales() {
        List<DatosPersonales> aux = dpJpa.findDatosPersonalesEntities();
        ArrayList<DatosPersonales> sol = new ArrayList<DatosPersonales>(aux);
        return sol;
    }
}
