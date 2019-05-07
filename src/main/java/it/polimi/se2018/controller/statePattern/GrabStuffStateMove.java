package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPosition;

public class GrabStuffStateMove implements State {

    private int actionNumber;
    private int numberOfMove;

    public GrabStuffStateMove(int actionNumber, int numberOfMove){
        this.actionNumber = actionNumber;
        this.numberOfMove = numberOfMove;
    }
    @Override
    public void askForInput(Player playerToAsk) {
        //ask input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
    }
}
