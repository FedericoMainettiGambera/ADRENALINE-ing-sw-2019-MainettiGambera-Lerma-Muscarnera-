package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class DamageTrackerV implements Serializable {
    private List<DamageSlotV> damageSlotsList;

    public List<DamageSlotV> getDamageSlotsList() {
        return damageSlotsList;
    }

    public void setDamageSlotsList(List<DamageSlotV> damageSlotsList) {
        this.damageSlotsList = damageSlotsList;
    }
}
