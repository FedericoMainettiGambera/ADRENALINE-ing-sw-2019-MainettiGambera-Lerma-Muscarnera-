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

public class SpawnState implements State {

    private Player playerToSpawn;

    private ArrayList<Player> deadPlayers;


    public SpawnState(ArrayList<Player> deadPlayers){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.deadPlayers = deadPlayers;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //(playerToAsk is null)
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToSpawn.getNickname() + "\"");

        if(!this.deadPlayers.isEmpty()) {
            this.playerToSpawn = deadPlayers.get(0);
        }

        //draw a power up
        ModelGate.model.getPowerUpDeck().moveCardTo(
                playerToSpawn.getPowerUpCardsInHand(),
                ModelGate.model.getPowerUpDeck().getFirstCard().getID()
        );

        //ask which power up he wants to discard
        SelectorGate.selector.setPlayerToAsk(playerToAsk);
        SelectorGate.selector.askSpawn((ArrayList)playerToSpawn.getPowerUpCardsInHand().getCards());
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;

        //set spawning position
        PowerUpCard cardChosen = playerToSpawn.getPowerUpCardsInHand().getCard(VCEString.getInput());

        try {
            Position spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
            System.out.println("<SERVER> Spawning player in position [" + spawnPosition.getX() + "][" + spawnPosition.getY() +"]");
            playerToSpawn.setPosition(spawnPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //discard the power up card
        System.out.println("<SERVER>Discarding choosen power up: " + cardChosen.getID());
        playerToSpawn.getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEString.getInput()
        );

        if(!this.deadPlayers.isEmpty()) {
            this.deadPlayers.remove(0);
        }

        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState(this.deadPlayers));
        ViewControllerEventHandlerContext.state.doAction(null);

    }
}
