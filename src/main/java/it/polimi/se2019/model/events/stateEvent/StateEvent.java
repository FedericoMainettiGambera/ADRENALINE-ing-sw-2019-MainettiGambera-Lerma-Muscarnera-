package it.polimi.se2019.model.events.stateEvent;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

public class StateEvent extends Event implements Serializable {

    public StateEvent(){
        this.setEventType(EventTypes.StateEvent);
    }

    private String state;

    public String getState(){
        return this.state;
    }

    public StateEvent(String state){
        this.state = state;
    }
}
