package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.Observable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The MarksSlots class keeps track of the number of marks a player has received from another player.
 * @author FedericoMainettiGambera
 * */
public class MarkSlots implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets player and quantity
     * @param quantity
     * @param player
     * */
    public MarkSlots(Player player, int quantity){
        this.quantity=quantity;
        this.markingPlayer = player;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**quantity of marks currently received*/
    private int quantity;

    /**marking player*/
    private Player markingPlayer;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**@return
     * */
    public int getQuantity() {
        return quantity;
    }

    /**@return
     * */
    public Player getMarkingPlayer() {
        return markingPlayer;
    }

    /**add a specified amount of marks, but makes sure that it never exceed GameConstant.MaxNumberOfMarkFromPlayer*/
    public void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MaxNumberOfMarkFromPlayer) {
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfMarkFromPlayer;
        }
    }
}