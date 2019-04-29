package it.polimi.se2018.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The DamageSlot class keeps track of a single damage taken from a player.
 * It represents a blood drop in the Game.
 * @author FedericoMainettiGambera
 * */
public class DamageSlot {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets the shooting player.
     * @param shootingPlayer
     * */
    public DamageSlot(Player shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**shooting player*/
    private Player shootingPlayer;

    /**stringify tag*/
    final static String tag = "<DamageSlot>\n";

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**@return
     * */
    public Player getShootingPlayer() {
        return shootingPlayer;
    }


    /***/
    public String stringify(){

        return DamageSlot.tag +
               shootingPlayer.stringify() +
               DamageSlot.tag;
    }

    /***/
    public static DamageSlot parse(String informations) throws Exception {

        String str;

        if( informations.startsWith(DamageSlot.tag) && informations.endsWith(DamageSlot.tag) ) {
            str = informations.replace(DamageSlot.tag, "");
            return new DamageSlot(Player.parse(str));
        }
        else {
            throw new Exception("tryed to parse a wrong string as a DamageSlot: " + informations);
        }
    }
}