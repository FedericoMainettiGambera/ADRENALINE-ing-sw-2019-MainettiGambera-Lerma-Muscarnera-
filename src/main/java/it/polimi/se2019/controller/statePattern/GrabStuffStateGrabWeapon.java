package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;
import it.polimi.se2019.model.events.ViewControllerEventTwoString;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GrabStuffStateGrabWeapon implements  State {

    private int actionNumber;

    public GrabStuffStateGrabWeapon(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        SelectorGate.selector.setPlayerToAsk(playerToAsk);

        SpawnPointSquare playerSquare = ((SpawnPointSquare)(ModelGate.model.getBoard().getSquare(playerToAsk.getPosition().getX(), playerToAsk.getPosition().getY())));
        ArrayList<WeaponCard> toPickUp = (ArrayList)playerSquare.getWeaponCards().getCards();

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){
            //ask what weapon in hand to discard and what weapon to pick up.
            ArrayList<WeaponCard> toDiscard = (ArrayList)playerToAsk.getWeaponCardsInHand().getCards();
            SelectorGate.selector.askGrabStuffSwitchWeapon(toPickUp, toDiscard);
        }
        else {
            //ask what weapon to pick up
            SelectorGate.selector.askGrabStuffGrabWeapon(toPickUp);
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        Position playerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();
        OrderedCardList<WeaponCard> playerWeapons = ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand();
        OrderedCardList<WeaponCard> squareWeapons = ((SpawnPointSquare)ModelGate.model.getBoard().getSquare(playerPosition)).getWeaponCards();

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){

            ViewControllerEventTwoString VCETwoString = (ViewControllerEventTwoString) VCE;
            WeaponCard toDiscard = playerWeapons.getCard(VCETwoString.getInput2());

            WeaponCard toDraw = squareWeapons.getCard(VCETwoString.getInput1());

            //reload toDiscard
            toDiscard.reload();
            //discard old weapon
            System.out.println("<SERVER> switching card: " + toDiscard.getID() + " ...");
            playerWeapons.moveCardTo(squareWeapons,toDiscard.getID());

            //draw new weapon
            System.out.println("<SERVER> ... for picking up card: " + toDraw.getID());
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());
        }
        else {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            WeaponCard toDraw = squareWeapons.getCard(VCEString.getInput());

            //draw the weapon
            System.out.println("<SERVER> picking up new card: " + toDraw.getID());
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            //replacing new card
            System.out.println("<SERVER> replacing picked up card in the spawn point with card: " + ModelGate.model.getWeaponDeck().getFirstCard().getID());
            if(!ModelGate.model.getWeaponDeck().getCards().isEmpty()){
                ModelGate.model.getWeaponDeck().moveCardTo(squareWeapons, ModelGate.model.getWeaponDeck().getFirstCard().getID());
            }
        }

        //set next state
        if(this.actionNumber == 1){
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        if(this.actionNumber == 2){
            ViewControllerEventHandlerContext.setNextState(new ReloadState());
        }
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

    }
}
