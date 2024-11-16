/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;

import Modelo.Item;
import Modelo.ItemDado;
import Modelo.ItemMoneda;

/**
 *
 * @author adria
 */
public class FactoriaItems {
    //public enum Item {MONEDA, DADO};
    public static Item getItem(String tipo){
        if(tipo.equals("moneda")){
            return new ItemMoneda();
        }else{
            return new ItemDado();
        }
    }
}
