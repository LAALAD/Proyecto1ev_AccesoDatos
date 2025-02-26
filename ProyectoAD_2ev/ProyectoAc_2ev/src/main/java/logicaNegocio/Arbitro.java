/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;
import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
/**
 * Clase que representa un árbitro en el sistema.
 * Un árbitro puede estar asociado a múltiples torneos.
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
    private String numeroLicencia;

    //@ManyToMany(mappedBy = "arbitros", cascade = CascadeType.ALL) // cascade-Se propaga la eliminación, La propiedad 'arbitros' está en Torneo, así que 'mappedBy' la enlaza
    @ManyToMany(mappedBy = "arbitros", cascade = CascadeType.ALL)
    private ArrayList<Torneo> torneos;

    /**
     * Constructor por defecto.
     */
    public Arbitro() {
    }

    /**
     * Constructor que inicializa un árbitro con nombre y número de licencia.
     * 
     * @param nombre Nombre del árbitro.
     * @param numeroLicencia Número de licencia del árbitro.
     */
    public Arbitro(String nombre, String numeroLicencia) {
        this.nombre = nombre;
        this.numeroLicencia = numeroLicencia;
    }

    // Getters y Setters
    /**
     * Obtiene el ID del árbitro.
     * 
     * @return ID del árbitro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del árbitro.
     * 
     * @param id ID a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del árbitro.
     * 
     * @return Nombre del árbitro.
     */
    public String getNombre() {
        return nombre;
    }

     /**
     * Establece el nombre del árbitro.
     * 
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     /**
     * Obtiene el número de licencia del árbitro.
     * 
     * @return Número de licencia del árbitro.
     */
    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    /**
     * Establece el número de licencia del árbitro.
     * 
     * @param numeroLicencia Número de licencia a asignar.
     */
    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    /**
     * Obtiene la lista de torneos en los que el árbitro ha participado.
     * 
     * @return Lista de torneos.
     */
    public ArrayList<Torneo> getTorneos() {
        return torneos;
    }

    /**
     * Establece la lista de torneos en los que el árbitro ha participado.
     * 
     * @param torneos Lista de torneos a asignar.
     */
    public void setTorneos(ArrayList<Torneo> torneos) {
        this.torneos = torneos;
    }
    
    
}
