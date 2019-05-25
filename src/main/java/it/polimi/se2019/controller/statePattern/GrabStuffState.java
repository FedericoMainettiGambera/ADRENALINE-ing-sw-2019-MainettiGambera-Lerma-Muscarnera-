package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;

public class GrabStuffState implements State {

    private int actionNumber;

    public GrabStuffState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
        if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
            SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorSocket.askGrabStuffAction();
        }
        else{
            SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorRMI.askGameSetUp();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;
        String choice = VCEString.getInput();

        System.out.println("<SERVER> Player's choice: " + choice);

        if(choice.equals("move")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateMove(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(choice.equals("grab")){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrab(this.actionNumber));
            ViewControllerEventHandlerContext.state.doAction(null);
        }
        else{
            this.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
