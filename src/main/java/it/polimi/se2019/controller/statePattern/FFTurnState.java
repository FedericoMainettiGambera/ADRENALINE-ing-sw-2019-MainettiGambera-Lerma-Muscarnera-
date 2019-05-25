package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;

public class FFTurnState implements State {

    private int actionNumber;

    public FFTurnState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        String actionChosen = ((ViewControllerEventString)VCE).getInput();

        /*
        //set correct next state
        if(actionChosen.equals("run around")){
            ViewControllerEventHandlerContext.setNextState(new FFRunAroundState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(actionChosen.equals("grab stuff")){
            ViewControllerEventHandlerContext.setNextState(new FFGrabStuffState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(actionChosen.equals("shoot people")){
            ViewControllerEventHandlerContext.setNextState(new FFShootPeopleState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            this.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        */
    }
}
