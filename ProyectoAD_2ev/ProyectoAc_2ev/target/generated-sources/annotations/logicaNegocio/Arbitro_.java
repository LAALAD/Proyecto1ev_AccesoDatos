package logicaNegocio;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logicaNegocio.Torneo;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-23T18:43:09", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Arbitro.class)
public class Arbitro_ { 

    public static volatile ListAttribute<Arbitro, Torneo> torneos;
    public static volatile SingularAttribute<Arbitro, Integer> id;
    public static volatile SingularAttribute<Arbitro, String> nombre;
    public static volatile SingularAttribute<Arbitro, Integer> numeroLicencia;

}