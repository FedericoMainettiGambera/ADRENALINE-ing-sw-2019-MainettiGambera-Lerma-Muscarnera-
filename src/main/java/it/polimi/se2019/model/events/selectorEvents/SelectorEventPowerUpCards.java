package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.ArrayList;

public class SelectorEventPowerUpCards extends SelectorEvent {

    private ArrayList<PowerUpCard> powerUpCards;

    public SelectorEventPowerUpCards(SelectorEventTypes selectorEventType,ArrayList<PowerUpCard> powerUpCards) {
        super(selectorEventType);
        this.powerUpCards=powerUpCards;
    }

    public ArrayList<PowerUpCard> getPowerUpCards(){
        return this.powerUpCards;
    }

}
