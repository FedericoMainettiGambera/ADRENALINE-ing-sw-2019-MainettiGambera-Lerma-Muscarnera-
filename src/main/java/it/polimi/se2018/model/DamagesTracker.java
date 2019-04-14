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
            damageSlotsList.add(new DamageSlot(shootingPlayer));
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