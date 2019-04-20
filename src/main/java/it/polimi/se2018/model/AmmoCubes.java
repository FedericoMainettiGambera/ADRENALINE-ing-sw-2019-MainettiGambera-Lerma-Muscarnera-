package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.*;
import java.io.*;

/**The AmmoCubes class keeps track of the number of cubes of a specific color a player has.
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
public class AmmoCubes {

    /***/
    public AmmoCubes(AmmoCubesColor color){
        quantity=0;
        this.color=color;
    }

    /***/
    private int quantity;

    /***/
    private AmmoCubesColor color;

    /***/
    public int getQuantity() {
        return this.quantity;
    }

    /***/
    public AmmoCubesColor getColor(){
        return this.color;
    }

    /***/
    public void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MaxNumberOfAmmoCubes){
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfAmmoCubes;
        }
    }

    /***/
    public boolean subQuantity(int quantity){
        if(this.quantity-quantity >= 0){
            this.quantity -= quantity;
            return true;
        }
        else{
            return false;
        }
    }
}