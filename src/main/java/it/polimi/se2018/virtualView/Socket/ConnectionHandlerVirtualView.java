package it.polimi.se2018.virtualView.Socket;

import it.polimi.se2018.controller.ViewControllerEventHandlerContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;

public class ConnectionHandlerVirtualView extends Thread {

    private ServerSocket serverSocket;

    private boolean isServerSocketLive;

    private ArrayList<ObjectOutputStream> oos;

    private Socket tempSocket;

    private ViewControllerEventHandlerContext controller;


    public ConnectionHandlerVirtualView(ServerSocket serverSocket, ViewControllerEventHandlerContext controller){
        this.serverSocket = serverSocket;
        this.isServerSocketLive = true;
        this.oos = new ArrayList<>();
        this.tempSocket = null;
        this.controller = controller;
    }

    public void CloseServerSocket() throws IOException{
        this.serverSocket.close();
        this.isServerSocketLive = false;
    }

    public ArrayList<ObjectOutputStream> getOos(){
        return this.oos;
    }

    @Override
    public void run(){
        while(this.isServerSocketLive){
            try{
                this.tempSocket = serverSocket.accept();
                System.out.println("<SERVER>New Connection from: " + this.tempSocket.getInetAddress().getHostAddress());
            }
            catch(IOException e){
                e.printStackTrace();
                try {
                    this.tempSocket.close();
                }
                catch(IOException a) {
                    a.printStackTrace();
                }
            }

            //ObjectOutputStream
            try {
                this.oos.add(new ObjectOutputStream(this.tempSocket.getOutputStream()));
            }
            catch(IOException e){
                e.printStackTrace();
            }

            //ObjectInputStream
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(this.tempSocket.getInputStream());
            }
            catch (IOException e){
                e.printStackTrace();
            }
            ClientListenerVirtualView sl = new ClientListenerVirtualView(this.tempSocket, ois, controller);
            //starts the Thread that listen for Object sent from the NetworkHandler.
            new Thread(sl).start();
        }
    }
}
