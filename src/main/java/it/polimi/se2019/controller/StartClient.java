package it.polimi.se2019.controller;

import java.io.IOException;

public class StartClient {
    public static Controller user;
    public static void main(String[] args) throws IOException {

        //TO FORCE START WITH CLI USE: Controller.startClientSocketOrRMIWithCLI();
        Controller.startClientSocketOrRMIWithGUI();
    }
}
