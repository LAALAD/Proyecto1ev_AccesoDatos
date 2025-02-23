package logicaNegocio;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-23T18:43:09", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(DatosPersonales.class)
public class DatosPersonales_ { 

    public static volatile SingularAttribute<DatosPersonales, Date> fechaNacimiento;
    public static volatile SingularAttribute<DatosPersonales, String> apellido;
    public static volatile SingularAttribute<DatosPersonales, Integer> id;
    public static volatile SingularAttribute<DatosPersonales, String> telefono;
    public static volatile SingularAttribute<DatosPersonales, String> email;

}