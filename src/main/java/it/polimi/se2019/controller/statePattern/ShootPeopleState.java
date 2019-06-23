package it.polimi.se2019.controller.statePattern;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.OrderedCardList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

public class ShootPeopleState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleState.class.getName());

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public ShootPeopleState(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //final frenzy hasnt begun and no adrenaline action available
        if(!ModelGate.model.hasFinalFrenzyBegun()&&!ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
             if(canShoot()){
                 ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
             else{
                 out.println("Player cant shoot");
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
                    logger.severe("Exception Occurred: "+e.getClass()+" "+e.getCause());
                }
            }
            else{
                out.println("Player cant shoot");
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
                out.println("Player cant shoot");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
        }
    }


    @Override
    public void doAction(ViewControllerEvent VCE) {

        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventPosition VCEPosition = (ViewControllerEventPosition)VCE;

        //set new position for the player
        out.println("<SERVER> Setting player position to: [" +VCEPosition.getX()+ "][" +VCEPosition.getY() + "]");
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
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
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
        Iterator<WeaponCard> elementListIterator = possibleCards.getCards().iterator();
        while (elementListIterator.hasNext()) {
            WeaponCard element = elementListIterator.next();
            if(!element.isLoaded()) {
                elementListIterator.remove();
            }
        }
        return possibleCards.getCards().size() > 0;
    }
}
