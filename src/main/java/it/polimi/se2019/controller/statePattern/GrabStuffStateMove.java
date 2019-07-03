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
import java.util.List;
import java.util.logging.Logger;

/**this class allows the user to move somewhere else to grab*/
public class GrabStuffStateMove implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffStateMove.class.getName());

    /**indicates if it's action number 1 or number 2*/
    private int actionNumber;

    /**the player to ask the input to*/
    private Player playerToAsk;

    /** the thread that starts the count down to AFK status*/
    private Thread inputTimer;

    /**constructor,
     * @param actionNumber indicates if it's being performed 1st or 2nd action
     * */
    public GrabStuffStateMove(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**@param playerToAsk indicates current playing player
     * user must tell the function where they wants to be moved
     * (within limits determined by many factors apart of his will)
     * */
    @Override
    public void askForInput(Player playerToAsk){

        List<Position> possiblePositions=calculateAndPrintPossiblePosition(playerToAsk);

        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffMove(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
           logger.severe("Exception Occurred"+" "+e.getClass()+" "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }


    /**@param playerToAsk is the player to ask the input to,
     *                     after having calculated all the possible positions
     * @return a List<Position> of them all*/
    public List<Position> calculateAndPrintPossiblePosition(Player playerToAsk){

        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        int numberOfMovement = 1;
        if((ModelGate.getModel().getCurrentPlayingPlayer().hasAdrenalineGrabAction())||(ModelGate.getModel().hasFinalFrenzyBegun()&&(playerToAsk.getBeforeorafterStartingPlayer()<0))){
            numberOfMovement = 2;
        }
        else if(ModelGate.getModel().hasFinalFrenzyBegun()&&playerToAsk.getBeforeorafterStartingPlayer()>=0){
            numberOfMovement =3;
        }

        out.println("<SERVER> The player can make " + numberOfMovement + " number of moves");

        ArrayList<Position> possiblePositions = ModelGate.getModel().getBoard().possiblePositions(playerToAsk.getPosition(), numberOfMovement);
        out.println("<SERVER> Possible positions to move before grabbing calculated:");
        StringBuilder toPrintln = new StringBuilder();
        for (Position possiblePosition : possiblePositions) {
            toPrintln.append("    [").append(possiblePosition.getX()).append("][").append(possiblePosition.getY()).append("]");
        }
        out.println(toPrintln);

        return possiblePositions;
    }


    /**the player is moved in the desired position
     * @param viewControllerEvent contains the user's choice
     * we extrapolate the square where to move the player from it
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();

        parsevce(viewControllerEvent);

        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
        ViewControllerEventHandlerContext.getState().doAction(null);
    }

    /**parce the view controller event to get the needed information on where to set the player position
     * @param viewControllerEvent as said */
    public void parsevce(ViewControllerEvent viewControllerEvent){
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition viewControllerEventPosition = (ViewControllerEventPosition)viewControllerEvent;

        //set new position for the player
        out.println("<SERVER> moving player to position: [" +viewControllerEventPosition.getX()+ "][" +viewControllerEventPosition.getY() + "]");
        ModelGate.getModel().getPlayerList().getCurrentPlayingPlayer().setPosition(
                viewControllerEventPosition.getX(),
                viewControllerEventPosition.getY()
        );

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
