package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPosition;

public class RunAroundState implements State {

    private int actionNumber;

    public RunAroundState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
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
