package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.util.ArrayList;


public class GrabStuffStateGrabWeapon implements  State {

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public GrabStuffStateGrabWeapon(int actionNumber){
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpawnPointSquare playerSquare = ((SpawnPointSquare)(ModelGate.model.getBoard().getSquare(playerToAsk.getPosition().getX(), playerToAsk.getPosition().getY())));
        ArrayList<WeaponCard> toPickUp = (ArrayList)playerSquare.getWeaponCards().getCards();
        System.out.println("<Server> Cards from the Spawn point:");
        String toPrintln = "";
        for (int i = 0; i < toPickUp.size() ; i++){
            toPrintln += "    " + toPickUp.get(i).getID();
        }
        System.out.println(toPrintln);

        for (int i = toPickUp.size()-1; i >= 0; i--) {
            if(!playerToAsk.canPayAmmoCubes(toPickUp.get(i).getPickUpCost())){
                System.out.println("<SERVER> Player can't pay for card: " + toPickUp.get(i).getID());
                toPickUp.remove(i);
            }
        }
        System.out.println("<Server> Possible cards to pick up from the spawn Point:");
        toPrintln = "";
        for (int i = 0; i < toPickUp.size() ; i++) {
            toPrintln += "    " + toPickUp.get(i).getID();
        }
        System.out.println(toPrintln);

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){
            System.out.println("<SERVER> The player has already three weapons");

            //ask what weapon in hand to discard and what weapon to pick up.
            ArrayList<WeaponCard> toDiscard = (ArrayList)playerToAsk.getWeaponCardsInHand().getCards();
            System.out.println("<Server> Possible cards to discard from hand:");
            toPrintln = "";
            for (int i = 0; i < toDiscard.size() ; i++) {
                toPrintln += toDiscard.get(i).getID() + "    ";
            }
            System.out.println("    " + toPrintln);

            if(toPickUp.size()== 0){
                System.out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            }
            else {
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffSwitchWeapon(toPickUp, toDiscard);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            //ask what weapon to pick up

            if(toPickUp.size()== 0){
                System.out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            }
            else{
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffGrabWeapon(toPickUp);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        Position playerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();
        OrderedCardList<WeaponCard> playerWeapons = ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand();
        OrderedCardList<WeaponCard> squareWeapons = ((SpawnPointSquare)ModelGate.model.getBoard().getSquare(playerPosition)).getWeaponCards();

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){

            ViewControllerEventTwoString VCETwoString = (ViewControllerEventTwoString) VCE;

            WeaponCard toDiscard = playerWeapons.getCard(VCETwoString.getInput2());

            WeaponCard toDraw = squareWeapons.getCard(VCETwoString.getInput1());

            System.out.println("<SERVER> The player decided to switch his card " + toDiscard.getID() + " with the card " + toDraw.getID());

            //reload toDiscard
            System.out.println("<SERVER> Loading the card to discard (for free)");
            toDiscard.reload();
            System.out.println("<SERVER> Loading the card to pickUp (for free)");
            toDraw.reload();

            //discard old weapon
            System.out.println("<SERVER> Switching card: " + toDiscard.getID() + " ...");
            playerWeapons.moveCardTo(squareWeapons,toDiscard.getID());

            //draw new weapon
            System.out.println("<SERVER> ... for picking up card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            System.out.println("<SERVER> Paying the pick up cost");
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());
        }
        else {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            WeaponCard toDraw = squareWeapons.getCard(VCEString.getInput());

            //draw the weapon
            System.out.println("<SERVER> Picking up new card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            System.out.println("<SERVER> Paying the pick up cost");
            ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(toDraw.getPickUpCost());

            //replacing new card
            if(!ModelGate.model.getWeaponDeck().getCards().isEmpty()){
                System.out.println("<SERVER> Replacing picked up card in the spawn point with card: " + ModelGate.model.getWeaponDeck().getFirstCard().getID());
                ModelGate.model.getWeaponDeck().moveCardTo(squareWeapons, ModelGate.model.getWeaponDeck().getFirstCard().getID());
            }
            else{
                System.out.println("<SERVER> The weapon deck is empty, so the space left in the SpawnPoint from the picked up card is not replaced.");
            }
        }

        //set next state
        if(this.actionNumber == 1){

            if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));

            }

               else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        if(this.actionNumber == 2){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
        }
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setIsAFK(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
