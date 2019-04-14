package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SpawnPointColors;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;


/***/
public class DominationSpawnPointSquare extends SpawnPointSquare {

    /***/
    public DominationSpawnPointSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, SpawnPointColors color) {
        super(X, Y, north, east, south, west, squareType, color);
    }

    /***/
    private DamagesTracker damagesTracker;

}