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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**this class allows the user to grab a weapon
 * * @author LudoLerma
 *  * @author FedericoMainettiGambera
 *  */
public class GrabStuffStateGrabWeapon implements  State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(GrabStuffStateGrabWeapon.class.getName());

    /**distinguish between 1st and 2nd action*/
    private int actionNumber;

    /**the player to be asked the input*/
    private Player playerToAsk;

    /**countdown till AFK status*/
    private Thread inputTimer;

    /**constructor
     * @param actionNumber initialize actionNumber*/
    public GrabStuffStateGrabWeapon(int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }



    private void printList(List<WeaponCard> weaponCard){

        StringBuilder toPrintln = new StringBuilder();
        for (WeaponCard weaponCard1 : weaponCard) {
            toPrintln.append("    ").append(weaponCard1.getID());
        }
        out.println(toPrintln);


    }
    /**@param playerToAsk refers to the current playing player
     * the input to be asked might be different depending on the context the action is performed,
     * indeed it may be that the user can't pay for any of the cards
     * that there are no cards at all in the spawn point where the user happens to be
     * that the user has three weapon in hand already and need to discard one etc
     * here all of these possibilities are handled,
     * and it is managed to ask the right input for each one of them
     * */
    @Override
    public void askForInput(Player playerToAsk) {

        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        try {
            SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"EXCEPTION", e);
        }

        SpawnPointSquare playerSquare = ((SpawnPointSquare)(ModelGate.getModel().getBoard().getSquare(playerToAsk.getPosition().getX(), playerToAsk.getPosition().getY())));
        List<WeaponCard> toPickUp = playerSquare.getWeaponCards().getCards();

        out.println("<Server> Cards from the Spawn point:");
        printList(toPickUp);

        for (int i = toPickUp.size()-1; i >= 0; i--) {
            if(!(new ChooseHowToPayState(playerToAsk,toPickUp.get(i).getPickUpCost())).canPayInSomeWay()){

                out.println("<SERVER> Player can't pay for card: " + toPickUp.get(i).getID());
                toPickUp.remove(i);
            }
        }
        out.println("<Server> Possible cards to pick up from the spawn Point:");
        printList(toPickUp);

        if(ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3){

            discardWep(toPickUp);
        }
        else {
            //ask what weapon to pick up

            if(toPickUp.isEmpty()){
                out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
                changeState(new TurnState(this.actionNumber));
            }
            else{
                try {
                    SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                    SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffGrabWeapon(toPickUp);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask grab stuff grab weapon"));
                    this.inputTimer.start();
                } catch (Exception e) {
                    logger.severe("Exception Occurred"+" "+e.getClass()+" "+e.getCause());
                }
            }
        }
    }


    /**if the player is already holding three cards he's going to need to discard one in order to
     * grab a new one
     * @param toPickUp show him the possible weapon to be picked up*/
    private void discardWep(List<WeaponCard> toPickUp){


        out.println("<SERVER> The player has already three weapons");

        //ask what weapon in hand to discard and what weapon to pick up.
        List<WeaponCard> toDiscard = playerToAsk.getWeaponCardsInHand().getCards();
        out.println("<Server> Possible cards to discard from hand:");
        printList(toDiscard);

        if(toPickUp.isEmpty()){
            out.println("<SERVER> There are no weapon to pick up, asking another action to the user.");
            changeState(new TurnState(this.actionNumber));
        }
        else {
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askGrabStuffSwitchWeapon(toPickUp, toDiscard);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask grab stuff switch weapon"));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.severe("Exception Occurred"+" "+e.getClass()+" "+e.getCause()+ Arrays.toString(e.getStackTrace()));
            }
        }


    }

    /**change the state to a new one
     * @param state indicates which*/
    private void changeState(State state){
        ViewControllerEventHandlerContext.setNextState(state);
        ViewControllerEventHandlerContext.getState().askForInput(playerToAsk);
    }

    /**@param viewControllerEvent contains the input from the current playing player
     * if a weapon card can be paid, the player will draw it loaded
     * if he already has 3 weapons, they will have to discard one in order to pick up the new one
     * */
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();


            WeaponCard toDraw=switchCardsOrJustPickUpOne(viewControllerEvent);
            out.println("<SERVER> Paying the pick up cost");
            ChooseHowToPayState.makePayment(ModelGate.getModel().getCurrentPlayingPlayer(), toDraw.getPickUpCost());
        }


    /** @param viewControllerEvent needed to extrapolate info:
     * 1 the function may need a two string event, in case the user needed to pick up and discard a weapon card
     * 2 the function may need a one string event, in case the user just needed to pick up one
     * @return toDraw, which is needed to the calling function in order to effectuate the payment*/
    private WeaponCard switchCardsOrJustPickUpOne(ViewControllerEvent viewControllerEvent) {

        out.println("<SERVER> player has answered before the timer ended.");
        out.println("<SERVER> " + this.getClass() + ".doAction();");

        Position playerPosition = ModelGate.getModel().getCurrentPlayingPlayer().getPosition();
        OrderedCardList<WeaponCard> playerWeapons = ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand();
        OrderedCardList<WeaponCard> squareWeapons = ((SpawnPointSquare) ModelGate.getModel().getBoard().getSquare(playerPosition)).getWeaponCards();

        if (ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().size() >= 3) {

            ViewControllerEventTwoString viewControllerEventTwoString = (ViewControllerEventTwoString) viewControllerEvent;
            WeaponCard toDiscard = playerWeapons.getCard(viewControllerEventTwoString.getInput2());
            WeaponCard toDraw = squareWeapons.getCard(viewControllerEventTwoString.getInput1());

            out.println("<SERVER> The player decided to switch his card " + toDiscard.getID() + " with the card " + toDraw.getID());

            //reload toDiscard

            out.println("<SERVER> Loading the card to discard (for free)");

            toDiscard.reload();

            out.println("<SERVER> Loading the card to pickUp (for free)");
            toDraw.reload();

            //discard old weapon
            out.println("<SERVER> Switching card: " + toDiscard.getID() + " ...");
            playerWeapons.moveCardTo(squareWeapons, toDiscard.getID());

            //draw new weapon
            out.println("<SERVER> ... for picking up card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            return toDraw;

        } else {
            ViewControllerEventString viewControllerEventString = (ViewControllerEventString) viewControllerEvent;
            WeaponCard toDraw = squareWeapons.getCard(viewControllerEventString.getInput());

            //draw the weapon
            out.println("<SERVER> Picking up new card: " + toDraw.getID());
            squareWeapons.moveCardTo(playerWeapons, toDraw.getID());

            //TODO delete, this part has been moved this part to SpawnState
            //replacing new card
            //if (!ModelGate.getModel().getWeaponDeck().getCards().isEmpty()) {
            //    out.println("<SERVER> Replacing picked up card in the spawn point with card: " + ModelGate.getModel().getWeaponDeck().getFirstCard().getID());
            //    ModelGate.getModel().getWeaponDeck().moveCardTo(squareWeapons, ModelGate.getModel().getWeaponDeck().getFirstCard().getID());
            //} else {
            //    out.println("<SERVER> The weapon deck is empty, so the space left in the SpawnPoint from the picked up card is not replaced.");
            //}
            return toDraw;
        }
    }

    /** called by choose how to pay method, this function will handle to direct the game toward the right next state*/
     void afterPayment(){
        //set next state
        if(this.actionNumber == 1){
            if (ModelGate.getModel().hasFinalFrenzyBegun() && ModelGate.getModel().getCurrentPlayingPlayer().getBeforeorafterStartingPlayer() >= 0) {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
            }
            else ViewControllerEventHandlerContext.setNextState(new TurnState(2));
        }
        else if(this.actionNumber == 2){
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
        }
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());

    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
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
