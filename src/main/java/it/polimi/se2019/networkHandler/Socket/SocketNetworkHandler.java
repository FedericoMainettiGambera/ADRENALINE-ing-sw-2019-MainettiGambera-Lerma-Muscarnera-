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

    /**socket of the server*/
    private static Socket socket;
    /**where to send the objects*/
     private static ObjectOutputStream oos;
    /**@return oos*/
    public static ObjectOutputStream getOos() {
        return oos;
    }
    /**from where the object are received*/
    private static ObjectInputStream ois;


    /**constructor,
     * @param inetAddress to get addresses
     * @param view to be passed to serverListenerNetworkHandler
     * @param port port on which the connection in built
     * @throws IOException socket*/
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

    /**update
     * @param port port of the socket
     * @param inetAddress  address of the socket
     * the socket
     * @throws IOException for sockets reason
     * */
   private static void  updateStreamsAndSocket(InetAddress inetAddress, int port) throws IOException {

        socket = new Socket(inetAddress, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


    }
    /**@param arg the object to be converted to viewcontrollerevent
     *             and sent to server*/
    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        try {
            oos.writeObject(viewControllerEvent);
        } catch (IOException e) {
            OutputHandlerGate.getCorrectOutputHandler(OutputHandlerGate.getUserIterface()).cantReachServer();
        }
    }
    /**in case of disconnection
     * the socket will be closed*/
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
