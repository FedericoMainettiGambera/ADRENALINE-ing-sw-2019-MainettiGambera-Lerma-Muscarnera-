package it.polimi.se2019.networkHandler.RMIREDO;

import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.RMIREDO.RmiInterface;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**client side of rmi connection
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class RmiNetworkHandler extends UnicastRemoteObject implements RmiInterface, Observer {

    private static final Logger logger=Logger.getLogger(SocketNetworkHandler.class.getName());
    /**the port is always 1099*/
    private int port;
    /**the ip adress*/
    private String ip;
    /**view reference*/
    private View view;
    /**server reference*/
    private static RmiInterface server;
    /**@return server*/
    public static RmiInterface getServer() {
        return server;
    }
    /**@param server to set server attribute*/
    public static void setServer(RmiInterface server) {
        RmiNetworkHandler.server = server;
    }
    /**constructor,
     * @param ip to set ip attribute
     * @param port to set port attribute
     * @param view to set view attribute
     *looks for the registry and get a reference to the server
     * */
    public RmiNetworkHandler(String ip, int port, View view) throws IOException {
        this.view = view;

        this.port = port;
        this.ip = ip;

        logger.info("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());

        logger.info("<CLIENT>Trying to connect to: " + this.ip + ":" + this.port);


        Registry reg = LocateRegistry.getRegistry(ip, 1099);

        try {

            server=(RmiInterface)reg.lookup("http://AdrenalineServer:1099");


        } catch (NotBoundException e) {
           logger.log(Level.SEVERE, "EXCEPTION",e);
        }


        RmiNetworkHandler.server.connect(this);

    }
    /**@param o the object to be send to the server*/
    @Override
    public void send(Object o) {

        (new Thread(new RmiServerListenerNetworkHandler(this.view, o))).start();
    }
    /***/
    @Override
    public void connect(RmiInterface client) {

    }
    /**@param arg the object to be converted to viewcontrollerevent
     *             and sent to server*/
    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        sendToServer(viewControllerEvent);
    }
    /**@param o the object to be sent to server*/
    private static void sendToServer(Object o){
        //send to server
        try {
            RmiNetworkHandler.server.send(o);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }


}
