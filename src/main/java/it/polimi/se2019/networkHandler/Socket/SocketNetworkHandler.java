package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class SocketNetworkHandler extends NetworkHandler implements Observer{
    private static final Logger logger=Logger.getLogger(SocketNetworkHandler.class.getName());

    private static Socket socket;

    private int port;

    private InetAddress inetAddress;

    public static ObjectOutputStream oos;

    private static ObjectInputStream ois;

    public View view;

    public SocketNetworkHandler(InetAddress inetAddress, int port, View view) throws IOException {
        this.view = view;

        this.port = port;
        this.inetAddress = inetAddress;

        logger.info("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());

        logger.info("<CLIENT>Trying to connect to: " + this.inetAddress.getHostAddress() + ":" + this.port);

        this.socket = new Socket(this.inetAddress, this.port);
        logger.info("<CLIENT>Connected to: " + this.inetAddress.getHostAddress() + ":" + this.port);


        this.oos = new ObjectOutputStream(this.socket.getOutputStream());


        this.ois = new ObjectInputStream(this.socket.getInputStream());

        ServerListenerNetworkHandler sl = new ServerListenerNetworkHandler(socket, ois, view);
        new Thread(sl).start();
    }


    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        try {
            this.oos.writeObject(viewControllerEvent);
        } catch (IOException e) {
            OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
        }
    }

    public static void disconnect() {
        //note that closing the socket will close the input streams and output streams too.
        try {
            socket.close();
        } catch (IOException e) {
           logger.warning("disconnection went wrong" + e.getCause());
        }
        // if i use this there are problem with the buffer reader
        // OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
    }
}
