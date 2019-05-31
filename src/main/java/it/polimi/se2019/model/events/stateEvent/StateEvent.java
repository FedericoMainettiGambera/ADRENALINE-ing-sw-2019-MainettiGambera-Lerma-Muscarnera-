package it.polimi.se2019.model.events.stateEvent;

import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

public class StateEvent extends Event implements Serializable {

    private String state;

    public String getState(){
        return this.state;
    }

    public StateEvent(String state){
        this.state = state;
    }
}
