package it.polimi.se2019.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testRMIServer {
    public static void main(String[] args){

        System.setProperty("java.rmi.Server.hostname", "192.168.x.x");

        Controller user1 = new Controller();

        try {
            user1.startGameWithRMIAsServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
