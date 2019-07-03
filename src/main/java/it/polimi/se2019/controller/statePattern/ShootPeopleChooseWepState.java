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
import java.util.Iterator;
import java.util.logging.Logger;

/**allow users to shoot*/
public class ShootPeopleChooseWepState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleChooseWepState.class.getName());

    private Player playerToAsk;

    private Thread inputTimer;

    private ArrayList<WeaponCard> loadedCardInHand;

    private int actionNumber;

    /**@param actionNumber needed to set the following state*/
    public ShootPeopleChooseWepState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        for (WeaponCard wp:ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {
            wp.passContext(ModelGate.getModel().getCurrentPlayingPlayer(), ModelGate.getModel().getPlayerList(), ModelGate.getModel().getBoard());
        }
        OrderedCardList<WeaponCard> possibleCards = ModelGate.getModel().getCurrentPlayingPlayer().getHand().usableWeapons();

        possibleCards.getCards().removeIf(element -> !element.isLoaded());

        this.loadedCardInHand = (ArrayList)possibleCards.getCards();

        if(!possibleCards.getCards().isEmpty()) {
            //ask input
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatWep(loadedCardInHand);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.severe("Exception Occured" + e.getClass() + e.getCause());
            }
        }
        else{
            //TODO check if this works:
            out.println("<SERVER> ----------------------------------------------------------------------------------------------------|");
            out.println("|         WARNING: This text should be displayed only if the player doesn't have any loaded playable weapon  |\n" +
                        "|                  and the FinalFrenzyHasBegun.                                                              |\n" +
                        "|                  To be noticed is the fact that the player could have shoot somebody if would have reloaded|\n" +
                        "|                  the correct weapon.                                                                       |\n" +
                        "|                  Anyway the player decided to reload an unusable weapon and so he now can't shoot.         |");
            out.println("|                  I'll let the user have this possibility of moving, reloading and than not shooting,       |\n" +
                        "|                  because it can led to some very special tactics in the game. N.B. this is not a mistake.  |");
            out.println("|         End warning message. FedericoMainettiGambera.                                                      |");
            out.println("|         This is a very hard scenario to replicate, so please, if you encounter this message, check if      |\n" +
                        "|         everything is correct.                                                                             |");
            out.println("<SERVER> ----------------------------------------------------------------------------------------------------|");
            //set next state
            State nextState = null;
            if(this.actionNumber == 2){
                nextState = (new ReloadState(false));
            }
            else if(this.actionNumber == 1){
                nextState = (new TurnState(2));
            }
            ViewControllerEventHandlerContext.setNextState(nextState);
            ViewControllerEventHandlerContext.getState().askForInput(playerToAsk);
        }
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent){
        while(this.inputTimer.isAlive()){
            this.inputTimer.interrupt();
        }
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventInt viewControllerEventInt = (ViewControllerEventInt)viewControllerEvent;

        out.println("<SERVER> player has chosen card with ID : " + this.loadedCardInHand.get(viewControllerEventInt.getInput()).getID());

        //next state
        ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseEffectState(playerToAsk.getWeaponCardsInHand().getCard(this.loadedCardInHand.get(viewControllerEventInt.getInput()).getID()), this.actionNumber));
        ViewControllerEventHandlerContext.getState().askForInput(this.playerToAsk);

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }
}
