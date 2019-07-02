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

    /**action 1 or action 2*/
    private int actionNumber;
    /**player to ask the input*/
    private Player playerToAsk;
    /**thread for the count down*/
    private Thread inputTimer;
    /**constructor
     * @param actionNumber represents if it's action 1 or action 2*/
    public TurnState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**asks the user an input, costumed on the basis of, if the bot is active, if they already used it and if they can use a power up card
     * @param playerToAsk represents the player the information is asked */
    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking the Player an input\"" + playerToAsk.getNickname() + "\"");

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            out.println("<SERVER> Can Use Power Up: " + canUsePowerUp(ModelGate.getModel().getCurrentPlayingPlayer()));
            if(ModelGate.getModel().isBotActive()) {
                out.println("<SERVER> Can Use Bot: " + ModelGate.getModel().getPlayerList().getPlayer("Terminator").isBotUsed());
                SelectorGate.getCorrectSelectorFor(playerToAsk).askTurnAction(this.actionNumber, canUsePowerUp(ModelGate.getModel().getCurrentPlayingPlayer()), !ModelGate.getModel().getPlayerList().getPlayer("Terminator").isBotUsed());
            }
            else{
                SelectorGate.getCorrectSelectorFor(playerToAsk).askTurnAction(this.actionNumber, canUsePowerUp(ModelGate.getModel().getCurrentPlayingPlayer()), false);
            }
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }


    /**@param viewControllerEvent represents the action the user choose to undertake,
     * the next state is based depending on this information*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer expired.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        String actionChosen = ((ViewControllerEventString)viewControllerEvent).getInput();
        out.println("<SERVER> Player's choice is : " + actionChosen);


        //set correct next state
        switch (actionChosen) {
            case "run around":
                ViewControllerEventHandlerContext.setNextState(new RunAroundState(this.actionNumber));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
            case "grab stuff":
                ViewControllerEventHandlerContext.setNextState(new GrabStuffState(this.actionNumber));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
            case "shoot people":
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleState(this.actionNumber));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
            case "use power up":
                ViewControllerEventHandlerContext.setNextState(new PowerUpState("movement", new TurnState(this.actionNumber)));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
            case "use Bot":
                ViewControllerEventHandlerContext.setNextState(new BotMoveState(new TurnState(this.actionNumber)));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
            default:
                this.askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
                break;
        }
    }

    /**if the user don't answer before the timer expires, they are set AFK and their turn ends*/
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }

    /**@param playerToAsk is the player the input is asked
     * this function verifies if the player can use any power up between the one he's holding in their hand
     * @return a boolean value that represents the answer*/
    public static boolean canUsePowerUp(Player playerToAsk){
        for (PowerUpCard pu: playerToAsk.getPowerUpCardsInHand().getCards()) {
            if(pu.getName().equalsIgnoreCase("teleporter") || pu.getName().equalsIgnoreCase("newton")){
                pu.getSpecialEffect().passContext(playerToAsk,ModelGate.getModel().getPlayerList(),ModelGate.getModel().getBoard());
                if(pu.isUsable()) {
                    return true;
                }
            }
        }
        return false;
    }
}
