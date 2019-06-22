package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.logging.Logger;

public class GrabStuffStateDrawPowerUp implements State {
    private int actionNumber;
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(State.class.getName());

    public GrabStuffStateDrawPowerUp(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        out.println("<SERVER> "+ this.getClass() +".doAction();");

        Position PlayerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();

        out.println("<SERVER> The ammo card makes the player draw a power up");
        ModelGate.model.getPowerUpDeck().moveCardTo(
                ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

        //set next state
        State state = null;
        if(this.actionNumber == 1){
            if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0)
                {
                    state = new ReloadState(false);//TODO check reload state cuz its the last turn and you dont really need to reload
                }
              else  state = new TurnState(2);
        }
        if(this.actionNumber == 2){
            //TODO not sure
            state = new ReloadState(false);
        }
        ViewControllerEventHandlerContext.setNextState(state);
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    @Override
    public void handleAFK() {
    }
}
