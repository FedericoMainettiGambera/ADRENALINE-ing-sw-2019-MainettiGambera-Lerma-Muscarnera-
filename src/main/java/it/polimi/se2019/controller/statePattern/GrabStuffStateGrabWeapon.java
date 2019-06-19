package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;


public class GrabStuffStateGrabWeapon implements  State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffStateGrabWeapon.class.getName());

    private int actionNumber;

    private Player playerToAsk;

    private Thread inputTimer;

    public GrabStuffStateGrabWeapon(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpawnPointSquare playerSquare = ((SpawnPointSquare)(ModelGate.model.getBoard().getSquare(playerToAsk.getPosition().getX(), playerToAsk.getPosition().getY())));
        ArrayList<WeaponCard> toPickUp = (ArrayList)playerSquare.getWeaponCards().getCards();
        out.println("<Server> Cards from the Spawn point:");
        StringBuilder toPrintln = new StringBuilder();
        for (int i = 0; i < toPickUp.size() ; i++){
            toPrintln.append("    ").append(toPickUp.get(i).getID());
        }
        out.println(toPrintln);

        for (int i = toPickUp.size()-1; i >= 0; i--) {
            if(!(new ChooseHowToPayState(playerToAsk,toPickUp.get(i).getPickUpCost())).canPayInSomeWay()){
                out.println("<SERVER> Player can't pay for card: " + toPickUp.get(i).getID());
                toPickUp.remove(i);
            }
        }
        out.println("<Server> Possible cards to pick up from the spawn Point:");
        toPrintln = new StringBuilder();
        for (WeaponCard weaponCard : toPickUp) {
            toPrintln.append("    ").append(weaponCard.getID());
        }
        out.println(toPrintln);

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){
            out.println("<SERVER> The player has already three weapons");

            //ask what weapon in hand to discard and what weapon to pick up.
            ArrayList<WeaponCard> toDiscard = (ArrayList)playerToAsk.getWeaponCardsInHand().getCards();
            out.println("<Server> Possible cards to discard from hand:");
            toPrintln = new StringBuilder();
            for (WeaponCard weaponCard : toDiscard) {
                toPrintln.append(weaponCard.getID()).append("    ");
            }
            out.println("    " + toPrintln);

            if(toPickUp.isEmpty()){
                out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
            else {
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffSwitchWeapon(toPickUp, toDiscard);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    logger.severe("Exception Occured"+" "+e.getClass()+" "+e.getCause());
                }
            }
        }
        else {
            //ask what weapon to pick up

            if(toPickUp.isEmpty()){
                out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
                ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(playerToAsk);
            }
            else{
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffGrabWeapon(toPickUp);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                    this.inputTimer.start();
                } catch (Exception e) {
                    logger.severe("Exception Occured"+" "+e.getClass()+" "+e.getCause());
                }
            }
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        Position playerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();
        OrderedCardList<WeaponCard> playerWeapons = ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand();
        OrderedCardList<WeaponCard> squareWeapons = ((SpawnPointSquare)ModelGate.model.getBoard().getSquare(playerPosition)).getWeaponCards();

        if(ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){

            ViewControllerEventTwoString VCETwoString = (ViewControllerEventTwoString) VCE;

            WeaponCard toDiscard = playerWeapons.getCard(VCETwoString.getInput2());

            WeaponCard toDraw = squareWeapons.getCard(VCETwoString.getInput1());

            out.println("<SERVER> The player decided to switch his card " + toDiscard.getID() + " with the card " + toDraw.getID());

            //reload toDiscard
            out.println("<SERVER> Loading the card to discard (for free)");
            toDiscard.reload();
            out.println("<SERVER> Loading the card to pickUp (for free)");
            toDraw.reload();

            //discard old weapon
            out.println("<SERVER> Switching card: " + toDiscard.getID() + " ...");
            playerWeapons.moveCardTo(squareWeapons,toDiscard.getID());

            //draw new weapon
            out.println("<SERVER> ... for picking up card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            out.println("<SERVER> Paying the pick up cost");
            ChooseHowToPayState.makePayment(ModelGate.model.getCurrentPlayingPlayer(), toDraw.getPickUpCost());
        }
        else {
            ViewControllerEventString VCEString = (ViewControllerEventString) VCE;
            WeaponCard toDraw = squareWeapons.getCard(VCEString.getInput());

            //draw the weapon
            out.println("<SERVER> Picking up new card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            //replacing new card
            if(!ModelGate.model.getWeaponDeck().getCards().isEmpty()){
                out.println("<SERVER> Replacing picked up card in the spawn point with card: " + ModelGate.model.getWeaponDeck().getFirstCard().getID());
                ModelGate.model.getWeaponDeck().moveCardTo(squareWeapons, ModelGate.model.getWeaponDeck().getFirstCard().getID());
            }
            else{
                out.println("<SERVER> The weapon deck is empty, so the space left in the SpawnPoint from the picked up card is not replaced.");
            }

            out.println("<SERVER> Paying the pick up cost");
            ChooseHowToPayState.makePayment(ModelGate.model.getCurrentPlayingPlayer(), toDraw.getPickUpCost());
        }
    }

    public void afterPayment(){
        //set next state
        if(this.actionNumber == 1){
            if (ModelGate.model.hasFinalFrenzyBegun() && ModelGate.model.getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
            }
            else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        else if(this.actionNumber == 2){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
        }
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());

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
