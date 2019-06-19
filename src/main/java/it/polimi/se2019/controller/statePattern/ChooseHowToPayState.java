package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChooseHowToPayState {
    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ChooseHowToPayState.class.getName());


    private boolean paymentDone = false;

    public boolean isPaymentDone() {
        if(paymentDone){
            return true;
        }
        else {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                logger.severe("<SERVER> something interrupted the sleep payiment process but should never happen: " + e.toString());
            }
            return paymentDone;
        }
    }

    private Player payingPlayer;

    private AmmoList toPay;

    private AmmoList leftToPay;

    private Thread inputTimer;

    public ChooseHowToPayState(Player payingPlayer, AmmoList toPay){
        out.println("<SERVER> Started a payment process for player: " + payingPlayer.getNickname());

        this.payingPlayer = payingPlayer;

        this.toPay = toPay;
        this.leftToPay = new AmmoList();
        for (AmmoCubes a: toPay.getAmmoCubesList()) {
            leftToPay.addAmmoCubesOfColor(a.getColor(),a.getQuantity());
        }
    }

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

    public boolean canPayInSomeWay(){
        //create an ammo list that represents the total budget of the player (AmmoBox + powerUps)
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

    public SelectorEventPaymentInformation usablePowerUps(){
        SelectorEventPaymentInformation SEPaymentInformation = new SelectorEventPaymentInformation(SelectorEventTypes.paymentInformation, toPay.buildAmmoListV());
        for (AmmoCubes a: toPay.getAmmoCubesList()) {
            for (PowerUpCard p : payingPlayer.getPowerUpCardsInHand().getCards()) {
                if(a.getColor() == p.getColor()){
                    SEPaymentInformation.addPossibility(p.buildPowerUpCardV());
                }
            }
        }
        SEPaymentInformation.setCanPayWithoutPowerUps(payingPlayer.canPayAmmoCubes(toPay));
        return SEPaymentInformation;
    }

    /*todo da eliminare in futuro:
    concetto di utilizzo di questa classe:
    ogni volta c he bisogna pagare qualcosa
    Nel VCEHC nell'attributo paymentProcess, si istanzia un nuovo oggetto di tipo ChooseHowToPayState con i valori desiderati.
    Successivamente si chiama il metodo VCEHC.paymentProcess.checkPayMethods().
            -se ritorna true il pagamento è possibile:
                     bisogna aspettare che il pagamento vada a buon fine controllando
                     la variabile statica paymentDone, finchè è false si aspetta.
                     Quando diventa true, allora il pagamento è andato a buon fine ed è terminato e si può proseguire col resto
            -Se ritorna false il pagamento non è possibile
                    -Se si fosse scelto di effettuare il pagamento: beh, non verrà mai eseguito

    Se si volesse solo controllare che un pagamento sia possibile si può fare così:
            (new ChooseHowToPayState(... , ... , ...)).canPayInSomeWay();
     */
    public boolean checkPayMethods() {
        if(canPayInSomeWay()) {
            out.println("<SERVER> the player can pay some way");
            /*if (canPaySomethingWithPowerUps()) {
                out.println("<SERVER> player can pay with power ups");
                //ask what he wants to pay with powerUp, start Timer
                SelectorEventPaymentInformation SEPaymentInformation = usablePowerUps();
                //ask for input
                try {
                    Player tempPlayer = SelectorGate.getCorrectSelectorFor(payingPlayer).getPlayerToAsk();
                    SelectorGate.getCorrectSelectorFor(payingPlayer).setPlayerToAsk(payingPlayer);
                    SelectorGate.getCorrectSelectorFor(payingPlayer).askPaymentInformation(SEPaymentInformation);
                    this.inputTimer = new Thread(new WaitForPlayerInput(this.payingPlayer, this.getClass().toString()));
                    this.inputTimer.start();
                    SelectorGate.getCorrectSelectorFor(payingPlayer).setPlayerToAsk(tempPlayer); //Restore the original playerToAsk so it doesn't get in the way of the State Pattern
                } catch (Exception e) {
                    logger.severe("exception occured during payment:"+ Arrays.toString(e.getStackTrace()) +" "+e.getClass()+" "+e.getCause());
                }
            }
            else {
                out.println("<SERVER> the player can only pay using his ammoBox");
                //pay as usual
                payingPlayer.payAmmoCubes(toPay);
                out.println("<SERVER> DONE PAYING");
                paymentDone = true;
            }*/
            //todo delete
            paymentDone = true;
            return true;
        }
        else{
            out.println("<SERVER> the player can't pay in any way.");
            return false;
        }
    }

    public void doPayment(ViewControllerEventPaymentInformation VCEChosenPowerUps) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doPayment();");

        for (Object o:VCEChosenPowerUps.getAnswer()) {
            out.println("         player is paying with power up: " + ((PowerUpCardV) o).getName() + " (" + ((PowerUpCardV) o).getColor() + ")");
            for (AmmoCubes a : leftToPay.getAmmoCubesList()) {
                if (((PowerUpCardV) o).getColor().equals( a.getColor()) ){
                    leftToPay.getAmmoCubesList().remove(a);
                    break;
                }
            }
            payingPlayer.getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(), ((PowerUpCardV) o).getID());
        }
        payingPlayer.payAmmoCubes(leftToPay);

        out.println("<SERVER> DONE PAYING");
        paymentDone = true;
    }

    public void handleAFK() {
        //TODO
        this.payingPlayer.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        out.println("<SERVER> forcing payment by AmmoBox");
        payingPlayer.payAmmoCubes(toPay);
        paymentDone = true;
        out.println("<SERVER> DONE PAYING");
    }

    public static void makePayment(Player player, AmmoList cost){
        ViewControllerEventHandlerContext.paymentProcess = new ChooseHowToPayState(player, cost);
        //make the payment process start
        if(ViewControllerEventHandlerContext.paymentProcess.checkPayMethods()) {
            //wait for the payment process to end
            /*
            while (!ViewControllerEventHandlerContext.paymentProcess.isPaymentDone()) {
                //DO nothing
            }
            */
        }
        else{
            logger.severe("<SERVER> trying to pay something that player can't afford, this should never happen.");
        }
    }
}
