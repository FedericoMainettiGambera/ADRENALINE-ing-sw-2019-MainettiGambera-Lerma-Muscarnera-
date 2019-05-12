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
            //ask what weapon
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
            WeaponCard toDiscard = playerWeapons.getCard(VCETwoString.getInput1());

            WeaponCard toDraw = squareWeapons.getCard(VCETwoString.getInput2());

            //reload toDiscard
            toDiscard.reload();
            //discard old weapon
            playerWeapons.moveCardTo(squareWeapons,toDiscard.getID());

            //draw new weapon
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());
        }
        else {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            WeaponCard toDraw = squareWeapons.getCard(VCEString.getInput());

            //draw the weapon
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());
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
