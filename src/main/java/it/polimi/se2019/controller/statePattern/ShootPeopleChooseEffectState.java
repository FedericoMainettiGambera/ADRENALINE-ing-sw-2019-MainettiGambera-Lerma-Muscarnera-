package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public class ShootPeopleChooseEffectState implements State{
    @Override
    public void askForInput(Player playerToAsk) {

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }

    @Override
    public void handleAFK() {
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }
}
