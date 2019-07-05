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

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    private static Logger logger=Logger.getLogger(ClientListenerVirtualView.class.getName());


    public ClientListenerVirtualView(Socket socket, ObjectInputStream ois, ViewControllerEventHandlerContext controller){
        this.socket = socket;
        this.isSocketLive = true;
        this.ois = ois;
        this.addObserver(controller);
    }

    public void closeSocket() throws IOException {
        this.socket.close();
        this.isSocketLive = false;
    }

    public void passOos(ObjectOutputStream oos){
        this.oos = oos;
    }

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
