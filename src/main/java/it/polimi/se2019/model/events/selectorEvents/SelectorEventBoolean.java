package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.io.Serializable;

/**implements GameSetUp connection selector event
 * @author LudoLerma
 * @author Federicomainettigambera*/
public class SelectorEventBoolean extends SelectorEvent implements Serializable{

    /**indicates if it's possible to use the bot*/
    private boolean canBot;

    /**constructor,
     * @param canBot to set the canBot variable
     * @param selectorEventType the type of event*/
    public SelectorEventBoolean(SelectorEventTypes selectorEventType, boolean canBot){
      super(selectorEventType);
      this.canBot=canBot;
    }

    /**@return canBot*/
    public boolean isCanBot() {
        return canBot;
    }
}
