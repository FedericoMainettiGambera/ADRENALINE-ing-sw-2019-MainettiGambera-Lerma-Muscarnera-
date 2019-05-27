package it.polimi.se2019.networkHandler.RMI;

import it.polimi.se2019.networkHandler.NetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.RMI.RMIInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMINetworkHandler extends NetworkHandler{

    public static Client client;
    RMIObsHandler rmiObsHandler;


    public RMINetworkHandler(String name, int port, View view) throws RemoteException, NotBoundException {
        rmiObsHandler=new RMIObsHandler(view);



        System.out.println("<CLIENT> " + "~~ Welcome To RMI Adrenaline Server~~   "+"Ready to Play? Cool! just a few steps before!");
        System.out.println("<CLIENT> " + "Connecting To RMI Server...\n");

        Registry reg=LocateRegistry.getRegistry("localhost", port);
        RMIInterface rmiInterface = (RMIInterface) reg.lookup(name+port);



        if( rmiInterface.numberOfConnection().getNumber()+1<3    )
        { client= new Client(rmiInterface, rmiInterface.getRmiIdentifier() );
        System.out.println("rdmiIdentifier in Network handler is" + rmiInterface.getRmiIdentifier());
            rmiInterface.addClientToList(client);
            new Thread(client).start();
            client.setRmiObsHandler(rmiObsHandler);
        }

        else {System.out.println("<CLIENT> " + "Sorry you cant play we are full, "+"number of connection is already"+rmiInterface.numberOfConnection());}


    }
}
