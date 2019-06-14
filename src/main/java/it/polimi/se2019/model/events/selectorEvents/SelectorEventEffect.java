package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.EffectV;

import java.util.ArrayList;
import java.util.List;

public class SelectorEventEffect extends SelectorEvent {
    private ArrayList<EffectV> possibleEffects;
    public SelectorEventEffect(SelectorEventTypes selectorEventType, ArrayList<EffectV> possibleEffects){
        super(selectorEventType);
        this.possibleEffects = possibleEffects;
    }

    public ArrayList<EffectV> getPossibleEffects() {
        return possibleEffects;
    }
}
