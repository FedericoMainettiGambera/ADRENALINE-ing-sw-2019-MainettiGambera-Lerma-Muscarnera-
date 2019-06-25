package it.polimi.se2019.networkHandler.RMIREDO;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.view.components.View;

import java.util.Observable;

public class RmiServerListenerNetworkHandler extends Observable implements Runnable {

    private  Object objectReceived;

    public RmiServerListenerNetworkHandler(View view, Object objectReceived){
        this.addObserver(view);
        this.objectReceived = objectReceived;
    }

    @Override
    public void run() {
        //inoltra gli eventi ricevuti dal server alla view
        Event event = (Event)this.objectReceived;
        this.setChanged();
        this.notifyObservers(event);
    }

}
