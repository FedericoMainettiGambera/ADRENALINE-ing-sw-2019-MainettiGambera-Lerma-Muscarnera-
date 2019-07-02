package it.polimi.se2019.controller;

import it.polimi.se2019.model.Game;

public class ModelGate {

    private ModelGate(){

        //
    }


    private static Game model = new Game();

    public static Game getModel() {
        return model;
    }

    public static void setModel(Game model) {
        ModelGate.model = model;
    }
}