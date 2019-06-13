package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.OrderedCardList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;

import java.util.ArrayList;

public class ShootPeopleChooseWepState implements State {

    private Player playerToAsk;

    private Thread inputTimer;

    private ArrayList<WeaponCard> loadedCardInHand;

    private int actionNumber;

    public ShootPeopleChooseWepState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        for (WeaponCard wp:ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {
            wp.passContext(ModelGate.model.getCurrentPlayingPlayer(), ModelGate.model.getPlayerList(), ModelGate.model.getBoard());
        }
        OrderedCardList<WeaponCard> possibleCards = ModelGate.model.getCurrentPlayingPlayer().getHand().usableWeapons();
        for (WeaponCard wp:possibleCards.getCards()) {
            if(!wp.isLoaded()){
                possibleCards.getCards().remove(wp);
            }
        }

        this.loadedCardInHand = (ArrayList)possibleCards.getCards();

        //ask input
        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatWep(loadedCardInHand);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        while(this.inputTimer.isAlive()){
            this.inputTimer.interrupt();
        }
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        System.out.println("<SERVER> player has chosen card with ID : " + this.loadedCardInHand.get(VCEInt.getInput()).getID());

        //next state
        ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseEffectState(playerToAsk.getWeaponCardsInHand().getCard(this.loadedCardInHand.get(VCEInt.getInput()).getID()), this.actionNumber));
        ViewControllerEventHandlerContext.state.askForInput(this.playerToAsk);

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
        ViewControllerEventHandlerContext.state.doAction(null);
    }
}
