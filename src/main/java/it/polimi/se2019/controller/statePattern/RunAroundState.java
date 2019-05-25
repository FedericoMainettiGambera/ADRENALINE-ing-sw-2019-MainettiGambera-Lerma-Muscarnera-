package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;

import java.util.ArrayList;

public class RunAroundState implements State {

    private int actionNumber;

    public RunAroundState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        ArrayList<Position> possiblePositions = ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(), 3);
        System.out.println("<SERVER> Possible positions to move calculated:");
        String toPrintln = "";
        for (int i = 0; i < possiblePositions.size() ; i++) {
            toPrintln += "[" + possiblePositions.get(i).getX()+ "][" + possiblePositions.get(i).getY() + "]    ";
        }
        System.out.println("    " + toPrintln);

        //ask for input
        if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
            SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorSocket.askRunAroundPosition(possiblePositions);
        }
        else{
            SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorRMI.askGameSetUp();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        System.out.println("<SERVER> Setting player position to: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        //set next State
        if(this.actionNumber == 1){ //first action of the turn
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        else if(this.actionNumber == 2){ //second action of the turn
            ViewControllerEventHandlerContext.setNextState(new ReloadState());
        }
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

}
