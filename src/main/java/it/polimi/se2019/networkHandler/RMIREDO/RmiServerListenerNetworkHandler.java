package it.polimi.se2019.networkHandler.RMIREDO;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.View;

import java.util.Observable;
/**listens to server event to be sent to client
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class RmiServerListenerNetworkHandler extends Observable implements Runnable {

    /**the object received from the server*/
    private  Object objectReceived;
    /**constructor
     * @param view add the view to the observers
     * @param objectReceived to set objectReceived*/
    public RmiServerListenerNetworkHandler(View view, Object objectReceived){
        this.addObserver(view);
        this.objectReceived = objectReceived;
    }

    /**isneds all the received object from server to view*/
    @Override
    public void run() {

        Event event = (Event)this.objectReceived;
        this.setChanged();
        this.notifyObservers(event);
    }

}
