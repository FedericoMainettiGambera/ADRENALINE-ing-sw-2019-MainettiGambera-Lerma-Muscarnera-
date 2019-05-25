package it.polimi.se2019.controller;

import it.polimi.se2019.controller.statePattern.State;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.util.Observable;
import java.util.Observer;

public class ViewControllerEventHandlerContext implements Observer {

    public static State state;

    public static String networkConnection;

    public static void setNextState(State nextState) {
        state = nextState;
    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;
        state.doAction(VCE);
    }
}