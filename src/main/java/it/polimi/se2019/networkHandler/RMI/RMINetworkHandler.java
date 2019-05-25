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

    Client client;

    public RMINetworkHandler(String name, int port, View view) throws RemoteException, NotBoundException {

        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        Client client;


        System.out.println("\n~~ Welcome To RMI Adrenaline Server~~\n"+"Ready to Play?\n Cool!\njust a few steps before!");
        System.out.print("Inserisci un nickname: ");
        clientName = scanner.nextLine();
        System.out.println("\nConnecting To RMI Server...\n");

        Registry reg=LocateRegistry.getRegistry("localhost", port);
        RMIInterface rmiInterface = (RMIInterface) reg.lookup(name+port);

        if( rmiInterface.numberOfConnection().getNumber()+1<3    )
        { client= new Client(rmiInterface, clientName );
            rmiInterface.addClientToList(client);
            new Thread(client).start();

        }

        else {System.out.println("Sorry you cant play we are full\n"+"number of connection is already\n"+rmiInterface.numberOfConnection());}


    }
}
