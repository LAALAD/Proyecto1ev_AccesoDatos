/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaNegocio;

/**
 * Clase abstracta que representa un ítem en el sistema.
 * 
 * Esta clase define una operación común para los diferentes tipos de ítems
 * que implementen la acción de "lanzar". La clase es abstracta, por lo que
 * no puede ser instanciada directamente y se espera que las clases hijas 
 * proporcionen la implementación de la operación `lanzar`.
 *
 * @author Usuario
 */
public abstract class Item {
    
    /**
     * Método abstracto que debe ser implementado por las clases que extiendan de {@link Item}.
     * Este método define la acción de "lanzar" un ítem. La implementación de este 
     * método depende del tipo de ítem que lo implemente.
     * 
     * @return Un valor entero que representa el resultado del lanzamiento. 
     *         El valor retornado dependerá de la implementación específica de cada ítem.
     */
    public abstract int lanzar();
}
