package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class SocketNetworkHandler extends NetworkHandler implements Observer{

    private static Socket socket;

    private int port;

    private InetAddress inetAddress;

    public static ObjectOutputStream oos;

    private static ObjectInputStream ois;

    private View view;

    public SocketNetworkHandler(InetAddress inetAddress, int port, View view) throws IOException {
        this.view = view;

        this.port = port;
        this.inetAddress = inetAddress;

        System.out.println("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());

        System.out.println("<CLIENT>Trying to connect to: " + this.inetAddress.getHostAddress() + ":" + this.port);

        this.socket = new Socket(this.inetAddress, this.port);
        System.out.println("<CLIENT>Connected to: " + this.inetAddress.getHostAddress() + ":" + this.port);


        this.oos = new ObjectOutputStream(this.socket.getOutputStream());


        this.ois = new ObjectInputStream(this.socket.getInputStream());

        ServerListenerNetworkHandler sl = new ServerListenerNetworkHandler(socket, ois, view);
        new Thread(sl).start();
    }


    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;
        try {
            this.oos.writeObject(VCE);
        } catch (IOException e) {
            OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
        }
    }

    public static void disconnect() throws IOException {
        //note that closing the socket will close the input streams and output streams too.
        socket.close();
        System.exit(0);
        // if i use this there are problem with the buffer reader
        // OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
    }
}
