package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.AmmoCubes;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TargetingScopeState implements State{
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    /**following state to be set*/
    private State nextState;
    /**player to ask the input*/
    private Player playerToAsk;
    /**thread for the count down*/
    private Thread inputTimer;
    /**a list of the player that have just been damaged*/
    private List<Player> damagedPlayers;
    /**a list of the targeting scope in hand*/
    private List<PowerUpCard> listOfTargetingScope;
    /**a list of possible way to purchase*/
    private List<Object> possiblePayments;

    /**constructor,
     * @param nextState sets the nextState attribute
     * @param damagedPlayers sets the damagedPlayer attribute*/
     TargetingScopeState(State nextState, List<Player> damagedPlayers){
        out.println("<SERVER> New state: " + this.getClass());
        this.nextState = nextState;
        this.damagedPlayers = damagedPlayers;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;

        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        this.possiblePayments = possiblePayments();

        if(!damagedPlayers.isEmpty()) {
            this.listOfTargetingScope = getListOfTargetingScope();
        }
        else{
            this.listOfTargetingScope = new ArrayList<>();
        }

        printStuff();

        if(!listOfTargetingScope.isEmpty() &&//checks if he has at least one targeting scope to use
                (!damagedPlayers.isEmpty()) &&//checks if the damagedPlayer list is not empty
                (!possiblePayments.isEmpty())){//checks if he can pay the targeting scope in some way
            out.println("<SERVER> player can use targeting scope");
            //build the V version of the lists
            List<PowerUpCardV> listOfTargetingScopeV = buildThePowerUpVList();

            List<Object> possiblePaymentsV = buildListOfPossiblePayments();

            List<PlayerV> damagedPlayersV = new ArrayList<>();
            for (Player p : damagedPlayers) {
                damagedPlayersV.add(p.buildPlayerV());
            }

            //asks what targeting scope he wants to use + possibility to not use it
            //asks how he wants to pay
            //asks who he wants to hit with the plus damage
            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askTargetingScope(listOfTargetingScopeV, possiblePaymentsV, damagedPlayersV);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
               logger.log(Level.SEVERE, "EXCEPTION", e);
            }
        }
        else{
            //the player can't play targeting scope, so jump to the tagback granade
            out.println("<SERVER> player can't use targeting scope.");
            passToTagBackGranadeState();
        }
    }

    private List<Object> buildListOfPossiblePayments(){
        List<Object> possiblePaymentsV = new ArrayList<>();

        for (Object o:possiblePayments) {
            if(o.getClass().toString().contains("AmmoCube")){
                possiblePaymentsV.add(((AmmoCubes)o).getColor());
            }
            else{
                possiblePaymentsV.add(((PowerUpCard)o).buildPowerUpCardV());
            }
        }
       return possiblePaymentsV;
    }
    private  List<PowerUpCardV> buildThePowerUpVList(){

        List<PowerUpCardV> listOfTargetingScopeV = new ArrayList<>();
        for (PowerUpCard p : listOfTargetingScope) {
            listOfTargetingScopeV.add(p.buildPowerUpCardV());
        }

        return  listOfTargetingScopeV;
    }

    private void printStuff(){
        out.println("<SERVER> Possible Targeting Scope to use:");
        for (PowerUpCard p: listOfTargetingScope) {
            out.println("         " + p.getName() + "    COLOR: " + p.getColor() + "    ID: " + p.getID());
        }
        out.println("<SERVER> Possible way to pay: ");
        for (Object o:possiblePayments) {
            if(o.getClass().toString().contains("AmmoCube")){
                out.println("         pay using an ammo cube of color: " +((AmmoCubes)o).getColor());
            }
            else{
                out.println("         pay discarding power up: " + ((PowerUpCard)o).getName()+ "    COLOR: " + ((PowerUpCard)o).getColor() + "    ID: " + ((PowerUpCard)o).getID());
            }
        }
        out.println("<SERVER> Possible players to hit: ");
        for (Player p : damagedPlayers) {
            out.println("         " + p.getNickname());
        }
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventListOfObject viewControllerEventListOfObject = (ViewControllerEventListOfObject)viewControllerEvent;
        List<Object> listOfObject = viewControllerEventListOfObject.getAnswer();
        //the answer is structured like so:
        //      .get(0) represents the number of the chosen targeting scope in the this.listOfTargetingScope to use
        //              or if he doesn't want to use any the number will be bigger than the this.listOfTargetingScope size's
        //      .get(1) represents the chosen paying method
        //      .get(2) represents the player he want to hit

        if((Integer) listOfObject.get(0) != this.listOfTargetingScope.size()){ //means "YES, the player wants to use a targeting scope"
            //take the chosen power up
            PowerUpCard chosenTargetingScope = this.listOfTargetingScope.get((Integer)listOfObject.get(0));
            out.println("<SERVER> chosen targeting scope is: " + chosenTargetingScope.getName() + "    COLOR: " + chosenTargetingScope.getColor() + "    ID: " + chosenTargetingScope.getID());

            //make the chosen payment
            Object chosenPayingmethod =this.possiblePayments.get((Integer)listOfObject.get(1));
            if(chosenPayingmethod.getClass().toString().contains("AmmoCube")){ //pay with an ammo cube
                playerToAsk.payAmmoCubes(((AmmoCubes)chosenPayingmethod).getColor(), 1);
                out.println("<SERVER> chosen paying method is with an ammoCube of color " + ((AmmoCubes)chosenPayingmethod).getColor() + " from the ammo box");
            }
            else{ // pay discarding a power up
                playerToAsk.getPowerUpCardsInHand().moveCardTo(ModelGate.getModel().getPowerUpDiscardPile(), ((PowerUpCard)chosenPayingmethod).getID());
                out.println("<SERVER> chosen paying methos is by discarding power up: " + ((PowerUpCard)chosenPayingmethod).getName() + "   COLOR: " + ((PowerUpCard)chosenPayingmethod).getColor() + "   ID: " +((PowerUpCard)chosenPayingmethod).getID());
            }


            //hit the chosen player with one damage
            Player chosenPlayerToHit = this.damagedPlayers.get((Integer)listOfObject.get(2));
            chosenPlayerToHit.addDamages(this.playerToAsk, 1);
            out.println("<SERVER> chosen player to hit is: " + chosenPlayerToHit.getNickname());

            //discard the chosenTargetingScope power up
            out.println("<SERVER> discarding the used targeting scope");
            this.playerToAsk.getPowerUpCardsInHand().moveCardTo(ModelGate.getModel().getPowerUpDiscardPile(), chosenTargetingScope.getID());
        }
        else { //means "NO", the player doesn't want to use the targeting scope
            out.println("<SERVER> the player doesn't want to use the Targeting Scope power up");
            //do nothing about it
        }

        //jump to tag back granade
        passToTagBackGranadeState();
    }

    private List<PowerUpCard> getListOfTargetingScope(){
        List<PowerUpCard> listOfTargetingScope = new ArrayList<>();

        for (PowerUpCard powerUpCard:this.playerToAsk.getPowerUpCardsInHand().getCards()) {
            if(powerUpCard.getName().equalsIgnoreCase("TARGETING SCOPE")){
                listOfTargetingScope.add(powerUpCard);
            }
        }
        return listOfTargetingScope;
    }

    private List<Object> possiblePayments(){
        //returns a list of ammoCubes and PowerUps that represents the possible ways the payment can be done.
        List<Object> possiblePayments = new ArrayList<>();
        //checks for all the possible colors he can pay with the ammo cubes
        for (AmmoCubes ammoCubes:playerToAsk.getBoard().getAmmoBox().getAmmoCubesList()) {
            if(ammoCubes.getQuantity()>0){
                //he can pay one ammo cube with the current color
                possiblePayments.add(ammoCubes);
            }
        }
        for (PowerUpCard powerUpCard: playerToAsk.getPowerUpCardsInHand().getCards()) {
            if(!powerUpCard.getName().equalsIgnoreCase("TARGETING SCOPE")){
                //TODO if you have time, fix the problem of having two targeting scope and pay one with the other... BUT it is a long thing, should add a new state...
                //he can pay with the power up, but not the one he is about to use (targeting scope), so skip all targeting scope
                possiblePayments.add(powerUpCard);
            }
        }
        return possiblePayments;
    }

    private void passToTagBackGranadeState(){
        ViewControllerEventHandlerContext.setNextState(new TagBackGranadeState(this.nextState, this.damagedPlayers, this.playerToAsk));
        ViewControllerEventHandlerContext.getState().askForInput(null);
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        ViewControllerEventHandlerContext.setNextState(new TagBackGranadeState(new ScoreKillsState(), this.damagedPlayers, this.playerToAsk));
        ViewControllerEventHandlerContext.getState().askForInput(null);
    }
}