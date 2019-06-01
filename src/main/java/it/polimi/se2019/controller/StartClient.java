package it.polimi.se2019.controller;

import java.io.IOException;

public class StartClient {
    public static void main(String[] args){
        Controller user = new Controller();

        user.startClientSocketOrRMI();
    }
}
