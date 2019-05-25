package it.polimi.se2019.controller;

public class testRMIClient {
    public static void main(String[] args){

        Controller user1 = new Controller();

        user1.startGmeWithRMIAsClient();

    }
}
