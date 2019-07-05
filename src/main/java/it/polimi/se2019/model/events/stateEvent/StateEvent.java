package it.polimi.se2019.model.events.stateEvent;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

/**a event needed to notify the players about the state the game is in a given moment*/
public class StateEvent extends Event implements Serializable {

    /**@deprecated */
    public StateEvent(){
        this.setEventType(EventTypes.StateEvent);
    }

    /**a string containing the name of the state*/
    private String state;

   /**@return state*/
    public String getState(){
        return this.state;
    }

    /**constructor,
     * @param state to set state attribute */
    public StateEvent(String state){
        this.state = state;
    }
}
