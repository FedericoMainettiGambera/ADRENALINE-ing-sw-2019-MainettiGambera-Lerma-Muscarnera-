package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.virtualView.WaitForPlayerInput;

public class TurnState implements State {

    private int actionNumber;

    private Player playerToAsk;

    public TurnState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask for input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askTurnAction(this.actionNumber);
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

        String actionChosen = ((ViewControllerEventString)VCE).getInput();
        System.out.println("<SERVER> Player's choice is : " + actionChosen);


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
