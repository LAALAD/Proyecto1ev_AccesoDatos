package logicaNegocio;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logicaNegocio.Jugador;
import logicaNegocio.Torneo;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-23T18:43:09", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(TorneoXJugador.class)
public class TorneoXJugador_ { 

    public static volatile SingularAttribute<TorneoXJugador, Integer> posicion;
    public static volatile SingularAttribute<TorneoXJugador, Torneo> torneo;
    public static volatile SingularAttribute<TorneoXJugador, Jugador> jugador;
    public static volatile SingularAttribute<TorneoXJugador, Integer> id;

}