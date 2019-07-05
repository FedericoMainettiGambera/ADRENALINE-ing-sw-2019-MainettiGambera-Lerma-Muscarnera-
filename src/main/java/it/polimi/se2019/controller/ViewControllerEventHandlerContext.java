package it.polimi.se2019.controller;

import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.controller.statePattern.State;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPaymentInformation;
import it.polimi.se2019.virtualView.RMIREDO.RmiVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.util.Observable;
import java.util.Observer;

/**
 * changes the context of the game
 */
public class  ViewControllerEventHandlerContext implements Observer{

    private static State state;

    public static State getState() {
        return state;
    }

    private static String stackOfStatesAndTimers = "Stack of states amd timers";

    public static void addElementTOStackOfStatesAndTimers(Object o, String s){
        stackOfStatesAndTimers += "\n" + o.getClass().toString() + " | " + s;
        //System.err.println(stackOfStatesAndTimers);
    }
    public static void addElementTOStackOfStatesAndTimers(Object o){
        stackOfStatesAndTimers += "\n" + o.getClass().toString();
        //System.err.println(stackOfStatesAndTimers);
    }

    public static void addStringToElementStackPane(String s){
        stackOfStatesAndTimers+= "\n" + s;
    }
    public static void printStackOfStatesAndTImers(){
        System.err.println(stackOfStatesAndTimers);
    }

    private static ChooseHowToPayState paymentProcess;

    public static ChooseHowToPayState getPaymentProcess() {
        return paymentProcess;
    }

    public static void setPaymentProcess(ChooseHowToPayState paymentProcess) {
        ViewControllerEventHandlerContext.paymentProcess = paymentProcess;
        addElementTOStackOfStatesAndTimers(paymentProcess, "payment process setted");
    }

    private static SocketVirtualView socketVV;

    public static SocketVirtualView getSocketVV() {
        return socketVV;
    }

     static void setSocketVV(SocketVirtualView socketVV) {
        ViewControllerEventHandlerContext.socketVV = socketVV;
    }

    private static RmiVirtualView rmiVirtualView;

    public static void setRmiVirtualView(RmiVirtualView rmiVirtualView) {
        ViewControllerEventHandlerContext.rmiVirtualView = rmiVirtualView;
    }

    public static RmiVirtualView getRmiVirtualView() {
        return rmiVirtualView;
    }

    public static void setNextState(State nextState) {
        state = nextState;
        String[] stringSplittedState= nextState.getClass().toString().split("\\.");
        String stateString = stringSplittedState[stringSplittedState.length-1];
        ModelGate.getModel().setCurrentState(stateString);
        addElementTOStackOfStatesAndTimers(nextState, "nextState setted");
    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        if(viewControllerEvent.getClass().toString().contains("PaymentInformation")){
            getPaymentProcess().doPayment((ViewControllerEventPaymentInformation)viewControllerEvent);
            addElementTOStackOfStatesAndTimers(arg, "payment received, notifying the state: " + getState().getClass().toString());
        }
        else {
            getState().doAction(viewControllerEvent);
            addElementTOStackOfStatesAndTimers(arg, "answer received, notifying the state: " + getState().getClass().toString());
        }
    }
}