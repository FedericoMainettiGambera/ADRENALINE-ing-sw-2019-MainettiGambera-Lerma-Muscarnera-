/**package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.controller.ViewControllerEventHandlerContext;

import java.util.Observable;

public class RMIObsVirtualView extends Observable {

    public RMIObsVirtualView(ViewControllerEventHandlerContext controller){
        this.addObserver(controller);
    }

    public void notify(Object o){
        this.setChanged();
        this.notifyObservers(o);
    }
}
**/