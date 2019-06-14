package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.util.ArrayList;

public class SelectorEventPowerUpCards extends SelectorEvent {

    private ArrayList<PowerUpCardV> powerUpCards;

    public SelectorEventPowerUpCards(SelectorEventTypes selectorEventType,ArrayList<PowerUpCardV> powerUpCards) {
        super(selectorEventType);
        this.powerUpCards=powerUpCards;
    }

    public ArrayList<PowerUpCardV> getPowerUpCards(){
        return this.powerUpCards;
    }

}
