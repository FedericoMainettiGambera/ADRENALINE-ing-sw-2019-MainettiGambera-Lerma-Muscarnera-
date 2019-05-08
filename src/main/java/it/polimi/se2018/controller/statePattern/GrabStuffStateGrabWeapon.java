package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;
import it.polimi.se2018.model.events.ViewControllerEventTwoString;

public class GrabStuffStateGrabWeapon implements  State {

    private int actionNumber;

    public GrabStuffStateGrabWeapon(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){
            //ask what weapon in hand to discard and what weapon to pick up.
        }
        else {
            //ask what weapon
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

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
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());
        }
        else {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            WeaponCard toDraw = squareWeapons.getCard(VCEString.getInput());

            //draw the weapon
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
