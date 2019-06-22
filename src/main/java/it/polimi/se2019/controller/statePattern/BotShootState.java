package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class BotShootState implements State{
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    private State nextState;

    public BotShootState(State state){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = state;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input + start timer
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //parse VCE

        //DO STUFF

        //change state in the next state
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");

        //handle case timer ends before player answers
    }
}
