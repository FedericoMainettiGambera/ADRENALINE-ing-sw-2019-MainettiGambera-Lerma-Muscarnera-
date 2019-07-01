package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**implements the very first spawn of each player*/
public class FirstSpawnState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FirstSpawnState.class.getName());


    public FirstSpawnState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    /**the player to ask the input to*/
    private Player playerToAsk;
    /**thread that starts the timer*/
    private Thread inputTimer;
    /**name of the bot*/
    private String botNickname="Terminator" ;


    /** @param playerToAsk  we need to know who is spawning and where it wants to spawn
     * if bot mode is active, first player will choose where the bot will spawn*/
    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask to "playerToAsk" what power up he want to discard to spawn on its correspondent SpawnPointColor
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            if(ModelGate.model.isBotActive()&&ModelGate.model.getPlayerList().getPlayer(botNickname).getPosition()==null){
                out.println("<SERVER> asking player to spawn himself and the bot");
                SelectorGate.getCorrectSelectorFor(playerToAsk).askFirstSpawnPosition(playerToAsk.getPowerUpCardsInHand().getCards(), true);
            }
            else{
                out.println("<SERVER> asking player to spawn himself");
                SelectorGate.getCorrectSelectorFor(playerToAsk).askFirstSpawnPosition(playerToAsk.getPowerUpCardsInHand().getCards(), false);
            }
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "EXCEPTION ", e);
        }
    }

    /**
     * @param vce is needed to know which powerup card has been discarded this card will determine where the player will spawn,
     * the player is set in the correct position on the map
     * the power up discarded is moved to the discard pile of power up
     * the player who just spawned is now ready for his first turn!
     * context is set on turnstate */
    @Override
    public void doAction(ViewControllerEvent vce){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        spawnBot(vce);

        spawnPlayer(vce);

        changeState();
    }


    /**@param vce is parsed to know where the bot will spawn*/
    private void spawnBot(ViewControllerEvent vce){

        ViewControllerEventTwoString viewControllerEventTwoString = (ViewControllerEventTwoString) vce;

        if(ModelGate.model.isBotActive() && ModelGate.model.getPlayerList().getPlayer(botNickname).getPosition()==null){
            String spawnPointColorForBot = viewControllerEventTwoString.getInput2();
            AmmoCubesColor spawnForBot =  AmmoCubesColor.red;
            switch (spawnPointColorForBot) {
                case "red":
                    spawnForBot = AmmoCubesColor.red;
                    break;
                case "blue":
                    spawnForBot = AmmoCubesColor.blue;
                    break;
                case "yellow":
                    spawnForBot = AmmoCubesColor.yellow;
                    break;
                default:
                    try {
                        throw new IllegalStateException("shouldn't spawn the bot");
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "EXCEPTION ", e);
                    }

                    break;
            }
            out.println("<SERVER> the bot will spawn in the SpawnPoint of color: " + spawnPointColorForBot);
            try {
                Position spawnPointPositionForBot = ModelGate.model.getBoard().getSpawnpointOfColor(spawnForBot);
                ModelGate.model.getPlayerList().getPlayer(botNickname).setPosition(spawnPointPositionForBot);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }
        }
    }

    /**@param vce is parsed to know which power up the player decided to discard and the consequent position where the player will be spawned */
    private void spawnPlayer(ViewControllerEvent vce){
        ViewControllerEventTwoString viewControllerEventTwoString = (ViewControllerEventTwoString) vce;

        PowerUpCard cardChosen = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCard(viewControllerEventTwoString.getInput1());
        Position spawnPosition = null;
        out.println("<SERVER> chosen card ID: " + cardChosen.getID());
        out.println("<SERVER> chosen card name: " + cardChosen.getName());
        out.println("<SERVER> chosen color: " + cardChosen.getColor());

        try {
            spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
        } catch (Exception e) {
            logger.severe("Exception occurred:"+e.getCause()+e.getClass()+ Arrays.toString(e.getStackTrace()));
        }
        assert spawnPosition!=null;
        ModelGate.model.getCurrentPlayingPlayer().setPosition(spawnPosition);
        out.println("<SERVER> Spawning in the SpawnPoint of color " + cardChosen.getColor() + ", in coordinates X:(" +spawnPosition.getX() + "), Y:(" + spawnPosition.getY() + ").");


        //discard the power up card
        out.println("<SERVER> Discarding the chosen power up");
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                cardChosen.getID()
        );
    }

    /** set following state*/
    private void changeState(){
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
