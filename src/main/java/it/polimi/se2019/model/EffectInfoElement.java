package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.util.ArrayList;
import java.util.List;

public class EffectInfoElement {
    public EffectInfoType getEffectInfoTypelist() {
        return effectInfoType;
    }

    public void setEffectInfoTypelist(EffectInfoType effectInfoTypelist) {
        this.effectInfoType = effectInfoTypelist;
    }

    public List<Integer> getEffectInfoTypeDestination() {
        return effectInfoTypeDestination;
    }

    public void setEffectInfoTypeDestination(List<Integer> effectInfoTypeDestination) {
        this.effectInfoTypeDestination = effectInfoTypeDestination;
    }

    private List<Integer> effectInfoTypeDestination;
    private EffectInfoType effectInfoType;

    public EffectInfoElement() {
        effectInfoTypeDestination  = new ArrayList<Integer>();

    }

}
