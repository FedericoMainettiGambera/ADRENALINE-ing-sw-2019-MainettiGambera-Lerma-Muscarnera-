package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;

import java.util.List;

public class ShootPeopleAskForInputState implements State {
    private Player playerToAsk;

    private Thread inputTimer;

    private Effect chosenEffect;

    private int actionNumber;


    public ShootPeopleAskForInputState(Effect chosenEffect, int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.inputRequestCounter = new Integer(0);
        this.actionNumber = actionNumber;
        this.chosenEffect = chosenEffect;
    }

    public Integer getInputRequestCounter() {
        return inputRequestCounter;
    }

    private Integer inputRequestCounter;
    private Integer nextInputRequestex() {
        if(this.chosenEffect.requestedInputs().get(inputRequestCounter) != null) {
            inputRequestCounter++;
        }
        else {
            return null;
        }

        return (inputRequestCounter-1);
    }


    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        int counter = 0;

        EffectInfoType inputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()).getEffectInfoTypelist();

        if(isToSend(inputType)) {
            try {
                System.out.println("<SERVER> sending " + inputType + " to player with the possible options.");

                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs(inputType, this.chosenEffect.usableInputs().get(getInputRequestCounter()).get(0));
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("<SERVER> " + inputType + " is not meant to be sent to player.");
            askMoreOrExec();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> " + this.getClass() + ".doAction();");

        List<Object> response = ((ViewControllerEventListOfObject)VCE).getAnswer();

        EffectInfoType currentInputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()).getEffectInfoTypelist();

        Object[] inputRow = new Object[10];

        int inputRowCurrent = 0;
        for(Object o: response) {
            inputRow[inputRowCurrent] = o;
            inputRowCurrent++;
        }

        this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()),inputRow);

        askMoreOrExec();
    }

    public void askMoreOrExec(){
        nextInputRequestex();
        if(getInputRequestCounter() == null) {
            if(!this.chosenEffect.Exec()) {
                System.err.println("<SERVER> exec didn't work");
            } else {
                System.out.println("<SERVER> exec worked!");
                //TODO chiedi a luca se c'è bisogno di scaricare l arma o lo fa già l'exec().
                if(this.actionNumber == 2){
                    ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
                }
                else if(this.actionNumber == 1){
                    ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                }
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
        else {
            askForInput(playerToAsk);
        }
    }


    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }


    public boolean isToSend(EffectInfoType infoType){
        if(infoType.equals(EffectInfoType.player) ||
                infoType.equals(EffectInfoType.playerSquare)||
                infoType.equals(EffectInfoType.targetListBySameSquareOfPlayer)||
                infoType.equals(EffectInfoType.singleRoom)||
                infoType.equals(EffectInfoType.squareOfLastTargetSelected)||
                infoType.equals(EffectInfoType.targetBySameSquareOfPlayer)||
                infoType.equals(EffectInfoType.targetListByLastTargetSelectedSquare)
        ){
            Object[] inputRow = new Object[10];
            this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()),inputRow);
            return false;
        }
        return true;
    }
}

