package it.polimi.se2019.networkHandler.RMI;

import it.polimi.se2019.virtualView.RMI.RMIInterface;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Logout extends Thread {
    private Client client;
    private boolean checkLogout;
    Thread t;

    public Logout(Client client, Thread thread1) {
        this.client = client;
        checkLogout=false;
        Thread t=thread1;
    }

    @Override
    public void run() {
        String string;
        Scanner scanner = new Scanner(System.in);
        string = scanner.nextLine();

        while (!string.equalsIgnoreCase("Logout")) {

            string = scanner.nextLine();

        }
        try {
            client.returnInterface().removeClient(client.getRmiIdentifier());
            this.t.interrupt();
            checkLogout=true;
        } catch (Exception e){

        }
        try {
            System.out.println("ciao! by logout thread"+client.getRmiIdentifier());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentThread().interrupt();


    }
}



