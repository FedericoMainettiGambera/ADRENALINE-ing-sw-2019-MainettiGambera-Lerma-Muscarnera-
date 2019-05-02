package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.CardinalPoint;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;


/***/
public abstract class Square {

    /***/
    public Square(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color){
        this.coordinates = new Position(X,Y);
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.squareType = squareType;
        this.color=color;
    }

    /***/
    private SquareSide north;

    /***/
    private SquareSide east;

    /***/
    private SquareSide south;

    /***/
    private SquareSide west;

    /***/
    private Position coordinates;

    /***/
    private SquareTypes squareType;

    private char color;

    /***/
    public SquareSide getSide(CardinalPoint cardinalPoint){
        if(cardinalPoint == CardinalPoint.north){
            return this.north;
        }
        else if(cardinalPoint == CardinalPoint.east){
            return this.east;
        }
        else if(cardinalPoint == CardinalPoint.south){
            return this.south;
        }
        else if(cardinalPoint == CardinalPoint.west){
            return this.west;
        }

        return null;
    }

    /***/
    public Position getCoordinates() {
        return coordinates;
    }

    /***/
    public SquareTypes getSquareType() {
        return squareType;
    }
    public void        setSquareType(SquareTypes S) {
       squareType = S;
    }
    public char getColor(){return this.color;}

}