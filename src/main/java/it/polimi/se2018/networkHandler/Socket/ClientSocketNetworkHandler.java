package it.polimi.se2018.networkHandler.Socket;

import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.networkHandler.NetworkHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientSocketNetworkHandler extends NetworkHandler implements Observer{

    private Socket socket;

    private int port;

    private InetAddress inetAddress;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    private Observer view;

    public ClientSocketNetworkHandler(InetAddress inetAddress, int port, Observer view){
        this.view = view;

        this.port = port;
        this.inetAddress = inetAddress;

        try {
            this.socket = new Socket(this.inetAddress, this.port);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try {
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.ois = new ObjectInputStream(this.socket.getInputStream());

            SocketListenerClientHandlerNetworkHandler sl = new SocketListenerClientHandlerNetworkHandler(socket, ois, view);

            new Thread(sl).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;

        try {
            this.oos.writeObject(VCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
