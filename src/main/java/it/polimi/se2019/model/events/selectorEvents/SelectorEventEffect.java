package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.Event;

import java.util.ArrayList;
import java.util.List;

public class SelectorEventEffect extends SelectorEvent {
    private ArrayList<Effect> possibleEffects;
    public SelectorEventEffect(SelectorEventTypes selectorEventType, ArrayList<Effect> possibleEffects){
        super(selectorEventType);
        this.possibleEffects = possibleEffects;
    }

    public ArrayList<Effect> getPossibleEffects() {
        return possibleEffects;
    }
}
