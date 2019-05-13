package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;
import java.util.Observable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The AmmoCubes class keeps track of the number of cubes of a specific color a player has.
 * @author FedericoMainettiGambera
 * */
public class AmmoCubes extends Observable implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * Sets quantity to 0. Sets color.
     * @param color
     * */
    public AmmoCubes(AmmoCubesColor color){
        quantity=0;
        this.color=color;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**quantity of ammo cubes of this.color*/
    private int quantity;

    /**color*/
    private AmmoCubesColor color;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**@return
     * */
    public int getQuantity() {
        return this.quantity;
    }

    /**@return
     * */
    public AmmoCubesColor getColor(){
        return this.color;
    }

    /**add the specified amount of ammos, if the total quantity exceed the "GameConstant.MaxNumberOfAmmoCubes"
     * this method will cut it to the max amount possible.
     * @param quantity
     * */
    public void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MaxNumberOfAmmoCubes){
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfAmmoCubes;
        }
        setChanged();
        notifyObservers();
    }

    /**Subtract a quantity of ammos. This method is used to pay ammos.
     * @param quantity
     * @return Before making the payment the method checks if the resulting quantity is positive,
     *         if it isn't it returns false;
     * */
    public boolean subQuantity(int quantity){
        if(canSubQuantity(quantity)){
            this.quantity -= quantity;
            setChanged();
            notifyObservers();
            return true;
        }
        else{
            return false;
        }
    }

    /**checks if a specified amount of ammos can be subbed*/
    public boolean canSubQuantity(int quantity){
        if(this.quantity-quantity >= 0){
            return true;
        }
        else{
            return false;
        }
    }

}