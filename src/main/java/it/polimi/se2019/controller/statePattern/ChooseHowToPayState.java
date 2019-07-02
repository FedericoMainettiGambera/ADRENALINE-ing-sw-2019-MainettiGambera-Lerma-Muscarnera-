package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.AmmoCubes;
import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventPaymentInformation;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPaymentInformation;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Logger;

/**this class allows payment*/
public class ChooseHowToPayState {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ChooseHowToPayState.class.getName());
   /**contains the player who needs to effectuate the payment*/
    private Player payingPlayer;
    /**the amount due*/
    private AmmoList toPay;
    /**amount left to be paid (for example if you don't have enough ammos and want to use a power up to reach the due amount*/
    private AmmoList leftToPay;
    /**thread that starts a timer for the user answer */
    private Thread inputTimer;

    private String donePaying="<SERVER> DONE PAYING";

    /**@param payingPlayer player that needs to effectuate a payment
     * @param toPay amount due*/
    public ChooseHowToPayState(Player payingPlayer, AmmoList toPay){
        out.println("<SERVER> Started a payment process for player: " + payingPlayer.getNickname());

        this.payingPlayer = payingPlayer;

        this.toPay = toPay;
        this.leftToPay = new AmmoList();
        for (AmmoCubes a: toPay.getAmmoCubesList()) {
            leftToPay.addAmmoCubesOfColor(a.getColor(),a.getQuantity());
        }
    }

    /**check if you can pay using power ups*/
    public boolean canPaySomethingWithPowerUps(){
        for (AmmoCubes a: toPay.getAmmoCubesList()) {
            for (PowerUpCard p: payingPlayer.getPowerUpCardsInHand().getCards()) {
                if(a.getColor().equals(p.getColor())){
                    return true;
                }
            }
        }
        return false;
    }

    /**check if you can pay using your ammos, your power ups or both of them*/
    public boolean canPayInSomeWay(){
        //create an ammo list that represents the total budget of the player (AmmoBox + powerUps)

        if(toPay.getAmmoCubesList().isEmpty()){
            return true;
            //because it's free
        }

        AmmoList tempAmmoList = new AmmoList();
        for (AmmoCubes a: payingPlayer.getBoard().getAmmoBox().getAmmoCubesList()) {
            tempAmmoList.addAmmoCubesOfColor(a.getColor(),a.getQuantity());
        }
        for (PowerUpCard p : payingPlayer.getPowerUpCardsInHand().getCards()) {
            tempAmmoList.addAmmoCubesOfColor(p.getColor(),1);
        }

        //create a fake player to simulate the transaction with the new total Budget
        Player tempPlayer = new Player();
        tempPlayer.getBoard().setAmmoBox(tempAmmoList);

        //checks if the total budget of the player can pay the amount
        return tempPlayer.canPayAmmoCubes(toPay);
    }

    /**tells the user which power ups he could use to pay
     * @return  selectorEventPaymentInformation */
     private SelectorEventPaymentInformation usablePowerUps(){
        SelectorEventPaymentInformation selectorEventPaymentInformation = new SelectorEventPaymentInformation(SelectorEventTypes.paymentInformation, toPay.buildAmmoListV());
        for (AmmoCubes a: toPay.getAmmoCubesList()) {
            for (PowerUpCard p : payingPlayer.getPowerUpCardsInHand().getCards()) {
                if(a.getColor() == p.getColor()){
                    selectorEventPaymentInformation.addPossibility(p.buildPowerUpCardV());
                }
            }
        }
        selectorEventPaymentInformation.setCanPayWithoutPowerUps(payingPlayer.canPayAmmoCubes(toPay));
        return selectorEventPaymentInformation;
    }

    /**make the player chose in which way he wants to pay, if he can pay in more than one way
     * @return  boolean value which is true in case the player can pay somehow */
    private boolean checkPayMethods() {
        if(canPayInSomeWay()) {
            out.println("<SERVER> the player can pay some way");
            if (canPaySomethingWithPowerUps()) {
                out.println("<SERVER> player can pay with power ups");
                //ask what he wants to pay with powerUp, start Timer
                SelectorEventPaymentInformation selectorEventPaymentInformation = usablePowerUps();
                //ask for input
                try {
                    Player tempPlayer = SelectorGate.getCorrectSelectorFor(payingPlayer).getPlayerToAsk();
                    SelectorGate.getCorrectSelectorFor(payingPlayer).setPlayerToAsk(payingPlayer);
                    SelectorGate.getCorrectSelectorFor(payingPlayer).askPaymentInformation(selectorEventPaymentInformation);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.payingPlayer, this.getClass().toString()));
                    this.inputTimer.start();
                    SelectorGate.getCorrectSelectorFor(payingPlayer).setPlayerToAsk(tempPlayer); //Restore the original playerToAsk so it doesn't get in the way of the State Pattern
                } catch (Exception e) {
                    logger.severe("exception occurred during payment:"+ Arrays.toString(e.getStackTrace()) +" "+e.getClass()+" "+e.getCause());
                }
            }
            else {
                out.println("<SERVER> the player can only pay using his ammoBox");
                //pay as usual
                payingPlayer.payAmmoCubes(toPay);
                out.println(donePaying);

                afterPayment();
            }
            return true;
        }
        else{
            out.println("<SERVER> the player can't pay in any way.");
            return false;
        }
    }
    /** @param viewControllerEventChosenPowerUp contains the power up the player chose to pay with
     * here it's removed from their hand*/
    public void doPayment(ViewControllerEventPaymentInformation viewControllerEventChosenPowerUp) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doPayment();");

        for (Object o:viewControllerEventChosenPowerUp.getAnswer()) {
            out.println("         player is paying with power up: " + ((PowerUpCardV) o).getName() + " (" + ((PowerUpCardV) o).getColor() + ")");
            for (AmmoCubes a : leftToPay.getAmmoCubesList()) {
                if (((PowerUpCardV) o).getColor().equals( a.getColor()) ){
                    leftToPay.getAmmoCubesList().remove(a);
                    break;
                }
            }
            payingPlayer.getPowerUpCardsInHand().moveCardTo(ModelGate.getModel().getPowerUpDiscardPile(), ((PowerUpCardV) o).getID());
        }
        payingPlayer.payAmmoCubes(leftToPay);

        out.println(donePaying);

        afterPayment();
    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    public void handleAFK() {
        //TODO
        this.payingPlayer.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        out.println("<SERVER> forcing payment by AmmoBox");
        payingPlayer.payAmmoCubes(toPay);

        afterPayment();

        out.println(donePaying);
    }

    /**once the payment is completed, a new state is set, depending on which state in first place called this one*/
     private void afterPayment(){
        //here are the three cases where the payment is used:

        if(ViewControllerEventHandlerContext.state.getClass().toString().contains("GrabStuffStateGrabWeapon")){
            ((GrabStuffStateGrabWeapon)ViewControllerEventHandlerContext.state).afterPayment();
        }
        else if(ViewControllerEventHandlerContext.state.getClass().toString().contains("ReloadState")){
            ((ReloadState)ViewControllerEventHandlerContext.state).afterPayment();
        }
        else if(ViewControllerEventHandlerContext.state.getClass().toString().contains("ShootPeople")){
            ((ShootPeopleAskForInputState)ViewControllerEventHandlerContext.state).afterPayment();
        }
    }
    /** effectuate the payment
     * @param player is the player who requested the payment
     * @param cost is the amount due*/
     static void makePayment(Player player, AmmoList cost){
        ViewControllerEventHandlerContext.paymentProcess = new ChooseHowToPayState(player, cost);
        //make the payment process start
        if(ViewControllerEventHandlerContext.paymentProcess.checkPayMethods()) {
            out.println("<SERVER> waiting for the payment to complete");
        }
        else{
            logger.severe("<SERVER> trying to pay something that player can't afford, this should never happen.");
        }
    }
}
