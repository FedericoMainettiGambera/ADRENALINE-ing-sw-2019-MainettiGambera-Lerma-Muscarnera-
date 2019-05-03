package it.polimi.se2018.virtualView.Socket;


import it.polimi.se2018.model.events.ModelViewEvent;
import it.polimi.se2018.virtualView.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ServerSocketVirtualView extends VirtualView {

    private ServerSocket serverSocket;

    private int port;

    private List<ObjectOutputStream> oos;

    private SocketConnectionHandlerVirtualView connectionHandler;

    private Observer controller;

    public void ServerSocketVirtualView(int port, Observer controller){
        this.port = port;

        this.controller = controller;

        try{
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            startServer();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        this.oos = null;
    }

    public void startServer() throws IOException{
        this.connectionHandler = new SocketConnectionHandlerVirtualView(this.serverSocket, this.controller);
        this.connectionHandler.start();
    }

    public void setOos(ArrayList<ObjectOutputStream> oos){
        this.oos = oos;
    }

    public void sendAllClient(Object o){
        this.setOos(this.connectionHandler.getOos());
        if(!(this.oos == null)){
            for (ObjectOutputStream out: this.oos) {
                try {
                    out.writeObject(o);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        ModelViewEvent MVE = (ModelViewEvent)arg;

        this.sendAllClient(MVE);

    }
}
