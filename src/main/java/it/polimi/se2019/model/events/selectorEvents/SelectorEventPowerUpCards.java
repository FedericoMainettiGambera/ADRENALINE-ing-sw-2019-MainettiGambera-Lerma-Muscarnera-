package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PowerUpCardV;
import java.util.List;

public class SelectorEventPowerUpCards extends SelectorEvent {

    private List<PowerUpCardV> powerUpCards;

    public SelectorEventPowerUpCards(SelectorEventTypes selectorEventType,List<PowerUpCardV> powerUpCards) {
        super(selectorEventType);
        this.powerUpCards=powerUpCards;
    }

    public List<PowerUpCardV> getPowerUpCards(){
        return this.powerUpCards;
    }

}
