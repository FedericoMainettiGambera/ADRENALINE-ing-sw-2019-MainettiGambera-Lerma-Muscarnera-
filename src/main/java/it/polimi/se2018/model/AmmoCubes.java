package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.*;
import java.io.*;

/**The AmmoCubes class keeps track of the number of cubes of a specific color a player has.
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Person" CLASS.
 * */
public class AmmoCubes {

    /**Constructor*/
    public AmmoCubes(AmmoCubesColor color){
        quantity=0;
        this.color=color;
    }


    /*ATTRIBUTES*/

    /***/
    private int quantity;

    /***/
    private AmmoCubesColor color;


    /*METHODS*/

    /***/
    public int getQuantity() {
        return this.quantity;
    }

    /***/
    public AmmoCubesColor getColor(){
        return this.color;
    }

    /**add the specified amount of ammos, if the total quantity exceed the "GameConstant.MaxNumberOfAmmoCubes"
     * this method will cut it to the max amount possible.*/
    public void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MaxNumberOfAmmoCubes){
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfAmmoCubes;
        }
    }

    /**Subtract a quantity of ammos. This method is used to pay ammos. If the resulting quantity is negative
     * the class return false.*/
    public boolean subQuantity(int quantity){
        if(this.quantity-quantity >= 0){
            this.quantity -= quantity;
            return true;
        }
        else{
            return false;
        }
    }
    /***/
    public boolean canSubQuantity(int quantity){
        if(this.quantity-quantity >= 0){
            return true;
        }
        else{
            return false;
        }
    }
}