package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.outputHandler.OutputHandlerGate;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**client side of socket
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class SocketNetworkHandler extends NetworkHandler implements Observer{
    private static final Logger logger=Logger.getLogger(SocketNetworkHandler.class.getName());

    /***/
    private static Socket socket;
    /***/
     private static ObjectOutputStream oos;
    /***/
    public static ObjectOutputStream getOos() {
        return oos;
    }
    /***/
    private static ObjectInputStream ois;


    /***/
    public SocketNetworkHandler(InetAddress inetAddress, int port, View view) throws IOException {

        if(Controller.getUserInterface().equalsIgnoreCase("CLI")) {
            logger.info("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());

            logger.info("<CLIENT>Trying to connect to: " + inetAddress.getHostAddress() + ":" + port);
        }

        updateStreamsAndSocket(inetAddress, port);

        if(Controller.getUserInterface().equalsIgnoreCase("CLI")) {
            logger.info("<CLIENT>Connected to: " + inetAddress.getHostAddress() + ":" + port);
        }



        ServerListenerNetworkHandler sl = new ServerListenerNetworkHandler(socket, ois, view);
        new Thread(sl).start();
    }

    /***/
   private static void  updateStreamsAndSocket(InetAddress inetAddress, int port) throws IOException {

        socket = new Socket(inetAddress, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


    }
    /***/
    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        try {
            oos.writeObject(viewControllerEvent);
        } catch (IOException e) {
            OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
        }
    }
    /***/
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
