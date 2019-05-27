package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;


/***/
public class DominationSpawnPointSquare extends SpawnPointSquare implements Serializable {

    /***/
    public DominationSpawnPointSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(X, Y, north, east, south, west, squareType, color);
        this.damagesTracker = new DamagesTracker();
    }

    /***/
    private DamagesTracker damagesTracker;

    /***/
    public DamagesTracker getDamagesTracker() {
        return damagesTracker;
    }
}