package it.polimi.se2019.controller;

import java.io.IOException;

public class StartClient {
    public static Controller user;
    public static void main(String[] args){
        user = new Controller();

        user.startClientSocketOrRMI();
    }
}
