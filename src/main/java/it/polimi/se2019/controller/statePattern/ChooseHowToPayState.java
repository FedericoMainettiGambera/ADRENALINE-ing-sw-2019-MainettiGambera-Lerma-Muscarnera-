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
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPaymentInformation;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.util.List;

public class ChooseHowToPayState {

    public static boolean paymentDone;

    private Player payingPlayer;

    private AmmoList toPay;

    private AmmoList leftToPay;

    private Thread inputTimer;

    private Object callingClass;

    public ChooseHowToPayState(Object callingClass, Player payingPlayer, AmmoList toPay){
        System.out.println("<SERVER> Started a payment process for player: " + payingPlayer.getNickname());

        paymentDone = false;

        this.callingClass = callingClass;
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
                if(a.getColor()==p.getColor()){
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

    /*idea behind the payment process:
    everytime something must be payed by someone:
    nel VCEHC si istanzia un nuovo oggetto di tipo ChooseHowToPayState con i valori desiderati.
    successivamente si chiama il metodo VCEHC.checkPayMethods(...)
    come parametro gli si da true se si vuole anche effettuare il pagamento, false se si vuole solo controllare se sia possibile pagare:
            se ritorna true il pagamento è possibile
                    ora bisogna aspettare che il pagamento vada a buon fine controllando la variabile statica paymentDone, finchè è false si aspetta.
                    quando diventa true, allora il pagamento è andato a buon fine ed è terminato e si può proseguire col resto
                    RICORDA DI RESETTARE IL playerToAsk in VCEHC !!
            se ritorna false il pagamento non è possibile
                    non si può fare il pagamento
     */
    public void checkPayMethods(boolean isToPay) {
        this.payingPlayer = payingPlayer;
        if(canPayInSomeWay()) {
            System.out.println("<SERVER> the player can pay some way");
            if (canPaySomethingWithPowerUps()) {
                System.out.println("<SERVER> player can pay with power ups");
                if(isToPay) {
                    //ask what he wants to pay with powerUp, start Timer
                    SelectorEventPaymentInformation SEPaymentInformation = usablePowerUps();
                    //ask for input
                    try {
                        SelectorGate.getCorrectSelectorFor(payingPlayer).setPlayerToAsk(payingPlayer);
                        SelectorGate.getCorrectSelectorFor(payingPlayer).askPaymentInformation(SEPaymentInformation);
                        this.inputTimer = new Thread(new WaitForPlayerInput(this.payingPlayer, this.getClass().toString()));
                        this.inputTimer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("<SERVER> the player can only pay using his ammoBox");
                if(isToPay) {
                    //pay as usual
                    payingPlayer.payAmmoCubes(toPay);
                    System.out.println("<SERVER> DONE PAYING");
                    paymentDone = true;
                }
            }
        }
        else{
            System.out.println("<SERVER> the player can't pay in any way.");
        }
    }

    public void doPayment(ViewControllerEventPaymentInformation VCEChosenPowerUps) {
        this.inputTimer.interrupt();
        System.out.println("<SERVER> player has answered before the timer ended.");

        System.out.println("<SERVER> "+ this.getClass() +".doPayment();");

        for (Object o:VCEChosenPowerUps.getAnswer()) {
            System.out.println("         player is paying with power up: " + ((PowerUpCardV) o).getName() + " (" + ((PowerUpCardV) o).getColor() + ")");
            for (AmmoCubes a : leftToPay.getAmmoCubesList()) {
                if (((PowerUpCardV) o).getColor() == a.getColor()) {
                    leftToPay.getAmmoCubesList().remove(a);
                    break;
                }
            }
            payingPlayer.getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(), ((PowerUpCardV) o).getID());
        }
        payingPlayer.payAmmoCubes(leftToPay);

        System.out.println("<SERVER> DONE PAYING");
        paymentDone = true;
    }

    public void handleAFK() {
        //TODO
        this.payingPlayer.setAFKWithNotify(true);
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        System.out.println("<SERVER> forcing payment by AmmoBox");
        payingPlayer.payAmmoCubes(toPay);
        paymentDone = true;
        System.out.println("<SERVER> DONE PAYING");
    }
}
