package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * this state allows the bot to be moved
 * */
public class BotMoveState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    /**the player to ask the input to*/
    private Player playerToAsk;

    /**the timer for the count down to AFK status*/
    private Thread inputTimer;

    /**the following state to be set*/
    private State nextState;

    /**a list of possible position the bot can be moved*/
    private List<Position> possiblePositions = new ArrayList<>();

    /**the name of the bot*/
    private String botNickname="Terminator";

    /**constructor
     * @param nextState contains the data to initialize nextState attribute
     * */
    public BotMoveState(State nextState){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = nextState;
    }



    /**
     * @param playerToAsk we need to know who ask where the bot need to be moved
     * this function collects the information needed to place the bot somewhere else
     * */
    @Override
    public void askForInput(Player playerToAsk){

        calculatePossiblePositions(playerToAsk);

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askBotMove(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask bot move"));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception occurred  "+e.getClass()+"  "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }

    /**calculates the possible position where the bot can be moved
     * @param playerToAsk is set here*/
    private void calculatePossiblePositions(Player playerToAsk){


        out.println("<SERVER> setting bot used...");
        ModelGate.getModel().getPlayerList().getPlayer(botNickname).setBotUsed(true);

        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //calculate possible positions
        Position botposition = ModelGate.getModel().getPlayerList().getPlayer(botNickname).getPosition();
        this.possiblePositions = ModelGate.getModel().getBoard().possiblePositions(botposition,1);
        out.println("<SERVER> Possible positions to move Bot calculated:");
        StringBuilder toPrintln = new StringBuilder();
        for (Position possiblePosition : possiblePositions) {
            toPrintln.append("[").append(possiblePosition.getX()).append("][").append(possiblePosition.getY()).append("]    ");
        }
        out.println("    " + toPrintln);


    }
    /**
     * @param vce a view controller event is needed to get the correct information about where to place the bot
     * this function move the bot in the position indicated by the player who owns it during the turn
     * */
    @Override
    public void doAction(ViewControllerEvent vce) {
        this.inputTimer.interrupt();

        parseVce(vce);

        //change state in botShootState passing him the next state
        ViewControllerEventHandlerContext.setNextState(new BotShootState(this.nextState));
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
    }

    /**@param vce, event that we need to extrapolate information from */
    private void parseVce(ViewControllerEvent vce){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        //parse VCE
        ViewControllerEventPosition vcePosition = (ViewControllerEventPosition)vce;
        ModelGate.getModel().getPlayerList().getPlayer(botNickname).setPosition(vcePosition.getX(), vcePosition.getY());
        out.println("<SERVER> Bot's new position is: [" + vcePosition.getX() + "][" + vcePosition.getY() + "]");


    }


    /**
     * set the player AFK in case they don't send required input in a while
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
