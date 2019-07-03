package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;
import java.util.Observable;


/***/
public abstract class Square implements Serializable {

    /**constructor
     * @param X to set x value on board
     * @param color to set the color of the square
     * @param Y  to set y value on board
     * @param east to set what there is of the east
     * @param north to set what there is on the north
     * @param south to set what there is on the south
     * @param squareType to set the type of square
     * @param west to set what there is on the west*/
    public Square(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color){
        this.coordinates = new Position(X,Y);
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.squareType = squareType;
        this.color=color;
    }

    /**the north side */
    private SquareSide north;

    /**the south side*/
    private SquareSide east;

    /**the south side*/
    private SquareSide south;

    /**the west side*/
    private SquareSide west;

    /**x and y on the board*/
    private Position coordinates;

    /**normal or spawn point*/
    private SquareTypes squareType;
/**color of the square*/
    private char color;

    /**@®return the asked side
     * @param cardinalPoint may be one of the 4 cardinal point*/
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

    /**@return  coordinates*/
    public Position getCoordinates() {
        return coordinates;
    }

    /**@return squaretype*/
    public SquareTypes getSquareType() {
        return squareType;
    }

   /**@return color*/
    public char getColor(){return this.color;}

}