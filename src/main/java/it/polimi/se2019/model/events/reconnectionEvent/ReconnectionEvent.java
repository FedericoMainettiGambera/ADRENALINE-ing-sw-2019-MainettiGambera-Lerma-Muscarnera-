package it.polimi.se2019.model.events.reconnectionEvent;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.virtualView.RMIREDO.RmiInterface;

import java.io.Serializable;
import java.util.List;

/**event of reconnection
 * @author federicomainettigambera*/
public class ReconnectionEvent extends Event implements Serializable {

    /**list of player set AFK*/
    private List<String> listOfAFKPlayers;
    /**reference to the client */
    private RmiInterface client;
    /**@param listOfAFKPlayers to set listOfAFKPlayers attribute
     * constructor*/
    public ReconnectionEvent(List<String> listOfAFKPlayers){
        this.listOfAFKPlayers= listOfAFKPlayers;
    }
    /**@return listOfAFKplayers*/
    public List<String> getListOfAFKPlayers() {
        return listOfAFKPlayers;
    }
    /**@param client to set client attribute*/
    public void setClient(RmiInterface client) {
        this.client = client;
    }
    /**@return client*/
    public RmiInterface getClient() {
        return client;
    }
}

