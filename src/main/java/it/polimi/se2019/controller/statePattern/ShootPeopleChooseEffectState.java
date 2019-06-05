package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;

import java.util.ArrayList;

public class ShootPeopleChooseEffectState implements State{
    private Player playerToAsk;

    private Thread inputTimer;

    private WeaponCard choosenWeaponCard;

    private ArrayList<Effect> possibleEffects;

    private Effect chosenEffect;

    public ShootPeopleChooseEffectState(WeaponCard choosenWeaponCard){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.choosenWeaponCard = choosenWeaponCard;
        this.possibleEffects = new ArrayList<>();
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        for (Effect e: this.choosenWeaponCard.getEffects()) {
            this.possibleEffects.add(e);
        }

        //ask input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatEffect(this.possibleEffects);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        this.chosenEffect = this.possibleEffects.get(VCEInt.getInput());

        ViewControllerEventHandlerContext.setNextState(new ShootPeopleAskForInputState(this.chosenEffect));
        ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
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
