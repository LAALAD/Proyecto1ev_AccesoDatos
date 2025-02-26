/**
 * Clase DatosPersonales representa la información personal de un individuo.
 * Contiene atributos como apellido, fecha de nacimiento, email y teléfono.
 */
package logicaNegocio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
public class DatosPersonales implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    private String apellido;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic
    private String email;
    @Basic
    private String telefono;

    /**
     * Constructor de la clase DatosPersonales.
     * @param apellido Apellido del individuo.
     * @param fechaNacimiento Fecha de nacimiento del individuo.
     * @param email Correo electrónico del individuo.
     * @param telefono Número de teléfono del individuo.
     */
    public DatosPersonales(String apellido, Date fechaNacimiento, String email, String telefono) {
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }
    
    /**
     * Constructor vacío requerido por JPA.
     */
    public DatosPersonales() {
    }

    // Getters y Setters
    /**
     * Obtiene el ID del registro.
     * @return ID del registro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del registro.
     * @param id Identificador único.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el apellido.
     * @return Apellido del individuo.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido.
     * @param apellido Apellido del individuo.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene la fecha de nacimiento.
     * @return Fecha de nacimiento del individuo.
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento.
     * @param fechaNacimiento Fecha de nacimiento del individuo.
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el correo electrónico.
     * @return Correo electrónico del individuo.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico.
     * @param email Correo electrónico del individuo.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de teléfono.
     * @return Número de teléfono del individuo.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono.
     * @param telefono Número de teléfono del individuo.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
