package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PowerUpCardV;
import java.util.List;

/**an event used to ask the player to choose between the list of power ups
 *   @author LudoLerma
 *  @author FedericoMainettiGambera
 *  */
public class SelectorEventPowerUpCards extends SelectorEvent {

    /** a list of power ups the player */
    private List<PowerUpCardV> powerUpCards;

    /**constructor,
     * @param selectorEventType the type of selectorEvent
     * @param powerUpCards to set powerUpCards attribute
     * */
    public SelectorEventPowerUpCards(SelectorEventTypes selectorEventType,List<PowerUpCardV> powerUpCards) {
        super(selectorEventType);
        this.powerUpCards=powerUpCards;
    }

    /**@return powerUpCards*/
    public List<PowerUpCardV> getPowerUpCards(){
        return this.powerUpCards;
    }

}
