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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ShootPeopleChooseWepState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleChooseWepState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    private ArrayList<WeaponCard> loadedCardInHand;

    private int actionNumber;

    public ShootPeopleChooseWepState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

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
           logger.severe("Exception Occured"+e.getClass()+e.getCause());
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        while(this.inputTimer.isAlive()){
            this.inputTimer.interrupt();
        }
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt VCEInt = (ViewControllerEventInt)VCE;

        out.println("<SERVER> player has chosen card with ID : " + this.loadedCardInHand.get(VCEInt.getInput()).getID());

        //next state
        ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseEffectState(playerToAsk.getWeaponCardsInHand().getCard(this.loadedCardInHand.get(VCEInt.getInput()).getID()), this.actionNumber));
        ViewControllerEventHandlerContext.state.askForInput(this.playerToAsk);

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }
}
