package it.polimi.se2019.controller;

import java.io.IOException;
import java.rmi.NotBoundException;

public class testRMIClient {
    public static void main(String[] args) throws NotBoundException {

        Controller user1 = new Controller();

        try {
            user1.startGameWithRMIAsClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
