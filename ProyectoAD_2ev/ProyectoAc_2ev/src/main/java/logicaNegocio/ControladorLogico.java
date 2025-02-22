package logicaNegocio;

import java.util.ArrayList;
import persistencia.ControladorPersitencia;

public class ControladorLogico {

    ControladorPersitencia controlPersis = new ControladorPersitencia();

    //METODOS CRUD JUGADOR
    public void crearJugador(Jugador jug) {
        controlPersis.crearJugador(jug);
    }

    public void eliminarJugador(int id) {
        controlPersis.eliminarJugador(id);
    }

    public void editarJugador(Jugador jug) {
        controlPersis.editarJugador(jug);
        //EclipseLink hace un merge, si el Jugador no existe lo crea
        //si existe lo modificia
        //Entiende Jugador por ID
    }

    public Jugador leerJugador(int id) {
        return controlPersis.leerJugador(id);
    }

    public ArrayList<Jugador> leerTodosJugadores() {
        return controlPersis.leerTodosJugadores();
    }

    //METODOS CRUD Torneo
    public void crearTorneo(Torneo tor) {
        controlPersis.crearTorneo(tor);
    }

    public void eliminarTorneo(int id) {
        controlPersis.eliminarTorneo(id);
    }

    public void editarTorneo(Torneo tor) {
        controlPersis.editarTorneo(tor);
        //EclipseLink hace un merge, si el torneo no existe lo crea
        //si existe lo modificia
        //Entiende torneo por ID
    }

    public Torneo leerTorneo(int id) {
        return controlPersis.leerTorneo(id);
    }

    public ArrayList<Torneo> leerTodosTorneos() {
        return controlPersis.leerTodosTorneos();
    }

    //METODOS CRUD TorneoXJugador
    public void crearTorneoXJugador(TorneoXJugador txj) {
        controlPersis.crearTorneoXJugador(txj);
    }

    public void eliminarTorneoXJugador(int id) {
        controlPersis.eliminarTorneoXJugador(id);
    }

    public void editarTorneoXJugador(TorneoXJugador txj) {
        controlPersis.editarTorneoXJugador(txj);
        //EclipseLink hace un merge, si el TorneoXJugador no existe lo crea
        //si existe lo modificia
        //Entiende TorneoXJugador por ID
    }

    public TorneoXJugador leerTorneoXJugador(int id) {
        return controlPersis.leerTorneoXJugador(id);
    }

    public ArrayList<TorneoXJugador> leerTodosTorneoXJugador() {
        return controlPersis.leerTodosTorneoXJugador();
    }
}
