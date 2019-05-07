package it.polimi.se2018.controller;

import it.polimi.se2018.controller.statePattern.State;
import it.polimi.se2018.model.events.ViewControllerEvent;

import java.util.Observable;
import java.util.Observer;

public class ViewControllerEventHandlerContext implements Observer {

    public static State state;


    public static void setNextState(State nextState) {
        state = nextState;
    }


    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;
        state.doAction(VCE);

    }
}