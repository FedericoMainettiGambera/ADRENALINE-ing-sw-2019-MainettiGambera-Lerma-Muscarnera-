package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;

/**in case a ammo card drew made it possible to draw a power up,
 *the state pattern will get here
 * * @author LudoLerma
 *  * @author FedericoMainettiGambera
 *  */
public class GrabStuffStateDrawPowerUp implements State {
   /**if it's action number 1 or number 2*/
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

        ViewControllerEventHandlerContext.setNextState(setNextState());
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
    }

    /**this function make the player draw a power up*/
    private void makeThePlayerDraw(){
        out.println("<SERVER> "+ this.getClass() +".doAction();");
        out.println("<SERVER> The ammo card makes the player draw a power up");
        ModelGate.getModel().getPowerUpDeck().moveCardTo(
                ModelGate.getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                ModelGate.getModel().getPowerUpDeck().getFirstCard().getID()
        );

    }


    /**set the following state
     * @return state, the state next to follow
     * */
    private State  setNextState(){

        State state = null;
        if(this.actionNumber == 1){
            if (ModelGate.getModel().hasFinalFrenzyBegun() && ModelGate.getModel().getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0)
                {
                    state = new ReloadState(false);
                }
              else  state = new TurnState(2);
        }
        if(this.actionNumber == 2){
            state = new ReloadState(false);
        }

        return state;
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
