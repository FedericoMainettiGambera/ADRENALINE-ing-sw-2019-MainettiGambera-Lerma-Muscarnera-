package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The AmmoCubes class keeps track of the number of cubes of a specific color a player has.
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class AmmoCubes implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * Sets quantity to 0. Sets color.
     * @param color to set color attribute
     * */
    public AmmoCubes(AmmoCubesColor color){
        quantity=0;
        this.color=color;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**quantity of ammo cubes of this.color*/
    private int quantity;

    /**color of the ammo*/
    private AmmoCubesColor color;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**@return quantity
     * */
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**@return color
     * */
    public AmmoCubesColor getColor(){
        return this.color;
    }

    /**add the specified amount of ammos, if the total quantity exceed the "GameConstant.MAX_NUMBER_OF_AMMO_CUBES"
     * this method will cut it to the max amount possible.
     * @param quantity the quantity of ammos to add
     * */
     void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MAX_NUMBER_OF_AMMO_CUBES){
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MAX_NUMBER_OF_AMMO_CUBES;
        }
    }

    /**Subtract a quantity of ammos. This method is used to pay ammos.
     * @param quantity the quantity to be subtracted
     * @return Before making the payment the method checks if the resulting quantity is positive,
     *         if it isn't it returns false;
     * */
     boolean subQuantity(int quantity){
        if(canSubQuantity(quantity)){
            this.quantity -= quantity;
            return true;
        }
        else{
            return false;
        }
    }

    /**checks if a specified amount of ammos can be subbed
     * @param quantity the quantity to be subtracted
     * @return boolean value*/
     boolean canSubQuantity(int quantity){
        return this.quantity - quantity >= 0;
    }

}