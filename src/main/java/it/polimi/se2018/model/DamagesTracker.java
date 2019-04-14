package it.polimi.se2018.model;

import java.util.List;

/***/
public class DamagesTracker {

    /***/
    public DamagesTracker() {
        this.damageSlotsList = null;
    }

    /***/
    private List<DamageSlot> damageSlotsList;

    /***/
    public void addDamages(Player shootingPlayer, int numberOfDamages) {
        for(int i = 0; i<numberOfDamages; i++) {
            if(damageSlotsList.size() < 12) {
                damageSlotsList.add(new DamageSlot(shootingPlayer));
            }
            else{
                System.out.println("Can't take more than twelve damages, sorry!");
            }
        }
    }

    /***/
    public DamageSlot getDamageSlot(int slotNumber) {
        return damageSlotsList.get(slotNumber);
    }

    /***/
    public void emptyList() {
        this.damageSlotsList = null;
    }
    
}