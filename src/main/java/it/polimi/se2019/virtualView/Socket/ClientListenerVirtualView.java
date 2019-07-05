package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
/**listens to events from the clients
 * @author LudoLerma &
 * @author FedericoMainettiGambera*/
public class ClientListenerVirtualView extends Observable implements Runnable{

    /**if the socket is active*/
    private boolean isSocketLive;

    /**to receive input from client*/
    private ObjectInputStream ois;
    /**oos to communicate with client*/
    private ObjectOutputStream oos;


    private static Logger logger=Logger.getLogger(ClientListenerVirtualView.class.getName());

    /**@param controller to be notified when an event comes
     * @param ois the stream from which listens to the event that arrives
     * @param socket current socket
     * constructor*/
    public ClientListenerVirtualView(Socket socket, ObjectInputStream ois, ViewControllerEventHandlerContext controller){
        Socket socket1 = socket;
        this.isSocketLive = true;
        this.ois = ois;
        this.addObserver(controller);
    }



    /**@param oos sets the oos attribute*/
    public void passOos(ObjectOutputStream oos){
        this.oos = oos;
    }

    /**converts the object received to a vieweventhandler and notify the observers
     * it happens in a thread because the server needs to keep listening to all the incoming events
     * in case the event is a reconnection event, the resetplayer function is called with
     * the event as a parameter*/
    @Override
    public void run(){
        while(isSocketLive) {

            ViewControllerEvent viewControllerEvent;
            Object o = null;
            try {
                o = ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error in read object from class ClientListenerVirtualView.");
                break;
            } catch (InternalError e) {
                System.err.println("Something went Wrong in class ClientListenerVirtualView..." + e.getMessage());
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }

            if (o != null) {
                if (o.getClass().toString().contains("ViewControllerEvent")) {
                    viewControllerEvent = (ViewControllerEvent) o;
                    setChanged();
                    this.notifyObservers(viewControllerEvent);
                } else if (o.getClass().toString().contains("ReconnectionEvent")) {
                    ReconnectionEvent reconnectionEvent = (ReconnectionEvent) o;
                    this.resetPlayer(reconnectionEvent);
                }
                else {
                    logger.severe("Received Event not recognized. class: ClientListenerVirtualView: object received->" + o.getClass());
                }
            }
        }
    }
    /**@param reconnectionEvent, contains the name of the afk player,
     * they are set not afk and their connection restored
     * */
    public void resetPlayer(ReconnectionEvent reconnectionEvent){
        String nickname = reconnectionEvent.getListOfAFKPlayers().get(0);
        String networkConnection = reconnectionEvent.getListOfAFKPlayers().get(1);

        System.out.println("<SERVER-socket> received the Reconnection event for player: " + nickname);
        //reset previous connection
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setRmiIdentifier(0);
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setRmiInterface(null);
        //set new connection
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setOos(this.oos);
        //set afk false
        SocketVirtualView.sendToClientEvenAFK(ModelGate.getModel().getPlayerList().getPlayer(nickname),(new ModelViewEvent(ModelGate.getModel().buildGameV(), ModelViewEventTypes.resetGame)));
        ModelGate.getModel().getPlayerList().getPlayer(nickname).setAFKWIthoutNotify(false);
    }
}
