package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.util.Observable;

public class RmiClientListenerVirtualView extends Observable implements Runnable {

    private Object objectReceived;

    public RmiClientListenerVirtualView(Object o, ViewControllerEventHandlerContext controller){
        this.objectReceived = o;
        this.addObserver(controller);
    }

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

    public void resetPlayer(ReconnectionEvent reconnectionEvent){
        String nickname = reconnectionEvent.getListOfAFKPlayers().get(0);
        String networkConnection = reconnectionEvent.getListOfAFKPlayers().get(1);

        System.out.println("<SERVER-socket> received the Reconnection event for player: " + nickname);

        //reset previous connection
        ModelGate.model.getPlayerList().getPlayer(nickname).setOos(null);

        //TODO
        //set new connection
        //esempio:
        //      ModelGate.model.getPlayerList().getPlayer(nickname).setRmiIdentifier(0);
        //      ModelGate.model.getPlayerList().getPlayer(nickname).setRmiInterface(null);
        //      (probabilemente l'RmiIdentifier è INUTILE, comunque queste cose dipendo strettamente dall'RMI, quindi le lascio fare a te Ludo)
        //      (p.s. modifica la classe player come preferisci)

        //set afk false and update the Model to the reconnected Client
        RmiVirtualView.sendToClientEvenAFK(ModelGate.model.getPlayerList().getPlayer(nickname),(new ModelViewEvent(ModelGate.model.buildGameV(), ModelViewEventTypes.resetGame)));
        ModelGate.model.getPlayerList().getPlayer(nickname).setAFKWIthoutNotify(false);
    }
}
