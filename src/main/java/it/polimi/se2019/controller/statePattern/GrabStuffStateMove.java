package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class GrabStuffStateMove implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffStateMove.class.getName());


    private int actionNumber;
    private int numberOfMovement;

    private Player playerToAsk;

    private Thread inputTimer;

    public GrabStuffStateMove(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.numberOfMovement = 1;
        if((ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineGrabAction())||(ModelGate.model.hasFinalFrenzyBegun()&&(playerToAsk.getBeforeorafterStartingPlayer()<0))){
            this.numberOfMovement = 2;
        }
        else if(ModelGate.model.hasFinalFrenzyBegun()&&playerToAsk.getBeforeorafterStartingPlayer()>=0){
            this.numberOfMovement=3;
        }

        out.println("<SERVER> The player can make " + this.numberOfMovement + " number of moves");

        ArrayList<Position> possiblePositions = ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),this.numberOfMovement);
        out.println("<SERVER> Possible positions to move before grabbing calculated:");
        StringBuilder toPrintln = new StringBuilder();
        for (Position possiblePosition : possiblePositions) {
            toPrintln.append("    [").append(possiblePosition.getX()).append("][").append(possiblePosition.getY()).append("]");
        }
        out.println(toPrintln);



        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffMove(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
           logger.severe("Exception Occured"+" "+e.getClass()+" "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        out.println("<SERVER> moving player to position: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
        ViewControllerEventHandlerContext.state.doAction(null);
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
}
