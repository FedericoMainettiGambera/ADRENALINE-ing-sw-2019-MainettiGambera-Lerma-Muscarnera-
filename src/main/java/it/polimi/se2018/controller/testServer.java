package it.polimi.se2018.controller;

import java.io.IOException;

public class testServer {
    public static void main(String[] args){
        Controller user1 = new Controller();

        user1.startGameWithSocketAsServer();

    }
}
