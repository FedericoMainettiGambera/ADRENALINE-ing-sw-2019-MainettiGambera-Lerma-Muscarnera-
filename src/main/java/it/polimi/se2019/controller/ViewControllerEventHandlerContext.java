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
 * @author LudoLerma
 * @author FedericoMainettiGambera
 */
public class  ViewControllerEventHandlerContext implements Observer{

    /**state to be set*/
    private static State state;

    /**@return state*/
    public static State getState() {
        return state;
    }

    /**debug aims*/
    private static String stackOfStatesAndTimers = "Stack of states amd timers";

    /**debug aims*/
    public static void addElementTOStackOfStatesAndTimers(Object o, String s){
        stackOfStatesAndTimers += "\n" + o.getClass().toString() + " | " + s;
    }

    /**debug aims*/
    public static void addStringToElementStackPane(String s){
        stackOfStatesAndTimers+= "\n" + s;
    }
    /**debug aims*/
    public static void printStackOfStatesAndTImers(){
        System.err.println(stackOfStatesAndTimers);
    }

    /**state that handles the payment process*/
    private static ChooseHowToPayState paymentProcess;

    /**@return paymentProcess*/
    public static ChooseHowToPayState getPaymentProcess() {
        return paymentProcess;
    }

    /**contain information on the payment process*/
    public static void setPaymentProcess(ChooseHowToPayState paymentProcess) {
        ViewControllerEventHandlerContext.paymentProcess = paymentProcess;
        addElementTOStackOfStatesAndTimers(paymentProcess, "payment process setted");
    }

    /**reference to the socket server */
    private static SocketVirtualView socketVV;

    /**@return socketVV */
    public static SocketVirtualView getSocketVV() {
        return socketVV;
    }

    /**@param  socketVV to set the socketVV attribute*/
    static void setSocketVV(SocketVirtualView socketVV) {
        ViewControllerEventHandlerContext.socketVV = socketVV;
    }

    /**reference to rmi server*/
    private static RmiVirtualView rmiVirtualView;

    /**@param rmiVirtualView set the rmiVirtualView attribute*/
    public static void setRmiVirtualView(RmiVirtualView rmiVirtualView) {
        ViewControllerEventHandlerContext.rmiVirtualView = rmiVirtualView;
    }

    /**@return rmiVirtualView */
    public static RmiVirtualView getRmiVirtualView() {
        return rmiVirtualView;
    }

    /**@param nextState the state to be set */
    public static void setNextState(State nextState) {
        state = nextState;
        String[] stringSplittedState= nextState.getClass().toString().split("\\.");
        String stateString = stringSplittedState[stringSplittedState.length-1];
        ModelGate.getModel().setCurrentState(stateString);
        addElementTOStackOfStatesAndTimers(nextState, "nextState setted");
    }

    /**@param o observer that called the notify
     *@param arg contains the event, if the event is of type of paymentInformation,
     *          the getPaymentProcess().doPayment function is called
     *          if else, the doAction of the current state is called
     *          (update is called when a viewController event is received, it means a askForInput
     *           has been answered)*/
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