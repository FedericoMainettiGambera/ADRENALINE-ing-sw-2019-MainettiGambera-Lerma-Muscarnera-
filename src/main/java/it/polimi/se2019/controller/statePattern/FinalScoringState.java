package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class FinalScoringState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FinalScoringState.class.getName());


    public FinalScoringState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        out.println("<SERVER> "+ this.getClass() +".doAction();");
        //TODO lot of stuff here
        System.exit(0);

    }

    @Override
    public void handleAFK() {
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //empty
    }
}
