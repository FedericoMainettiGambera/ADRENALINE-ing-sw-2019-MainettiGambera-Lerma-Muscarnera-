package it.polimi.se2019.model;


import it.polimi.se2019.view.components.DamageSlotV;
import it.polimi.se2019.view.components.DamageTrackerV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The DamagesTracker class keeps track of the damages taken from a player.
 * @author FedericoMainettiGambera
 * */
public class DamagesTracker implements Serializable {

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
     * @param shootingPlayer the player who is shooting
     * @param numberOfDamages the number of damage he's giving
     * */
    public void addDamages(Player shootingPlayer, int numberOfDamages){
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

    public boolean isEmpty(){
        return this.damageSlotsList.isEmpty();
    }

    /**add Damages
     * @param damageSlot
     * */
    public void addDamage(DamageSlot damageSlot){
        if(damageSlot == null){
            throw new IllegalArgumentException("the DamageSlot can't be null.");
        }
        damageSlotsList.add(damageSlot);
    }

    public DamageSlot getLastDamageSlot() {
        return damageSlotsList.get(damageSlotsList.size()-1);
    }

    public DamageSlot getDamageSlot(int slotNumber){
        if(damageSlotsList.size()>= slotNumber) {
            return damageSlotsList.get(slotNumber);
        }
        throw new IndexOutOfBoundsException("requested a slot number higher than the number of DamageSlots existing: " +slotNumber);
    }

    /**empty the damageSlotsList
     * (used if a player dies)
     * */
    public void emptyList() {
        this.damageSlotsList = new ArrayList<>();
    }

    public DamageTrackerV buildDamageTrackerV(){
        DamageTrackerV damageTrackerV = new DamageTrackerV();
        List<DamageSlotV> listOfDamageSlotV = new ArrayList<>();
        DamageSlotV tempDamageSlotV;
        for (DamageSlot d:this.damageSlotsList) {
            tempDamageSlotV = new DamageSlotV();
            tempDamageSlotV.setShootingPlayerNickname(d.getShootingPlayer().getNickname());
            tempDamageSlotV.setShootingPlayerColor(d.getShootingPlayer().getColor());
            listOfDamageSlotV.add(tempDamageSlotV);
        }
        damageTrackerV.setDamageSlotsList(listOfDamageSlotV);

        return damageTrackerV;
    }
}