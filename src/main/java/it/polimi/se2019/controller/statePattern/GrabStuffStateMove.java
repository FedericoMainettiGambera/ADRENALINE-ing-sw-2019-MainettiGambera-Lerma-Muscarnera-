package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;

import java.util.ArrayList;

public class GrabStuffStateMove implements State {

    private int actionNumber;
    private int numberOfMovement;

    public GrabStuffStateMove(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.numberOfMovement = 1;
        if(ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineGrabAction()){
            this.numberOfMovement = 2;
        }

        System.out.println("<SERVER> The player can make " + this.numberOfMovement + " number of moves");

        ArrayList<Position> possiblePositions = ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(),this.numberOfMovement);
        System.out.println("<SERVER> Possible positions to move before grabbing calculated:");
        String toPrintln = "";
        for (int i = 0; i < possiblePositions.size() ; i++) {
            toPrintln += "    [" + possiblePositions.get(i).getX()+ "][" + possiblePositions.get(i).getY() + "]";
        }
        System.out.println(toPrintln);

        if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
            SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorSocket.askGrabStuffMove(possiblePositions);
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
        System.out.println("<SERVER> moving player to position: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
        ViewControllerEventHandlerContext.state.doAction(null);
    }
}
