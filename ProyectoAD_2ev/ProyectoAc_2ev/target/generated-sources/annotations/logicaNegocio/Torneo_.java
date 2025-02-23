package logicaNegocio;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logicaNegocio.Arbitro;
import logicaNegocio.TorneoXJugador;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-23T18:43:09", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Torneo.class)
public class Torneo_ { 

    public static volatile SingularAttribute<Torneo, Integer> num_max_jugadores;
    public static volatile SingularAttribute<Torneo, Integer> id_t;
    public static volatile SingularAttribute<Torneo, Date> fecha;
    public static volatile SingularAttribute<Torneo, Boolean> inscripciones_abiertas;
    public static volatile ListAttribute<Torneo, TorneoXJugador> inscritos;
    public static volatile ListAttribute<Torneo, Arbitro> arbitros;
    public static volatile SingularAttribute<Torneo, String> nombre;

}