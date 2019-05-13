package it.polimi.se2019.model.events;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.io.Serializable;

public class SelectorEvent extends Event implements Serializable {
    private SelectorEventTypes selectorEventTypes;

    public SelectorEvent(SelectorEventTypes selectorEventType){
        this.selectorEventTypes = selectorEventType;
        this.setEventType(EventTypes.SelectorEvent);
    }

    public SelectorEventTypes getSelectorEventTypes(){
        return this.selectorEventTypes;
    }
}
