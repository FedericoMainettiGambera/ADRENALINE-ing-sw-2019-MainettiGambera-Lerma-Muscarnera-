package it.polimi.se2019.controller;


import it.polimi.se2019.model.Player;

public class testServer {
    public static void main(String[] args){

        Controller user1 = new Controller();

        user1.startGameWithSocketAsServer();

    }
}
