package Model;

import java.util.*;

/**
 * 
 */
public abstract class Square {

    /**
     * Default constructor
     */
    public Square() {
    }

    /**
     * 
     */
    private SquareSide north;

    /**
     * 
     */
    private SquareSide east;

    /**
     * 
     */
    private SquareSide south;

    /**
     * 
     */
    private SquareSide west;

    /**
     * 
     */
    private Position coordinates;

    /**
     * 
     */
    private SquareTypes squareType;

    /**
     * 
     */
    private Square northSquare;

    /**
     * 
     */
    private Square southSquare;

    /**
     * 
     */
    private Square westSquare;

    /**
     * 
     */
    private Square eastSquare;



    /**
     * @param sideOrientation 
     * @return
     */
    public SquareSide getSide(SquareSide sideOrientation) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Position getCoordinates() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public SquareType getSquareType() {
        // TODO implement here
        return null;
    }

    /**
     * @param cardinalPoint 
     * @return
     */
    public boolean existsSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
        // TODO implement here
        return false;
    }

    /**
     * @param cardinalPoint 
     * @return
     */
    public Square getSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
        // TODO implement here
        return null;
    }

}