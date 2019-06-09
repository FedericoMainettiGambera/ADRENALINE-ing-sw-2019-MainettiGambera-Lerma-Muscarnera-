package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;

public class ClientListenerVirtualView extends Observable implements Runnable{

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

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

    @Override
    public void run(){
        while(isSocketLive){
            try {
                this.VCE = (ViewControllerEvent)ois.readObject();
            }
            catch (IOException|ClassNotFoundException e){
                System.err.println("Player has disconnected. Closing ClientListenerVirtualView.");
                break;
            }
            setChanged();
            this.notifyObservers(this.VCE);
        }
    }
}
