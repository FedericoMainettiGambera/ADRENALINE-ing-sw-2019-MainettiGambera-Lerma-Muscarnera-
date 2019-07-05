package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;

import java.io.Serializable;
/**equivalent view class of Square class in the model
 *  @author FedericoMainettiGambera
 *  @author LudoLerma*/
public class SquareV implements Serializable {
    private SquareSide north;

    private SquareSide east;

    private SquareSide south;

    private SquareSide west;

    private SquareTypes squareType;

    private char color;

    private int X;

    private int Y;

    public void setY(int y) {
        Y = y;
    }

    public void setX(int x) {
        X = x;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public void setSquareType(SquareTypes squareType) {
        this.squareType = squareType;
    }

    public SquareTypes getSquareType() {
        return squareType;
    }

    public void setEast(SquareSide east) {
        this.east = east;
    }

    public void setNorth(SquareSide north) {
        this.north = north;
    }

    public void setSouth(SquareSide south) {
        this.south = south;
    }

    public void setWest(SquareSide west) {
        this.west = west;
    }

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
}
