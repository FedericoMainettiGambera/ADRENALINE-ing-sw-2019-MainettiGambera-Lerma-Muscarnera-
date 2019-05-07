package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.PowerUpCard;
import it.polimi.se2018.model.Square;
import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class FirstSpawnState implements State {

    public FirstSpawnState(){
    }

    @Override
    public void askForInput(Player playerToAsk){
        //ask to "playerToAsk" inputs
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws NullPointerException {
        ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

        //set spawning position
        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());
        Position spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
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
