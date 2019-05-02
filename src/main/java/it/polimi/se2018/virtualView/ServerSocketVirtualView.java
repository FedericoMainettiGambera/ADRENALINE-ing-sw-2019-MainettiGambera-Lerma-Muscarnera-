package it.polimi.se2018.virtualView;


import it.polimi.se2018.model.events.ModelViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ServerSocketVirtualView extends VirtualView{

    private ServerSocket serverSocket;

    private Boolean isServerSocketLive;

    private Socket socket;

    private int port;

    private ObjectOutputStream oos;

    public void ServerSocketVirtualView(int port){
        this.port = port;
        try{
            serverSocket = new ServerSocket(port);
            this.isServerSocketLive = true;
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
    }

    public void CloseServerSocket() throws IOException{
        this.serverSocket.close();
        this.isServerSocketLive = false;
    }

    public void startServer() throws IOException{

        /*BIG PROBLEMS HERE WHIT THE WHILE LOOP, IT BLOCK THE PROGRAM.*/

        while(this.isServerSocketLive){
            try{
                socket = serverSocket.accept();
            }
            catch(IOException e){
                e.printStackTrace();
                try {
                    socket.close();
                }
                catch(IOException a) {
                    a.printStackTrace();
                }
            }

            //Used in the update function
            this.oos = new ObjectOutputStream(socket.getOutputStream());

            //used for the Thread
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            SocketListenerClientHandlerVirtualView sl = new SocketListenerClientHandlerVirtualView(socket, ois);
            //starts the Thread that listen for Object sent from the NetworkHandler.
            sl.start();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //every time the VirtualView is notified it extract the event and send it to the Client.

        ModelViewEvent MVE = (ModelViewEvent)arg;
        try{
            oos.writeObject(MVE);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
