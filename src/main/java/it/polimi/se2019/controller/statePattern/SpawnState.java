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
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**Implements the spawn of the players who died*/
public class SpawnState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(SpawnState.class.getName());

    private Player playerToSpawn;

    private List<Player> deadPlayers;

    private Thread inputTimer;


    public SpawnState(List<Player> deadPlayers){
        out.println("<SERVER> New state: " + this.getClass());
        this.deadPlayers = deadPlayers;
    }

    @Override
    public void askForInput(Player nullPlayer) {
        //player to ask is null !

        deadDrawPowerUp();

        //list of power up of the player to spawn
        ArrayList<PowerUpCard> powerUpCards = (ArrayList)playerToSpawn.getPowerUpCardsInHand().getCards();
        ArrayList<PowerUpCardV> powerUpCardsV = new ArrayList<>();
        for (PowerUpCard p: powerUpCards) {
            powerUpCardsV.add(p.buildPowerUpCardV());
        }

        //ask which power up he wants to discard and spawn to
        try {
            SelectorGate.getCorrectSelectorFor(playerToSpawn).setPlayerToAsk(playerToSpawn);
            SelectorGate.getCorrectSelectorFor(playerToSpawn).askSpawn(powerUpCardsV);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToSpawn, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception Occurred: "+e.getClass()+" "+e.getCause());
        }
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();

        handleVce(viewControllerEvent);

        if(!this.deadPlayers.isEmpty()) {
            out.println("<SERVER> Player spawned and removed from the list of dead players");
            this.deadPlayers.remove(0);
        }

        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState(this.deadPlayers));
        ViewControllerEventHandlerContext.getState().doAction(null);

    }

    @Override
    public void handleAFK(){
        //TODO
        this.playerToSpawn.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        out.println("<SERVER> randomly making player spawn using first card in hand.");
        this.doAction(new ViewControllerEventString(playerToSpawn.getPowerUpCardsInHand().getCards().get(0).getID()));
    }

    public void printDied(){

        String string="---------------------------------------------------------------------------------------------------------";

        System.err.println(string);
        System.err.println(string);
        System.err.println("----------------------------------        SOMEONE DIED        -------------------------------------------");
        System.err.println(string);
        System.err.println(string);
    }

    public void handleVce(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString viewControllerEventString = (ViewControllerEventString)viewControllerEvent;

        //set spawning position
        PowerUpCard cardChosen = playerToSpawn.getPowerUpCardsInHand().getCard(viewControllerEventString.getInput());

        try {
            Position spawnPosition = ModelGate.getModel().getBoard().getSpawnpointOfColor(cardChosen.getColor());
            out.println("<SERVER> Spawning player in position [" + spawnPosition.getX() + "][" + spawnPosition.getY() +"]");
            playerToSpawn.setPosition(spawnPosition);
        } catch (Exception e) {
            logger.severe("Exception Occured: "+e.getClass()+" "+e.getCause());
        }



        //discard the power up card
        out.println("<SERVER>Discarding the chosen power up: " + cardChosen.getID());

        playerToSpawn.getPowerUpCardsInHand().moveCardTo(
                ModelGate.getModel().getPowerUpDiscardPile(),
                viewControllerEventString.getInput()
        );


    }

    public void deadDrawPowerUp(){

        printDied();

        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + deadPlayers.get(0).getNickname() + "\"");

        if(!this.deadPlayers.isEmpty()) {
            this.playerToSpawn = deadPlayers.get(0);

            out.println("<SERVER> Making " + playerToSpawn.getNickname() + " draw a power up");
            //draw a power up
            ModelGate.getModel().getPowerUpDeck().moveCardTo(
                    playerToSpawn.getPowerUpCardsInHand(),
                    ModelGate.getModel().getPowerUpDeck().getFirstCard().getID()
            );
        }

    }
}
