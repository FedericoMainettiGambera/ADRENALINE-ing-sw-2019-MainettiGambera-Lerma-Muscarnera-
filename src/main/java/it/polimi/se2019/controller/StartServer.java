package it.polimi.se2019.controller;

public class StartServer {
    public static void main(String[] args){

        Controller user = new Controller();

        user.startServerWithRMIAndSocket();

    }
}
