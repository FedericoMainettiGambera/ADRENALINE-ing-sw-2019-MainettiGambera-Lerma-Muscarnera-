package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.ModelViewEvent;
import it.polimi.se2019.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ServerListenerNetworkHandler extends Observable implements Runnable {

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

    private View view;

    public ServerListenerNetworkHandler(Socket socket, ObjectInputStream ois, View view){
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
                Event E = (Event)this.ois.readObject();
                this.setChanged();
                this.notifyObservers(E);
            }
            catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
