package it.polimi.se2018.virtualView;

import it.polimi.se2018.model.events.ViewControllerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketListenerClientHandlerVirtualView extends Thread {

    private Socket socket;

    private boolean isSocketLive;

    private ObjectInputStream ois;

    private ViewControllerEvent VCE;

    public SocketListenerClientHandlerVirtualView(Socket socket, ObjectInputStream ois){
        this.socket = socket;
        this.isSocketLive = true;
        this.ois = ois;
    }

    public void closeSocket() throws IOException {
        this.socket.close();
        this.isSocketLive = false;
    }

    @Override
    public void run(){
        while(isSocketLive){
            //read from ois an object;
            //cast the object in an ViewControllerEvent and saves it in VCE;
            //notify the controller;
        }
    }
}
