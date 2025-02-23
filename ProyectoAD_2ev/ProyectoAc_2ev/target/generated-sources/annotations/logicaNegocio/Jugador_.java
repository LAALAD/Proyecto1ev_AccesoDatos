package logicaNegocio;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logicaNegocio.DatosPersonales;
import logicaNegocio.TorneoXJugador;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-23T18:43:09", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Jugador.class)
public class Jugador_ { 

    public static volatile ListAttribute<Jugador, TorneoXJugador> torneos;
    public static volatile SingularAttribute<Jugador, DatosPersonales> datosPersonales;
    public static volatile SingularAttribute<Jugador, Integer> id_j;
    public static volatile SingularAttribute<Jugador, Double> partidasGanadas;
    public static volatile SingularAttribute<Jugador, String> nombre;
    public static volatile SingularAttribute<Jugador, Double> partidasJugadas;

}