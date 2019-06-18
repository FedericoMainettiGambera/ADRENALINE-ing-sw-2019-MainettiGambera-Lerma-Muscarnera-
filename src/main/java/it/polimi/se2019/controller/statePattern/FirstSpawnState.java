package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FirstSpawnState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FirstSpawnState.class.getName());


    public FirstSpawnState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    private Player playerToAsk;
    private Thread inputTimer;

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask to "playerToAsk" what power up he want to discard to spawn on its correspondent SpawnPointColor
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askFirstSpawnPosition((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws NullPointerException{
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

        //set spawning position
        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());
        Position spawnPosition = null;
        out.println("<SERVER> choosen card: " + cardChosen.getID());
        out.println("<SERVER> choosen color: " + cardChosen.getColor());

        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);
        out.println("<SERVER> Spawning in the SpawnPoint of color " + cardChosen.getColor() + ", in coordinates X:(" +spawnPosition.getX() + "), Y:(" + spawnPosition.getY() + ").");


        //discard the power up card
        out.println("<SERVER> Discarding the choosen power up");
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEPowerUpId.getInput()
        );

        //set next State
        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        out.println("<SERVER> randomly spwaning player.");
        Position spawnPosition = null;
        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(playerToAsk.getPowerUpCardsInHand().getFirstCard().getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);
        out.println("<SERVER> Spawning in SpawnPoint of color " + playerToAsk.getPowerUpCardsInHand().getFirstCard().getColor() + ", in coordinates X:(" +spawnPosition.getX() + "), Y:(" + spawnPosition.getY() + ").");


        //discard the power up card
        out.println("<SERVER> Discarding the randomly chosen power up");
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                playerToAsk.getPowerUpCardsInHand().getFirstCard().getID()
        );

        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }
}
