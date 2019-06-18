package it.polimi.se2019.controller;

public class StartClient {
    public static Controller user;
    public static void main(String[] args) {

        //TO FORCE START WITH CLI USE: Controller.startClientSocketOrRMIWithCLI();
        Controller.startClientSocketOrRMIWithGUI();
    }
}
