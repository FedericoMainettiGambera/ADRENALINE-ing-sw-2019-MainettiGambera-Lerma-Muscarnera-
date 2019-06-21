package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;

public class SelectorEventTurnAction extends SelectorEvent {

    private int actionNumber;

    private boolean canUsePowerUp;

    public SelectorEventTurnAction(SelectorEventTypes selectorEventType, int actionNumber, boolean canUsePowerUp){
        super(selectorEventType);
        this.actionNumber = actionNumber;
        this.canUsePowerUp = canUsePowerUp;
    }

    public boolean canUsePowerUp() {
        return canUsePowerUp;
    }

    public int getActionNumber() {
        return actionNumber;
    }
}
