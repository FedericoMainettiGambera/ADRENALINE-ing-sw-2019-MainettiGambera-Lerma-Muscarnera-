package it.polimi.se2019.controller.statePattern;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.OrderedCardList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.util.ArrayList;

public class ShootPeopleState implements State {

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public ShootPeopleState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //final frenzy hasnt begun and no adrenaline action available
        if(!ModelGate.model.hasFinalFrenzyBegun()&&!ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
             if(canShoot()){
                 ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
             else{
                 System.out.println("Player cant shoot");
                 ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
        }
       //FF aint begun & adrenaline action avaible
        else if(!ModelGate.model.hasFinalFrenzyBegun()&&ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
            if(canShoot()) {
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(), 1));
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Player cant shoot");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }

        }
        //FF began
        else if(ModelGate.model.hasFinalFrenzyBegun()){
            if(canShoot()) {
                int numberOfMoves = 1;
                if (playerToAsk.getBeforeorafterStartingPlayer() < 0) {
                    numberOfMoves = 1;
                } else if (playerToAsk.getBeforeorafterStartingPlayer() >= 0) {
                    numberOfMoves = 2;
                }
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askRunAroundPosition(ModelGate.model.getBoard().possiblePositions(playerToAsk.getPosition(), numberOfMoves));
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Player cant shoot");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
        }
    }


    @Override
    public void doAction(ViewControllerEvent VCE) {

        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        System.out.println("<SERVER> Setting player position to: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
        ModelGate.model.getPlayerList().getCurrentPlayingPlayer().setPosition(
                VCEPosition.getX(),
                VCEPosition.getY()
        );

        if(!ModelGate.model.hasFinalFrenzyBegun()&&ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
            ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(ModelGate.model.hasFinalFrenzyBegun()){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(true, this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
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

    public ArrayList<WeaponCard> UsableWep(){
        ArrayList<WeaponCard> UsableWep=new ArrayList<>();
        for (WeaponCard card:ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {
          if(card.isLoaded()){
              UsableWep.add(card);
          }
        }
        return UsableWep;
    }

    public boolean canShoot(){
        for (WeaponCard wp:ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {
            wp.passContext(ModelGate.model.getCurrentPlayingPlayer(), ModelGate.model.getPlayerList(), ModelGate.model.getBoard());
        }
        OrderedCardList<WeaponCard> possibleCards = ModelGate.model.getCurrentPlayingPlayer().getHand().usableWeapons();
        for (WeaponCard wp:possibleCards.getCards()) {
            if(!wp.isLoaded()){
                possibleCards.getCards().remove(wp);
            }
        }
        return possibleCards.getCards().size() > 0;
    }
}
