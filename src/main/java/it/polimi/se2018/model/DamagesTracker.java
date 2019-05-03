package it.polimi.se2018.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The DamagesTracker class keeps track of the damages taken from a player.
 * @author FedericoMainettiGambera
 * */
public class DamagesTracker {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * initialize the damageSlotsList with a new Arraylist of DamageSlot
     * */
    public DamagesTracker() {
        this.damageSlotsList = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**damage slots list*/
    private List<DamageSlot> damageSlotsList;

    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public List<DamageSlot> getDamageSlotsList() {
        return damageSlotsList;
    }

    /**add Damages
     * @param shootingPlayer
     * @param numberOfDamages
     * */
    public void addDamages(Player shootingPlayer, int numberOfDamages) throws IllegalArgumentException {
        if(shootingPlayer == null){
            throw new IllegalArgumentException("The shooting player can't be null.");
        }
        if(numberOfDamages<1){
            throw new IllegalArgumentException("the number of Damages can't be < 1 .");
        }
        for(int i = 0; i < numberOfDamages; i++) {
            damageSlotsList.add(new DamageSlot(shootingPlayer));
        }
    }

    /**add Damages
     * @param damageSlot
     * */
    public void addDamage(DamageSlot damageSlot)throws IllegalArgumentException {
        if(damageSlot == null){
            throw new IllegalArgumentException("the DamageSlot can't be null.");
        }
        damageSlotsList.add(damageSlot);
    }

    /**@param slotNumber
     * @return the slotNumber DamageSlot
     * */
    public DamageSlot getDamageSlot(int slotNumber) {
        if(damageSlotsList.size()>= slotNumber) {
            return damageSlotsList.get(slotNumber);
        }
        return null;
    }

    /**empty the damageSlotsList
     * (used if a player dies)
     * */
    public void emptyList() {
        this.damageSlotsList = new ArrayList<>();
    }
}