package it.polimi.se2019.model.events.reconnectionEvent;

import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
import java.util.List;

public class ReconnectionEvent extends Event implements Serializable {
    private List<String> listOfAFKPlayers;
    public ReconnectionEvent(List<String> listOfAFKPlayers){
        this.listOfAFKPlayers= listOfAFKPlayers;
    }
    public List<String> getListOfAFKPlayers() {
        return listOfAFKPlayers;
    }
}

