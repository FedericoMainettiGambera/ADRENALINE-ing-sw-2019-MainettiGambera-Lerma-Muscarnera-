package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class InitialState implements State {
    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventHandlerContext.setNextState(new InitialState());
    }
}
