package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;

public class TurnState implements State {

    private int actionNumber;

    public TurnState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        String actionChosen = ((ViewControllerEventString)VCE).getInput();
        //set correct next state
        if(actionChosen.equals("run around")){
            ViewControllerEventHandlerContext.setNextState(new RunAroundState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(actionChosen.equals("grab stuff")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(actionChosen.equals("shoot people")){
            ViewControllerEventHandlerContext.setNextState(new ShootPeopleState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            this.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
