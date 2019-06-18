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

import java.util.ArrayList;

public class GrabStuffStateMove implements State {

    private int actionNumber;
    private int numberOfMovement;

    private Player playerToAsk;

    private Thread inputTimer;

    public GrabStuffStateMove(int actionNumber){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.numberOfMovement = 1;
        if((ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineGrabAction())||(ModelGate.model.hasFinalFrenzyBegun()&&(playerToAsk.getBeforeorafterStartingPlayer()<0))){
            this.numberOfMovement = 2;
        }
        else if(ModelGate.model.hasFinalFrenzyBegun()&&playerToAsk.getBeforeorafterStartingPlayer()>=0){
            this.numberOfMovement=3;
        }

        System.out.println("<SERVER> The player can make " + this.numberOfMovement + " number of moves");

        ArrayList<Position> possiblePositions = ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),this.numberOfMovement);
        System.out.println("<SERVER> Possible positions to move before grabbing calculated:");
        String toPrintln = "";
        for (int i = 0; i < possiblePositions.size() ; i++) {
            toPrintln += "    [" + possiblePositions.get(i).getX()+ "][" + possiblePositions.get(i).getY() + "]";
        }
        System.out.println(toPrintln);



        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffMove(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        System.out.println("<SERVER> moving player to position: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
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
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }
}
