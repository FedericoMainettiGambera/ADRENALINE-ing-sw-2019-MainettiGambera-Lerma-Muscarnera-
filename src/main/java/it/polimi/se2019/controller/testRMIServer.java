package it.polimi.se2019.controller;

public class testRMIServer {
    public static void main(String[] args){

        Controller user1 = new Controller();

        user1.startGameWithRMIAsServer();

    }
}
