package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;

import java.util.ArrayList;

public class FirstSpawnState implements State {

    public FirstSpawnState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //ask to "playerToAsk" inputs
        SelectorGate.selector.setPlayerToAsk(playerToAsk);
        SelectorGate.selector.askFirstSpawnPosition((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws NullPointerException{
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

        //set spawning position
        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());
        Position spawnPosition = null;
        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);


        //discard the power up card
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEPowerUpId.getInput()
        );

        //set next State
        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
