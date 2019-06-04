package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;

public class TurnState implements State {

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

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
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk));
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

    @Override
    public void handleAFK() {
        this.playerToAsk.setIsAFK(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        ModelGate.model.getPlayerList().setNextPlayingPlayer();
        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
