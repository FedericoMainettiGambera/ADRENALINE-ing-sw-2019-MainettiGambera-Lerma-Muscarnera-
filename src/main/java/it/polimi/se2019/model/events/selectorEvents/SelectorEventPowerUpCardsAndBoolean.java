package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PowerUpCardV;
import java.io.Serializable;
import java.util.List;

public class SelectorEventPowerUpCardsAndBoolean extends SelectorEventPowerUpCards implements Serializable {
    private boolean spawnBot;

    public SelectorEventPowerUpCardsAndBoolean(SelectorEventTypes selectorEventType, List<PowerUpCardV> powerUpCards, boolean spawnBot) {
        super(selectorEventType, powerUpCards);
        this.spawnBot=spawnBot;
    }

    public boolean isSpawnBot() {
        return spawnBot;
    }
}
