package it.polimi.se2019.model;

import java.io.Serializable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" or "Player" CLASS.
 * The DamageSlot class keeps track of a single damage taken from a player.
 * It represents a blood drop in the Game.
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class DamageSlot implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets the shooting player.
     * @param shootingPlayer as already said
     * */
     DamageSlot(Player shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**shooting player*/
    private Player shootingPlayer;


    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**@return the shooting player
     * */
    public Player getShootingPlayer() {
        return shootingPlayer;
    }
}