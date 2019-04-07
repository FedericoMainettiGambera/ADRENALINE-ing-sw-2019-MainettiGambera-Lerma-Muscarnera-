
import java.util.*;


public abstract class Square {

    
    public Square() {
    }

    
    private SquareSide north;

    
    private SquareSide east;

    
    private SquareSide south;

    
    private SquareSide west;

    
    private Position coordinates;

    
    private SquareTypes squareType;

    
    private Square northSquare;

    
    private Square southSquare;

    
    private Square westSquare;

    
    private Square eastSquare;



    
    public SquareSide getSide(CardinalPoint sideOrientation) {
        // TODO implement here
        return null;
    }

    
    public Position getCoordinates() {
        // TODO implement here
        return null;
    }

    
    public SquareType getSquareType() {
        // TODO implement here
        return null;
    }

    
    public boolean existsSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
        // TODO implement here
        return false;
    }

    
    public Square getSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
        // TODO implement here
        return null;
    }

}ÿ