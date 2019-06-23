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
import java.util.logging.Logger;

public class SpawnState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(SpawnState.class.getName());

    private Player playerToSpawn;

    private ArrayList<Player> deadPlayers;

    private Player playerToAsk;

    private Thread inputTimer;


    public SpawnState(ArrayList<Player> deadPlayers){
        out.println("<SERVER> New state: " + this.getClass());
        this.deadPlayers = deadPlayers;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        //(playerToAsk is null)
        out.println("<SERVER> ("+ this.getClass() +") AskingForInput started");

        if(!this.deadPlayers.isEmpty()) {
            this.playerToSpawn = deadPlayers.get(0);

            out.println("<SERVER> Making " + playerToSpawn.getNickname() + " draw a power");
            //draw a power up
            ModelGate.model.getPowerUpDeck().moveCardTo(
                    playerToSpawn.getPowerUpCardsInHand(),
                    ModelGate.model.getPowerUpDeck().getFirstCard().getID()
            );
        }

        ArrayList<PowerUpCard> powerUpCards = (ArrayList)playerToSpawn.getPowerUpCardsInHand().getCards();
        ArrayList<PowerUpCardV> powerUpCardsV = new ArrayList<>();
        for (PowerUpCard p: powerUpCards) {
            powerUpCardsV.add(p.buildPowerUpCardV());
        }

        //ask which power up he wants to discard
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askSpawn(powerUpCardsV);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception Occured: "+e.getClass()+" "+e.getCause());
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;

        //set spawning position
        PowerUpCard cardChosen = playerToSpawn.getPowerUpCardsInHand().getCard(VCEString.getInput());

        try {
            Position spawnPosition = ModelGate.model.getBoard().getSpawnpointOfColor(cardChosen.getColor());
            out.println("<SERVER> Spawning player in position [" + spawnPosition.getX() + "][" + spawnPosition.getY() +"]");
            playerToSpawn.setPosition(spawnPosition);
        } catch (Exception e) {
            logger.severe("Exception Occured: "+e.getClass()+" "+e.getCause());
        }

        //discard the power up card
        out.println("<SERVER>Discarding the chosen power up: " + cardChosen.getID());
        playerToSpawn.getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEString.getInput()
        );

        if(!this.deadPlayers.isEmpty()) {
            out.println("<SERVER> Player spawned and removed from the list of dead players");
            this.deadPlayers.remove(0);
        }

        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState(this.deadPlayers));
        ViewControllerEventHandlerContext.state.doAction(null);

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        out.println("<SERVER> randomly making player spawn using first card in hand.");
        this.doAction(new ViewControllerEventString(playerToSpawn.getPowerUpCardsInHand().getCards().get(0).getID()));
    }
}
