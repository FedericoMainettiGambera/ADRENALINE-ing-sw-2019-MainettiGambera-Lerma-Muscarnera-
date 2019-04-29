package it.polimi.se2018.model;

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

    /**for stringify method*/
    final static String tag = "<DamageTracker>\n";

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
    public void addDamages(Player shootingPlayer, int numberOfDamages) {
        for(int i = 0; i<numberOfDamages; i++) {
            damageSlotsList.add(new DamageSlot(shootingPlayer));
        }
    }

    /**add Damages
     * @param damageSlot
     * */
    public void addDamages(DamageSlot damageSlot) {
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

    /***/
    public String stringify(){

        String information = DamagesTracker.tag;
        for (int i = 0; i < damageSlotsList.size(); i++) {
            DamageSlot slot = getDamageSlot(i);
            information += slot.stringify();
        }
        information += DamagesTracker.tag;
        return information;
    }

    /***/
    public static DamagesTracker parse(String informations) throws Exception {
        String str;
        String[] damageSlots;

        if( informations.startsWith(DamagesTracker.tag) && informations.endsWith(DamagesTracker.tag) ) {
            str = informations.replace(DamagesTracker.tag, "");

            DamagesTracker tracker = new DamagesTracker();

            damageSlots = str.split(DamageSlot.tag);
            for (int i = 0; i < damageSlots.length; i++) {
                tracker.addDamages(DamageSlot.parse(damageSlots[i]));
            }
            return tracker;
        }
        else {
            throw new Exception("tryed to parse a wrong string as a DamageTracker: " + informations);
        }
    }
}