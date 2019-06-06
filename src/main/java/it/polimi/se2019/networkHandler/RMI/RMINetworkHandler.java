package it.polimi.se2019.networkHandler.RMI;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.RMI.RMIInterface;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Scanner;

public class RMINetworkHandler extends NetworkHandler{

    public static Client client;
    RMIObsHandler rmiObsHandler;


    public RMINetworkHandler(String name, int port, View view) throws RemoteException, NotBoundException, UnknownHostException {
        rmiObsHandler = new RMIObsHandler(view);


        System.out.println("<CLIENT> " + "~~ Welcome To RMI Adrenaline Server~~   " + "Ready to Play? Cool! just a few steps before!");
        System.out.println("<CLIENT> " + "Connecting To RMI Server...\n");


        Registry reg = LocateRegistry.getRegistry(name, port);
        RMIInterface rmiInterface = (RMIInterface) reg.lookup("http://AdrenalineServer:1099");

        //if (rmiInterface.numberOfConnection().getNumber() + 1 < 3) {
        try {
            //TODO here it has some problem sometimes
            if (rmiInterface.numberOfConnection() <= GameConstant.maxNumberOfPlayerPerGame) {

                client = new Client(rmiInterface, rmiInterface.getRmiIdentifier());

                System.out.println("<CLIENT> your RMIIdentifier in class NetworkHandler is: " + rmiInterface.getRmiIdentifier());
                rmiInterface.addClientToList(client);
                new Thread(client).start();
                client.setRmiObsHandler(rmiObsHandler);


            } else {
                System.out.println("<CLIENT> " + "Sorry you cant play we are full, " + "number of connection is already" + rmiInterface.numberOfConnection());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;

        try {
            this.client.returnInterface().sendToServer(VCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        //todo
        //from internet:
        //Your client's disconnect method should be calling the server's unregister method
        // (ie it should be basically the same as the client's connect method) rather than
        // trying to unregister the server object from the RMI Registry.
    }

}
