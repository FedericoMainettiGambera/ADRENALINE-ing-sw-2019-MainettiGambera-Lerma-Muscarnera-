package it.polimi.se2019.model.events.reconnectionEvent;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.virtualView.RMIREDO.RmiInterface;

import java.io.Serializable;
import java.util.List;

public class ReconnectionEvent extends Event implements Serializable {
    private List<String> listOfAFKPlayers;
    private RmiInterface client;
    public ReconnectionEvent(List<String> listOfAFKPlayers){
        this.listOfAFKPlayers= listOfAFKPlayers;
    }
    public List<String> getListOfAFKPlayers() {
        return listOfAFKPlayers;
    }
    public void setClient(RmiInterface client) {
        this.client = client;
    }
    public RmiInterface getClient() {
        return client;
    }
}

