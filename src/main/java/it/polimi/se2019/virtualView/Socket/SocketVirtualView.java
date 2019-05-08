package it.polimi.se2019.virtualView.Socket;


import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.events.ModelViewEvent;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SocketVirtualView extends VirtualView {

    private ServerSocket serverSocket;

    private int port;

    private List<ObjectOutputStream> oos;

    private ConnectionHandlerVirtualView connectionHandler;

    private ViewControllerEventHandlerContext controller;

    public SocketVirtualView(ViewControllerEventHandlerContext controller){

        this.controller = controller;

        try{
            serverSocket = new ServerSocket(0);
            this.port = serverSocket.getLocalPort();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.oos = null;
    }

    public void startServer() throws IOException{
        this.connectionHandler = new ConnectionHandlerVirtualView(this.serverSocket, this.controller);
        this.connectionHandler.start();
        System.out.println("<SERVER>Running Server on: " + this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort());
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


    public void sendToClient(ObjectOutputStream oos, Object o){
        try{
            oos.writeObject(o);
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket(){
        return this.serverSocket;
    }

    @Override
    public void update(Observable o, Object arg) {

        ModelViewEvent MVE = (ModelViewEvent)arg;

        this.sendAllClient(MVE);

    }
}
