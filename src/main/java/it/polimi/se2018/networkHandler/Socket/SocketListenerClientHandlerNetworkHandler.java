package it.polimi.se2018.networkHandler.Socket;

import it.polimi.se2018.model.events.ModelViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class SocketListenerClientHandlerNetworkHandler extends Observable implements Runnable {

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

    private Observer view;

    public SocketListenerClientHandlerNetworkHandler(Socket socket, ObjectInputStream ois, Observer view){
            this.socket = socket;
            this.isSocketLive = true;
            this.ois = ois;
            this.view = view;

            this.addObserver(this.view);
    }

    public void closeSocket(){
        try {
            this.socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.isSocketLive=false;
    }

    @Override
    public void run() {
        while(isSocketLive){
            try {
                ModelViewEvent MVE = (ModelViewEvent)this.ois.readObject();

                this.setChanged();
                this.notifyObservers(MVE);
            }
            catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
