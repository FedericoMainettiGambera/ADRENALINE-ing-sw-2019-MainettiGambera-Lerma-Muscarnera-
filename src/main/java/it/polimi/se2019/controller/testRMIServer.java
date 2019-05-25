package it.polimi.se2019.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class testRMIServer {
    public static void main(String[] args){

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
