package it.polimi.se2019.model.events;
import it.polimi.se2019.model.enumerations.EventTypes;
/**abstract class for event
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public abstract class Event {
    /**the type of the event (see the enumerations)*/
    private EventTypes eventType ;
    /**constructor,
     * @param eventType to set eventType attribute*/
    public void setEventType(EventTypes eventType){
        this.eventType = eventType;
    }
    /**@deprecated */
    public EventTypes getEventType(){
        return this.eventType;
    }
}
