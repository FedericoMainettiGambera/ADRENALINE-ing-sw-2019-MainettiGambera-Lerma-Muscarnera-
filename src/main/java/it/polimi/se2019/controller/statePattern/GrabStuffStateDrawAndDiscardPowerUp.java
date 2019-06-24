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

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //draw a new power up
        out.println("<Server> The player draws a power up: " + ModelGate.model.getPowerUpDeck().getFirstCard().getID());
        ModelGate.model.getPowerUpDeck().moveCardTo(
                playerToAsk.getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

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

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        //discard power up
        out.println("<SERVER> The player discards power up: " + ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(VCEInt.getInput()).getID());
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(VCEInt.getInput()).getID()
        );

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
