package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PowerUpCardV;
import java.io.Serializable;
import java.util.List;

/**a event used to ask the player where he wants to spawn for the first time
 * if they are the starting player, they will, afterwards, asked to spawn the bot too
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class SelectorEventPowerUpCardsAndBoolean extends SelectorEventPowerUpCards implements Serializable {

    /**attribute that indicates if the player asked needs to spawn the bot*/
    private boolean spawnBot;

    /**constructor,
     * @param powerUpCards the cards the player needs to choose between
     * @param selectorEventType the type of event
     @param spawnBot to set spawnBot attribute
      * */
    public SelectorEventPowerUpCardsAndBoolean(SelectorEventTypes selectorEventType, List<PowerUpCardV> powerUpCards, boolean spawnBot) {
        super(selectorEventType, powerUpCards);
        this.spawnBot=spawnBot;
    }

    /**@return spawnBot*/
    public boolean isSpawnBot() {
        return spawnBot;
    }
}
