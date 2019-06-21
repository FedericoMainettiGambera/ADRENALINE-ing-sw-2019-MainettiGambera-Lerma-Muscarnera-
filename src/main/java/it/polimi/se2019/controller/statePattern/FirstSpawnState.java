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
import java.util.Arrays;
import java.util.logging.Logger;

public class FirstSpawnState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FirstSpawnState.class.getName());


    public FirstSpawnState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    private Player playerToAsk;
    private Thread inputTimer;


    /** @param playerToAsk  we need to know who is spawning and where it wants to spawn
     * if bot mode is active, first player will choose where the bot will spawn*/
    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //TODO the first player who spawns needs to choose -freely- where the bot will spawn too, he choose it after having seen his cards but before choosing his own spot to spawn

        //ask to "playerToAsk" what power up he want to discard to spawn on its correspondent SpawnPointColor
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askFirstSpawnPosition((ArrayList)playerToAsk.getPowerUpCardsInHand().getCards());
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
           logger.severe("Exception occurred:"+" "+ " "+ Arrays.toString(e.getStackTrace())+e.getClass()+e.getCause());
        }
    }

    /**
     * @param VCE is needed to know which powerup card has been discarded this card will determine where the player will spawn,
     * the player is set in the correct position on the map
     * the power up discarded is moved to the discard pile of power up
     * the player who just spawned is now ready for his first turn!
     * context is set on turnstate */
    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEPowerUpId = (ViewControllerEventString) VCE;

        //set spawning position
        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(VCEPowerUpId.getInput());
        Position spawnPosition = null;
        out.println("<SERVER> choosen card ID: " + cardChosen.getID());
        out.println("<SERVER> choosen card name: " + cardChosen.getName());
        out.println("<SERVER> choosen color: " + cardChosen.getColor());

        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
        } catch (Exception e) {
            logger.severe("Exception occurred:"+e.getCause()+e.getClass()+ Arrays.toString(e.getStackTrace()));
        }
        assert spawnPosition!=null;
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);
        out.println("<SERVER> Spawning in the SpawnPoint of color " + cardChosen.getColor() + ", in coordinates X:(" +spawnPosition.getX() + "), Y:(" + spawnPosition.getY() + ").");


        //discard the power up card
        out.println("<SERVER> Discarding the choosen power up");
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                cardChosen.getID()
        );

        //set next State
        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    /**
     * if player in not reachable we provide to set it AFK,
     * it will anyhow spawn randomly
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        out.println("<SERVER> randomly spawning player.");
        Position spawnPosition = null;
        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(playerToAsk.getPowerUpCardsInHand().getFirstCard().getColor());
        } catch (Exception e) {
            logger.severe("Exception occurred:"+" "+ " "+ Arrays.toString(e.getStackTrace())+e.getClass()+e.getCause());
        }
        assert spawnPosition!=null;
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
