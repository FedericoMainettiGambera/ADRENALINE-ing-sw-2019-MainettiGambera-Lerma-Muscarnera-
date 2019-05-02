package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;

/***/
public class TurretSquare extends Square {

    /***/
    public TurretSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color){
        super(X,Y,north,east,south,west,squareType,color );
        this.owner=null;
    }

    /***/
    private Player owner;

    /***/
    public Player getOwner() {
        return owner;
    }

    /***/
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}