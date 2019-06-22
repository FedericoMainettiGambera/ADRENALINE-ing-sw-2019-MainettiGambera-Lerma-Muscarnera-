package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.io.Serializable;

public class SelectorEventBoolean extends SelectorEvent implements Serializable{
    private SelectorEventTypes selectorEventTypes;
    private boolean canBot;

    public SelectorEventBoolean(SelectorEventTypes selectorEventType, boolean canBot){
      super(selectorEventType);
      this.canBot=canBot;
    }

    public boolean isCanBot() {
        return canBot;
    }
}
