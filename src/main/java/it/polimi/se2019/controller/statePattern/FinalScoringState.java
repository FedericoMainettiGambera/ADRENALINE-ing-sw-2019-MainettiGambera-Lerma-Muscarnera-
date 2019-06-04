package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public class FinalScoringState implements State {

    public FinalScoringState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

    }

    @Override
    public void handleAFK() {
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");

    }
}
