package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;

import java.util.ArrayList;

public class GrabStuffStateDrawAndDiscardPowerUp implements State {

    private int actionNumber;

    public GrabStuffStateDrawAndDiscardPowerUp(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //draw a new power up
        System.out.println("<Server> The player draws a power up: " + ModelGate.model.getPowerUpDeck().getFirstCard().getID());
        ModelGate.model.getPowerUpDeck().moveCardTo(
                playerToAsk.getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

        //ask which power up to discard
        if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
            SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorSocket.askPowerUpToDiscard((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
        }
        else{
            SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorRMI.askPowerUpToDiscard((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        //discard power up
        System.out.println("<SERVER> The player discards power up: " + ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(VCEInt.getInput()).getID());
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().get(VCEInt.getInput()).getID()
        );

        //set next state
        if(this.actionNumber == 1){
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        if(this.actionNumber == 2) {
            ViewControllerEventHandlerContext.setNextState(new ReloadState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
