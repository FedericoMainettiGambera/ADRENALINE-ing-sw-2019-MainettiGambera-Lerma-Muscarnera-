package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.model.events.ViewControllerEvent;

public interface State {
    public void doAction(ViewControllerEvent VCE);
}