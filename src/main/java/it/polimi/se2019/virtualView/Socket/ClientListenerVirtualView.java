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

public class ClientListenerVirtualView extends Observable implements Runnable{

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private ViewControllerEvent VCE;

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
        while(isSocketLive){
            Object o = null;
            try {
                o = ois.readObject();
            }
            catch (IOException|ClassNotFoundException e){
                System.err.println("Error in read object from class ClientListenerVirtualView.");
                break;
            }
            catch (InternalError e){
                System.err.println("Something went Wrong in class ClientListenerVirtualView..." + e.getMessage());
                e.printStackTrace();
            }

            if(o.getClass().toString().contains("ViewControllerEvent")) {
                this.VCE = (ViewControllerEvent) o;
                setChanged();
                this.notifyObservers(this.VCE);
            }
            else if(o.getClass().toString().contains("ReconnectionEvent")){
                ReconnectionEvent RE = (ReconnectionEvent) o;
                resetPlayer(RE);
            }
            else{
                System.err.println("Received Event not recognized. class: ClientListenerVirtualView: object received->" + o.getClass());
            }
        }
    }

    public void resetPlayer(ReconnectionEvent RE){
        String nickname = RE.getListOfAFKPlayers().get(0);
        String networkConnection = RE.getListOfAFKPlayers().get(1);

        System.out.println("<SERVER-socket> received the Reconnection event for player: " + nickname);
        //reset previous connection
        ModelGate.model.getPlayerList().getPlayer(nickname).setRmiIdentifier(0);
        ModelGate.model.getPlayerList().getPlayer(nickname).setRmiInterface(null);
        //set new connection
        ModelGate.model.getPlayerList().getPlayer(nickname).setOos(this.oos);
        //set afk false
        SocketVirtualView.sendToClientEvenAFK(ModelGate.model.getPlayerList().getPlayer(nickname),(new ModelViewEvent(ModelGate.model.buildGameV(), ModelViewEventTypes.resetGame)));
        ModelGate.model.getPlayerList().getPlayer(nickname).setAFKWIthoutNotify(false);
    }
}
