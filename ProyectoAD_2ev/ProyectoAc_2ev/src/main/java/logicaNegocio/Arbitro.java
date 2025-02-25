/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;
import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
/**
 *
 * @author paulc
 */
@Entity
public class Arbitro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;
    @Basic
    private int numeroLicencia;

    //@ManyToMany(mappedBy = "arbitros", cascade = CascadeType.ALL) // cascade-Se propaga la eliminación, La propiedad 'arbitros' está en Torneo, así que 'mappedBy' la enlaza
    @ManyToMany(mappedBy = "arbitros", cascade = CascadeType.ALL)
    private ArrayList<Torneo> torneos;

    public Arbitro() {
    }

    public Arbitro(String nombre, int numeroLicencia) {
        this.nombre = nombre;
        this.numeroLicencia = numeroLicencia;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(int numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public ArrayList<Torneo> getTorneos() {
        return torneos;
    }

    public void setTorneos(ArrayList<Torneo> torneos) {
        this.torneos = torneos;
    }
    
    
}
