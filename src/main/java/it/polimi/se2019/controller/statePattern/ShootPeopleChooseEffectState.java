package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ShootPeopleChooseEffectState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleChooseEffectState.class.getName());
    private Player playerToAsk;

    private Thread inputTimer;

    private WeaponCard choosenWeaponCard;

    private ArrayList<Effect> possibleEffects;

    private Effect chosenEffect;

    private int actionNumber;

    public ShootPeopleChooseEffectState(WeaponCard choosenWeaponCard, int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
        this.choosenWeaponCard = choosenWeaponCard;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.possibleEffects = (ArrayList)choosenWeaponCard.usableEffects();

        //ask input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatEffect(this.possibleEffects);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
           logger.severe("Exception Occured: "+e.getClass()+" "+e.getCause());
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        this.chosenEffect = this.possibleEffects.get(VCEInt.getInput());

        out.println("<SERVER> Player has chosen effect: " + this.chosenEffect.getEffectName());

        ViewControllerEventHandlerContext.setNextState(new ShootPeopleAskForInputState(this.chosenEffect, this.actionNumber));
        ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
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
}
