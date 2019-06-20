package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public interface State {
    public void askForInput(Player playerToAsk);

    public void doAction(ViewControllerEvent VCE) throws Exception;

    public void handleAFK();
}

/* The state pattern can access the model using:
 * ModelGame.model.whateverYouNeed()
 * */

/* The state pattern can set the next State like this:
 * ViewControllerEventHandlerContext.setNextState(new nextState());
 * */