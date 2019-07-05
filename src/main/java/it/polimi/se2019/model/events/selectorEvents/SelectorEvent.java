package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

/**implements the event that requires a selection from the user
 *  * * @author LudoLerma
 *  *  * @author FedericoMainettiGambera
 *  */
public class SelectorEvent extends Event implements Serializable {
    /**the type of selectorEvent required*/
    private SelectorEventTypes selectorEventTypes;

    /**constructor,
     * @param selectorEventType needed to set selectorEventTypes attribute */
    public SelectorEvent(SelectorEventTypes selectorEventType){
        this.selectorEventTypes = selectorEventType;
        this.setEventType(EventTypes.SelectorEvent);
    }

    /**@return selectorEventTypes*/
    public SelectorEventTypes getSelectorEventTypes(){
        return this.selectorEventTypes;
    }
}
