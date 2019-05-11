package it.polimi.se2019.model.events;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;

public class SelectorEvent extends Event {
    private SelectorEventTypes selectorEventTypes;

    public SelectorEvent(SelectorEventTypes selectorEventType){
        this.selectorEventTypes = selectorEventType;
    }

    public SelectorEventTypes getSelectorEventTypes(){
        return this.selectorEventTypes;
    }
}
