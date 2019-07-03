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

/**give the user the possibility to choose the effect of the weapon he chose that he wants to use*/
public class ShootPeopleChooseEffectState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleChooseEffectState.class.getName());
    /**the player to be asked an input*/
    private Player playerToAsk;
    /**count down till AFK status*/
    private Thread inputTimer;

    /**player choice of weapon to use*/
    private WeaponCard choosenWeaponCard;

    /**the possible effect to be used in a given context*/
    private ArrayList<Effect> possibleEffects;

    /**if it's 1st or 2nd action*/
    private int actionNumber;

    /**constructor,
     * @param choosenWeaponCard  sets attribute choosenWeaponCard
     * @param actionNumber sets attribute actionNumber*/
     ShootPeopleChooseEffectState(WeaponCard choosenWeaponCard, int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
        this.choosenWeaponCard = choosenWeaponCard;
        this.possibleEffects = new ArrayList<>();
    }

    /**@param playerToAsk the player to be asked the input
     * in this case the player has to choose between a list of effects*/
    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.possibleEffects = (ArrayList<Effect>)choosenWeaponCard.usableEffects();

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

    /**@param VCE the event to be parsed, containing the chosen effect
     * then the next state is set, it will be ShootPeopleAskForInputState
     * */
    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        Effect chosenEffect = this.possibleEffects.get(VCEInt.getInput());

        out.println("<SERVER> Player has chosen effect: " + chosenEffect.getEffectName());

        out.println("<SERVER> Paying for the effect cost");

        ViewControllerEventHandlerContext.setNextState(new ShootPeopleAskForInputState(chosenEffect, this.choosenWeaponCard, this.actionNumber));
        ViewControllerEventHandlerContext.getState().askForInput(playerToAsk);
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }
}
