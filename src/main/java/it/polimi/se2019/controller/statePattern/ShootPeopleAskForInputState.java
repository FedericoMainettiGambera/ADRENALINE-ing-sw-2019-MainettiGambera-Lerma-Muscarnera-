package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

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
        if(this.chosenEffect.requestedInputs().get(inputRequestCounter) != null)
            inputRequestCounter++;
        else
        return null;

        return (inputRequestCounter-1);
    }


    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask input

            /*************************************************/
        int counter = 0;

        EffectInfoType inputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()).getEffectInfoTypelist();
            /*fai vedere all'utente cosa inserire*/
            System.out.println("inserisci un " + inputType.toString());
            List<Object> usables =  this.chosenEffect.usableInputs().get(getInputRequestCounter()).get(0);
            /*scegli tra quelli sopra*/

            //   invia all'utente le possibilità

            //   quando l'utente manda una risposta parte la do action


            /*************************************************/

        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
           // SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs();



            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> " + this.getClass() + ".doAction();");
        List<Object> response = (List<Object>) VCE;
        EffectInfoType currentInputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()).getEffectInfoTypelist();
        Object[] inputRow = new Object[10];
        int inputRowCurrent = 0;
        for(Object o: response) {
            inputRow[inputRowCurrent] = o;
            inputRowCurrent++;
        }
        this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(getInputRequestCounter()),inputRow);
        nextInputRequestex();
        if(getInputRequestCounter() == null) { // non ci sono più input
        //
            if(!this.chosenEffect.Exec()) {
                System.out.println("<SERVER> exec didn't work");
            } else {
                System.out.println("<SERVER> exec worked!");
                // vai avanti col gioco

            }
        }
        else {
            askForInput(playerToAsk);
        }

    }


    @Override
    public void handleAFK() {
        this.playerToAsk.setIsAFK(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
        ViewControllerEventHandlerContext.state.doAction(null);
    }
}

