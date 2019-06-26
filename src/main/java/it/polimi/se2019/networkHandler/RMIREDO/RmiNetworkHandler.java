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

public class RmiNetworkHandler extends UnicastRemoteObject implements RmiInterface, Observer {

    private static final Logger logger=Logger.getLogger(SocketNetworkHandler.class.getName());

    private int port;

    private String ip;

    private View view;

    public static RmiInterface server;

    public RmiNetworkHandler(String ip, int port, View view) throws IOException {
        this.view = view;

        this.port = port;
        this.ip = ip;

        logger.info("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());

        logger.info("<CLIENT>Trying to connect to: " + this.ip + ":" + this.port);


        Registry reg = LocateRegistry.getRegistry(ip, 1099);

        try {

            this.server=(RmiInterface)reg.lookup("http://AdrenalineServer:1099");


        } catch (NotBoundException e) {
           logger.log(Level.SEVERE, "EXCEPTION",e);
        }


        RmiNetworkHandler.server.connect(this);

    }

    @Override
    public void send(Object o) {
        //fa partire un thread: RmiServerListenerNetworkHandler
        (new Thread(new RmiServerListenerNetworkHandler(this.view, o))).start();
    }

    @Override
    public void connect(RmiInterface client) {
        //per ora lascia vuoto
        //never Used !!!
        //  sono solo idee ma:
        //      lancia un eccezione (?) oppure...
        //      ...potremmo usarla malvagiamente per disconnettere la gente
    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent viewControllerEvent = (ViewControllerEvent) arg;
        sendToServer(viewControllerEvent);
    }

    public static void sendToServer(Object o){
        //send to server
        try {
            RmiNetworkHandler.server.send(o);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
    }


}
