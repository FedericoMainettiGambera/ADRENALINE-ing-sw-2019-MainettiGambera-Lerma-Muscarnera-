package it.polimi.se2018.controller;

import java.io.IOException;

public class testClient {
    public static void main(String[] args){
        Controller user2 = new Controller();

        try {
            user2.startGameWithSocketAsClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
