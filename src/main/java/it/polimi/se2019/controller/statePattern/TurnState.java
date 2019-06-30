package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurnState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public TurnState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            out.println("<SERVER> Can Use Power Up: " + canUsePowerUp(ModelGate.model.getCurrentPlayingPlayer()));
            if(ModelGate.model.isBotActive()) {
                out.println("<SERVER> Can Use Bot: " + ModelGate.model.getPlayerList().getPlayer("Terminator").isBotUsed());
                SelectorGate.getCorrectSelectorFor(playerToAsk).askTurnAction(this.actionNumber, canUsePowerUp(ModelGate.model.getCurrentPlayingPlayer()), !ModelGate.model.getPlayerList().getPlayer("Terminator").isBotUsed());
            }
            else{
                SelectorGate.getCorrectSelectorFor(playerToAsk).askTurnAction(this.actionNumber, canUsePowerUp(ModelGate.model.getCurrentPlayingPlayer()), false);
            }
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        String actionChosen = ((ViewControllerEventString)viewControllerEvent).getInput();
        out.println("<SERVER> Player's choice is : " + actionChosen);


        //set correct next state
        switch (actionChosen) {
            case "run around":
                ViewControllerEventHandlerContext.setNextState(new RunAroundState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
            case "grab stuff":
                ViewControllerEventHandlerContext.setNextState(new GrabStuffState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
            case "shoot people":
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
            case "use power up":
                ViewControllerEventHandlerContext.setNextState(new PowerUpState("movement", new TurnState(this.actionNumber)));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
            case "use Bot":
                ViewControllerEventHandlerContext.setNextState(new BotMoveState(new TurnState(this.actionNumber)));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
            default:
                this.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                break;
        }
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public static boolean canUsePowerUp(Player playerToAsk){
        for (PowerUpCard pu: playerToAsk.getPowerUpCardsInHand().getCards()) {
            if(pu.getName().toLowerCase().equals("teleporter") || pu.getName().toLowerCase().equals("newton")){
                return true;
            }
        }
        return false;
    }
}
