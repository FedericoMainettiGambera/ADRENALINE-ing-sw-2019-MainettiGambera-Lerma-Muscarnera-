package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * allows player to move around the map
 * */
public class RunAroundState implements State{
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(RunAroundState.class.getName());

    /**if it's action 1st or 2nd*/
    private int actionNumber;
    /**the player to be asked the input*/
    private Player playerToAsk;

    /**count down till AFK*/
    private Thread inputTimer;

    /**constructor,
     * @param actionNumber to set action number attribute*/
    public RunAroundState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**
     * @param playerToAsk is the current playing player
     *  this function send to the playerToAsk all the possible positions they can move to
     *  within a certain number of moves
     * */
    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        int numberOfMoves;

        if(ModelGate.getModel().hasFinalFrenzyBegun()&&playerToAsk.getBeforeorafterStartingPlayer()<0){numberOfMoves=4;}
        else{numberOfMoves=3;}

        ArrayList<Position> possiblePositions = ModelGate.getModel().getBoard().possiblePositions(playerToAsk.getPosition(), numberOfMoves);
        out.println("<SERVER> Possible positions to move calculated:");
        StringBuilder toPrintln = new StringBuilder();
        for (Position possiblePosition : possiblePositions) {
            toPrintln.append("[").append(possiblePosition.getX()).append("][").append(possiblePosition.getY()).append("]    ");
        }
        out.println("    " + toPrintln);

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askRunAroundPosition(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.severe("Exception occurred  "+e.getClass()+"  "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }

    /**@param vce from which needed information is extrapolated
     * this doAction moves the player in the board to the desired position
     * */
    @Override
    public void doAction(ViewControllerEvent vce) {
        this.inputTimer.interrupt();

        //set new position for the player
        handleVce(vce);

        //set next State
        if(this.actionNumber == 1){ //first action of the turn
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        else if(this.actionNumber == 2){ //second action of the turn
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
        }
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
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

    /**@param vce, where the state extract the information to know where  player is going to be placed*/
    public void handleVce(ViewControllerEvent vce){

        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition viewControllerEventPosition = (ViewControllerEventPosition)vce;

        //set new position for the player
        Position newPosition=new Position(viewControllerEventPosition.getX(), viewControllerEventPosition.getY());
        out.println("<SERVER> Setting player position to: [" +newPosition.getX()+ "][" +newPosition.getY() + "]");
        ModelGate.getModel().getPlayerList().getCurrentPlayingPlayer().setPosition(newPosition);

    }

}
