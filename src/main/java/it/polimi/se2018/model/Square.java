package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;


/***/
public abstract class Square {

    /***/
    public Square() {
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

    /***/
    private Square northSquare;

    /***/
    private Square southSquare;

    /***/
    private Square westSquare;

    /***/
    private Square eastSquare;

}