package it.polimi.se2018.virtualView.Socket;

import it.polimi.se2018.model.events.ViewControllerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientListenerVirtualView extends Observable implements Runnable{

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

    private ViewControllerEvent VCE;

    public ClientListenerVirtualView(Socket socket, ObjectInputStream ois, Observer controller){
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
                e.printStackTrace();
            }
            //Should notify the controller using Observer/Observable Pattern and the method notifyObservers(this.VCE).
            setChanged();
            this.notifyObservers(this.VCE);
        }
    }
}
