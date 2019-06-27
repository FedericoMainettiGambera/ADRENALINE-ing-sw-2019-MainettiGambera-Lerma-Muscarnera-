package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**in case a ammo card drew made it possible to draw a power up and user already holds too many,
 *the state pattern will get here*/
public class GrabStuffStateDrawAndDiscardPowerUp implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffStateDrawAndDiscardPowerUp.class.getName());

    private int actionNumber;

    private Thread inputTimer;

    private Player playerToAsk;

    public GrabStuffStateDrawAndDiscardPowerUp(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**if the user gets lucky, the card he decided to grab give them the possibility to draw a power up,
     * lucky but not lucky enough! If they want to draw it, they will have to discard one they already have in their hand,
     * indeed it is only possible to hold in hand 2 power up at the same time
     * @param playerToAsk holds the current playing player*/
    @Override
    public void askForInput(Player playerToAsk) {

        drawANewPowerUp(playerToAsk);

        //ask which power up to discard
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askPowerUpToDiscard((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception occured"+" "+e.getCause()+" "+ e.getClass()+ Arrays.toString(e.getStackTrace()));
        }
    }

    /**@param playerToAsk, make the player to ask draw a new power up*/
    public void drawANewPowerUp(Player playerToAsk){

        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //draw a new power up
        out.println("<Server> The player draws a power up: " + ModelGate.model.getPowerUpDeck().getFirstCard().getID());
        ModelGate.model.getPowerUpDeck().moveCardTo(
                playerToAsk.getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

    }

    /**once the player has made their choices, we can handle them using a
     * @param viewControllerEvent as parameter to discern what to do
     *this function will let the player discards the power up he prefers
     * and make him able to continue their turn with the second action,
     * in case this was it, the turn will pass to the next playing player
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();

        discardPowerUp(viewControllerEvent);

        //set next state

        if(this.actionNumber == 1){

           //if you are in final frenzy mode and you are or come after the starting player
            if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
           else{
                ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
        if(this.actionNumber == 2) {
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }

    /**@param viewControllerEvent, this function extrapolates from the event the power up the player wants to discard*/
    public void discardPowerUp(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt viewControllerEventInt = (ViewControllerEventInt)viewControllerEvent;

        //discard power up
        out.println("<SERVER> The player discards power up: " + ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(viewControllerEventInt.getInput()).getID());
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(viewControllerEventInt.getInput()).getID()
        );

    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }
}
