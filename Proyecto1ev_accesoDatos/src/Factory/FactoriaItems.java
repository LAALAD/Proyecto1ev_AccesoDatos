/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Modelo.Item;
import Modelo.ItemDado;
import Modelo.ItemMoneda;

/**
 * La clase <code>FactoriaItems</code> es una implementación del patrón de diseño *Factory Method*.
 * Este patrón permite crear objetos de diferentes tipos de la clase <code>Item</code> (en este caso, 
 * <code>ItemMoneda</code> y <code>ItemDado</code>) basándose en un parámetro de entrada.
 * 
 * El método <code>getItem</code> toma una cadena que indica el tipo de objeto a crear, 
 * y devuelve una instancia correspondiente de <code>ItemMoneda</code> o <code>ItemDado</code>.
 * 
 * <p>Este enfoque es útil cuando se desea abstraer la creación de objetos específicos 
 * sin necesidad de que el código cliente conozca la clase concreta que se está instanciando.</p>
 * 
 * @author adria
 */
public class FactoriaItems {
    
    /**
     * Enumerado que define los tipos posibles de objetos que se pueden crear: 
     * MONEDA y DADO.
     */
    public enum ItemTipo {MONEDA, DADO};
    
    /**
     * Método estático que devuelve una instancia de un objeto de tipo <code>Item</code>.
     * Basado en el parámetro de tipo <code>ItemTipo</code>, crea una instancia de <code>ItemMoneda</code> 
     * si el tipo es <code>ItemTipo.MONEDA</code>, o una instancia de <code>ItemDado</code> si el tipo 
     * es <code>ItemTipo.DADO</code>.
     *
     * @param tipo El tipo de objeto a crear, de tipo <code>ItemTipo</code>, que puede ser MONEDA o DADO.
     * @return Un objeto de tipo <code>ItemMoneda</code> si el tipo es <code>ItemTipo.MONEDA</code>, o un 
     *         objeto de tipo <code>ItemDado</code> si el tipo es <code>ItemTipo.DADO</code>.
     */
    public static Item getItem(ItemTipo tipo){
        if(tipo.equals(ItemTipo.MONEDA)){
            return new ItemMoneda();
        }else{
            return new ItemDado();
        }
    }
    
    /**
     * Método estático que devuelve una instancia de un objeto de tipo <code>Item</code>.
     * Basado en el parámetro de entrada, crea una instancia de <code>ItemMoneda</code> si el parámetro
     * es "moneda", o una instancia de <code>ItemDado</code> si el parámetro es cualquier otro valor.
     *
     * @param tipo El tipo de objeto a crear, que puede ser "moneda" o "dado". El valor determina el tipo
     *             de objeto que se instanciará.
     * @return Un objeto de tipo <code>ItemMoneda</code> si el tipo es "moneda", o un objeto de tipo 
     *         <code>ItemDado</code> en caso contrario.
     * 
     */
    /*public static Item getItem(String tipo){
        if(tipo.equals("moneda")){
            return new ItemMoneda();
        }else{
            return new ItemDado();
        }
    }*/
}
