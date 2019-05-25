package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;

public class SelectorEventInt extends SelectorEvent {

    private int number;

    public SelectorEventInt(SelectorEventTypes selectorEventType, int number) {
        super(selectorEventType);
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }
}
