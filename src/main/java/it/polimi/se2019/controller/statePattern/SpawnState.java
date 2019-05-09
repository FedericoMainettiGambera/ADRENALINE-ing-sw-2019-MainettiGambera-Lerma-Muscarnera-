package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;

import java.util.ArrayList;

public class SpawnState implements State {

    private Player playerToSpawn;

    private ArrayList<Player> deadPlayers;

    private int numberOfSpawnedPlayers;

    public SpawnState(ArrayList<Player> deadPlayers){
        this.deadPlayers = deadPlayers;
        this.numberOfSpawnedPlayers = 0;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //(playerToAsk is null)

        this.playerToSpawn = deadPlayers.get(numberOfSpawnedPlayers);

        //draw a power up
        ModelGate.model.getPowerUpDeck().moveCardTo(
                playerToSpawn.getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

        //ask which power up he wants to discard
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;

        //set spawning position
        PowerUpCard cardChosen = playerToSpawn.getPowerUpCardsInHand().getCard(VCEString.getInput());
        try {
            Position spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
            playerToSpawn.setPosition(spawnPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //discard the power up card
        playerToSpawn.getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEString.getInput()
        );

        this.numberOfSpawnedPlayers++;

        if(this.numberOfSpawnedPlayers == this.deadPlayers.size()){
            //set next state & change current playing player
            ModelGate.model.getPlayerList().setNextPlayingPlayer();
            ViewControllerEventHandlerContext.setNextState(new TurnState(1));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else {
            ViewControllerEventHandlerContext.state.askForInput(null);
        }
    }
}
