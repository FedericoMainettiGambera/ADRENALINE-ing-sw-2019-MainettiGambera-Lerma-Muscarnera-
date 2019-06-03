package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.controller.WaitForPlayerInput;

public class GrabStuffState implements State {

    private int actionNumber;

    private Player playerToAsk;

    public GrabStuffState(int actionNumber){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffAction();
            Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk));
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        this.playerToAsk.menageAFKAndInputs();
        if(playerToAsk.isAFK()){
            //set next State
            System.out.println("<SERVER> " + playerToAsk.getNickname() + " is AFK, he'll pass the turn.");
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
            return;
        }

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
