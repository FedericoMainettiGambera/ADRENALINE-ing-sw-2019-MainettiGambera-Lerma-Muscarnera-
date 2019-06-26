package it.polimi.se2019.controller.statePattern;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.OrderedCardList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
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
        //final frenzy hasn't begun and no adrenaline action available
        if(!ModelGate.model.hasFinalFrenzyBegun()&&!ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
             if(canShoot(playerToAsk)){
                 ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
             else{
                 out.println("<SERVER> Player cant shoot");
                 ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                 ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
             }
        }
       //FF aint begun & adrenaline action avaible
        else if(!ModelGate.model.hasFinalFrenzyBegun()&&ModelGate.model.getCurrentPlayingPlayer().hasAdrenalineShootAction()){
            ArrayList<Position> possiblePositions = this.possiblePositionsToShootFrom(1, playerToAsk);
            if(possiblePositions.isEmpty()){
                out.println("<SERVER> there are no possible positions to move where the player can shoot from");
                //asking a new turn state action
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
            else {
                askWhereToMove(possiblePositions);
            }
        }
        //FF began
        else if(ModelGate.model.hasFinalFrenzyBegun()){
            int numberOfMoves = 1;
            if (playerToAsk.getBeforeorafterStartingPlayer() < 0) {
                numberOfMoves = 1;
            } else if (playerToAsk.getBeforeorafterStartingPlayer() >= 0) {
                numberOfMoves = 2;
            }
            ArrayList<Position> possiblePositions = this.possiblePositionsToShootFromConsideringReloadableWeapons(1, playerToAsk);
            if(possiblePositions.isEmpty()){
                out.println("<SERVER> there are no possible positions to move where the player can shoot from, considering even reloadable weapons.");
                //asking a new action to take
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
            else {
                askWhereToMove(possiblePositions);
            }
        }
    }

    private void askWhereToMove(ArrayList<Position> possiblePositions){
        try {
            out.println("<SERVER> the player can move and shoot from another position");
            //asking where to move
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
            SelectorGate.getCorrectSelectorFor(playerToAsk).askRunAroundPosition(possiblePositions);
            this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
            this.inputTimer.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
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

    private ArrayList<Position> possiblePositionsToShootFrom(int numberOfMoves, Player player){
        //all possible positions where the player can move
        ArrayList<Position> possiblePositions = ModelGate.model.getBoard().possiblePositions(player.getPosition(), numberOfMoves);

        //filtering all the possible position: keeping only the ones from where the player can shoot
        Iterator<Position> possiblePositionsIterator = possiblePositions.iterator();
        while (possiblePositionsIterator.hasNext()){
            Position currentPossiblePositionElement = possiblePositionsIterator.next();

            out.println("<SERVER> checking if player can shoot from position " + currentPossiblePositionElement.humanString());

            if(!canShootFrom(currentPossiblePositionElement, player)){
                possiblePositionsIterator.remove();
                out.println("         no, he can't!");
            }
            else{
                out.println("         yes, he can!");
            }
        }

        return possiblePositions;
    }

    private ArrayList<Position> possiblePositionsToShootFromConsideringReloadableWeapons(int numberOfMoves, Player player){
        ArrayList<WeaponCard> loadableWeapons = new ArrayList<>();

        //finds all unloaded weapons
        for (WeaponCard wp: player.getWeaponCardsInHand().getCards()) {
            if(!wp.isLoaded()){
                loadableWeapons.add(wp);
            }
        }

        //filters the unloaded weapons with only the ones that are loadable
        Iterator<WeaponCard> loadableWeaponsIterator = loadableWeapons.iterator();
        while(loadableWeaponsIterator.hasNext()){
            WeaponCard weaponCardElement = loadableWeaponsIterator.next();
            if(!(new ChooseHowToPayState(player, weaponCardElement.getReloadCost())).canPayInSomeWay()){
                loadableWeaponsIterator.remove();
            }
        }

        //now we have the possible loadable weapons and we load them for a moment
        for (WeaponCard wp: loadableWeapons){
            wp.reloadWithoutNotify();
        }

        //we now search for possiblePosition in the usual way
        ArrayList<Position> possiblePositions = possiblePositionsToShootFrom(numberOfMoves, player);

        //we unload the previosly loaded weapons
        for (WeaponCard wp: loadableWeapons){
            wp.unloadWithoutNotify();
        }

        return possiblePositions;
    }

    private boolean canShootFrom(Position pos, Player shootingPlayer){
        //old position of the player saved to restore
        Position oldPosition = shootingPlayer.getPosition();

        //changing the players position to the new we want to calculate
        shootingPlayer.setPositionWithoutNotify(pos);

        //checks if the player can shoot
        boolean canShoot = this.canShoot(shootingPlayer);

        //restore old position
        shootingPlayer.setPositionWithoutNotify(oldPosition);

        return canShoot;
    }

    private boolean canShoot(Player player){
        //passing context to the weapons
        for (WeaponCard wp:player.getWeaponCardsInHand().getCards()) {
            wp.passContext(player, ModelGate.model.getPlayerList(), ModelGate.model.getBoard());
        }

        //all possible usable cards
        OrderedCardList<WeaponCard> possibleCards = player.getHand().usableWeapons();

        //filtering cards with the only loaded ones
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
