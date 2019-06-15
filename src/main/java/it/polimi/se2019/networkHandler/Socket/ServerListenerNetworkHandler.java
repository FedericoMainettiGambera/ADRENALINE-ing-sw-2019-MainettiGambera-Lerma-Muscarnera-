package it.polimi.se2019.networkHandler.Socket;

import com.sun.jmx.remote.internal.ArrayQueue;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.selectorEvents.SelectorEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEventWeaponCards;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.components.ViewModelGate;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static it.polimi.se2019.model.enumerations.SelectorEventTypes.askGrabStuffGrabWeapon;

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


    @Override
    public void run() {
        while(this.socket.isConnected()){
            try {
                Event E = (Event)this.ois.readObject();
                this.setChanged();
                this.notifyObservers(E);
            }
            catch (IOException|ClassNotFoundException e) {
                OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
                break;
            }
        }
    }
}
