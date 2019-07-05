package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.util.Observable;
/**listens to events from the clients
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class RmiClientListenerVirtualView extends Observable implements Runnable {

    /**attribute to save object received from the client*/
    private Object objectReceived;

    /**@param controller to be notified when an event comes
     * @param o the event arrived
     * constructor*/
    public RmiClientListenerVirtualView(Object o, ViewControllerEventHandlerContext controller){
        this.objectReceived = o;
        this.addObserver(controller);
    }

    /**converts the object received to a vieweventhandler and notify the observers
     * it happens in a thread because the server needs to keep listening to all the incoming events
     * in case the event is a reconnection event, the resetplayer function is called with
     * the event as a parameter*/
    @Override
    public void run(){
        ViewControllerEvent viewControllerEvent;
        if (this.objectReceived != null) {
            if (this.objectReceived.getClass().toString().contains("ViewControllerEvent")) {
                viewControllerEvent = (ViewControllerEvent) this.objectReceived;
                setChanged();
                this.notifyObservers(viewControllerEvent);
            } else if (this.objectReceived.getClass().toString().contains("ReconnectionEvent")) {
                ReconnectionEvent reconnectionEvent = (ReconnectionEvent) this.objectReceived;
                this.resetPlayer(reconnectionEvent);
            }
            else {
                System.err.println("Received Event not recognized. class: ClientListenerVirtualView: object received->" + this.objectReceived.getClass());
            }
        }
    }

    /**@param reconnectionEvent, contains the name of the afk player,
     * they are set not afk and their connection restored
     * */
    public void resetPlayer(ReconnectionEvent reconnectionEvent){
        String nickname = reconnectionEvent.getListOfAFKPlayers().get(0);

        System.out.println("<SERVER-socket> received the Reconnection event for player: " + nickname);

        //reset previous connection
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setOos(null);

        ModelGate.getModel().getPlayerList().getPlayer(nickname).setRmiInterface(null);

        ModelGate.getModel().getPlayerList().getPlayer(nickname).setRmiInterface(reconnectionEvent.getClient());

        //set afk false and update the Model to the reconnected Client
        RmiVirtualView.sendToClientEvenAFK(ModelGate.getModel().getPlayerList().getPlayer(nickname),(new ModelViewEvent(ModelGate.getModel().buildGameV(), ModelViewEventTypes.resetGame)));
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setAFKWIthoutNotify(false);
    }
}
