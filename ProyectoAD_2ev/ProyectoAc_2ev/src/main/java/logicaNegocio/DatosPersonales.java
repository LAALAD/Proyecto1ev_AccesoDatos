
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

     public DatosPersonales( String apellido, Date fechaNacimiento, String email, String telefono) {
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
    }
    
    public DatosPersonales(){
        
    }

    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    
}
