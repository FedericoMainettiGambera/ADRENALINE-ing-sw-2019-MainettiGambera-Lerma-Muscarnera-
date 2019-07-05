package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventBoolean;

import java.io.PrintWriter;
import java.util.logging.Logger;

/**called after ReloadState and before ScoreKillsState*/
public class WantToPlayPowerUpState  implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    /**the player input is asked*/
    private Player playerToAsk;
    /**timer for the count down till AFK status*/
    private Thread inputTimer;

    /**constructor */
     WantToPlayPowerUpState(){
        out.println("<SERVER> New state: " + this.getClass());
    }

    /**@param playerToAsk the player input is asked,
     * checks if the player can use a power up, in the case they can't
     * scoreKillsState is called (since this state is called when the player's turn is about to end),
     * if else, the player is asked if they actually want to use one*/
    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking Player the input \"" + playerToAsk.getNickname() + "\"");
        this.playerToAsk = playerToAsk;

        //check if player can use a power up (teleport or newton)
        if(TurnState.canUsePowerUp(playerToAsk)){
            //the player can play a power up, so we ask him if he wants to
            out.println("<SERVER> player can play a power up.");

            //ask if he wants to play power up or not
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askWantToUsePowerUpOrNot();
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask want to use power up"));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.severe("Exception Occurred: "+e.getClass()+" "+e.getCause());
            }
        }
        else{
            //if the player can't use a power up, keep the game continue with ScoreKillsState (that is the state after ReloadState)
            out.println("<SERVER> player can't play a power up");
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }

    /**@param viewControllerEvent the event to be parsed and cast to an answer for the previous ask for input request
     * if the boolean value the event is parsed to is true, then the user is send to the PowerUpState
     * if else, the ScoreKillsState will be called*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {

        this.inputTimer.interrupt();

        out.println("<SERVER> player has answered before the timer ended.");
        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //parse the VCE
        ViewControllerEventBoolean viewControllerEventBoolean = (ViewControllerEventBoolean)viewControllerEvent;
        if(viewControllerEventBoolean.getInput()){
            //if he wants to use a power up, send player to state powerUpState (after which should restart this state (to play more than 1 powerup)
            ViewControllerEventHandlerContext.setNextState(new PowerUpState("movement", this)); //i'm already sure he can play at least 1 power up (cheked in the askForInput)
            ViewControllerEventHandlerContext.getState().askForInput(playerToAsk);
        }
        else{
            //else send player to the ScoreKillsState
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }

    }

    /**if the player doesn't answer to the askForInput request before the timer expires,
     * they will be set AFK
     * */
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
}
