package it.polimi.se2019.networkHandler.RMI;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.view.components.View;

import java.util.Observable;

public class RMIObsHandler extends Observable {

    public RMIObsHandler(View view){
        this.addObserver(view);
    }

    public void  notify(Event E){
        //System.out.println("<CLIENT> An Event has arrived: class is " + E.getClass() + " and Event Type is " + E.getEventType());
        this.setChanged();
        this.notifyObservers(E);
    }
}
