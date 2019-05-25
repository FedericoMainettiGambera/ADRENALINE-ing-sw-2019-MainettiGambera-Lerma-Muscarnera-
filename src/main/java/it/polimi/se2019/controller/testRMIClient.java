package it.polimi.se2019.controller;

import java.io.IOException;

public class testRMIClient {
    public static void main(String[] args){

        Controller user1 = new Controller();

        try {
            user1.startGmeWithRMIAsClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
