
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
    }

    
    public Position getCoordinates() {
    }

    
    public SquareType getSquareType() {
    }

    
    public boolean existsSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
    }

    
    public Square getSquareOnCardinalPoint(CardinalPoint cardinalPoint) {
    }

}