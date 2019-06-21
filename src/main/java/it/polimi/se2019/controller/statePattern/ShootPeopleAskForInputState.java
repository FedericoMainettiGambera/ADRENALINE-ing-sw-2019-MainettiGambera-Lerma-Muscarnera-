package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.SquareV;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShootPeopleAskForInputState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleAskForInputState.class.getName());
    private Player playerToAsk;

    private Thread inputTimer;

    private Effect chosenEffect;

    private int actionNumber;

    private WeaponCard chosenWeaponCard;


    public ShootPeopleAskForInputState(Effect chosenEffect, WeaponCard chosenWeaponCard, int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
        this.chosenEffect = chosenEffect;
        this.chosenWeaponCard = chosenWeaponCard;
    }


    private Integer inputRequestCounterF = 0;
    public boolean canIncrementRequest(){
        if(inputRequestCounterF < this.chosenEffect.requestedInputs().size()-1 ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        EffectInfoType inputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF).getEffectInfoTypelist();

        if(isToSend(inputType)) {
            try {
                out.println("<SERVER> sending " + inputType + " to player with the possible options.");

                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs(inputType, this.chosenEffect.usableInputs().get(inputRequestCounterF).get(0));
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }
        }
        else{
            out.println("<SERVER> " + inputType + " is not meant to be sent to player.");
            askMoreOrExec();
        }
    }



    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> " + this.getClass() + ".doAction();");

        List<Object> response = ((ViewControllerEventListOfObject)VCE).getAnswer();

        Object[] inputRow = new Object[10];

        int inputRowCurrent = 0;
        for(Object o: response) {
            if(o.getClass().toString().contains("PlayerV")){
                inputRow[inputRowCurrent] = ModelGate.model.getPlayerList().getPlayer(((PlayerV)o).getNickname());
            }
            else{
                inputRow[inputRowCurrent] = ModelGate.model.getBoard().getMap()[((SquareV)o).getX()][((SquareV)o).getY()];
            }
            // <L>
            System.out.println("<SERVER> content of input: " + inputRow[inputRowCurrent]);
            // </L>
            inputRowCurrent++;
        }

        this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);

        askMoreOrExec();
    }

    public void askMoreOrExec(){
        if(canIncrementRequest()) {
            inputRequestCounterF++;
            askForInput(playerToAsk);
        }
        else {
            ChooseHowToPayState.makePayment(playerToAsk, this.chosenEffect.getUsageCost());
        }
    }
    public void afterPayment(){

        this.chosenWeaponCard.unload(); //TODO not sure about this, ask luca

        this.chosenEffect.Exec();

        if(this.actionNumber == 2){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
        }
        else if(this.actionNumber == 1){
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }


    @Override
    public void handleAFK(){
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
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
            this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);
            return false;
        }
        return true;
    }
}

