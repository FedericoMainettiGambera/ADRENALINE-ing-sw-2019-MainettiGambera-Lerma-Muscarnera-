package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.EffectInfoType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * An EffectInfoElement is an object that describes the routh that a certain input has to take
 * to fill the data of the actions that are linked with that input.
 *
 *  It contains a reference to the input ( getEffectInfoTypelist())
 *  and a list of integer that represents the list of Actions that are linked with that input.
 *
 * @author LucaMuscarnera
 * */
public class EffectInfoElement implements Serializable {
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
