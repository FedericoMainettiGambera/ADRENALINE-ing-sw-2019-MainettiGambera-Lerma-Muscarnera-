package it.polimi.se2019.controller;

import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.controller.statePattern.State;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPaymentInformation;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.util.Observable;
import java.util.Observer;

public class  ViewControllerEventHandlerContext implements Observer{

    public static State state;

    public static ChooseHowToPayState paymentProcess;

    public static SocketVirtualView socketVV;
    public static RMIVirtualView RMIVV;

    public static void setNextState(State nextState) {
        state = nextState;
        ModelGate.model.setCurrentState(state.getClass().toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;
        System.err.println("I HAVE BEEN NOTIFIED WITH THE ELEMENT: " + arg.getClass());
        if(VCE.getClass().toString().contains("PaymentInformation")){
            paymentProcess.doPayment((ViewControllerEventPaymentInformation)VCE);
        }
        else {
            state.doAction(VCE);
        }
    }
}