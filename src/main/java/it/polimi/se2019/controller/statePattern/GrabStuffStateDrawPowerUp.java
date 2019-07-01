package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;

/**in case a ammo card drew made it possible to draw a power up,
 *the state pattern will get here*/
public class GrabStuffStateDrawPowerUp implements State {
    private int actionNumber;
    private static PrintWriter out= new PrintWriter(System.out, true);

    /** @param actionNumber indicates if this is the first or the second action*/
    public GrabStuffStateDrawPowerUp(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**@param playerToAsk indicates the current playing player
     * any input is needed here */
    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    /**@param viewControllerEvent  unused
     *this function let the player draw a power up, they wont have to discard any because they are holding in their hand
     * less than two*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        makeThePlayerDraw();

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

    /**this function make the player draw a power up*/
    public void makeThePlayerDraw(){
        out.println("<SERVER> "+ this.getClass() +".doAction();");
        out.println("<SERVER> The ammo card makes the player draw a power up");
        ModelGate.model.getPowerUpDeck().moveCardTo(
                ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

    }

    /**
     * theorically sets the player AFK in case they don't send required input in a while
     * here we have no input so we'll find out in the next state
     * */
    @Override
    public void handleAFK() {
       //empty for now
    }
}
