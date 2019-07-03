package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.view.components.NormalSquareV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;

/**implements a normal square*/
public class NormalSquare extends Square implements Serializable {

    /**constructor
     * @param X to set x value on board
     * @param color to set the color of the square
     * @param Y  to set y value on board
     * @param east to set what there is of the east
     * @param north to set what there is on the north
     * @param south to set what there is on the south
     * @param squareType to set it is a normal square
     * @param west to set what there is on the west*/
    public NormalSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(X,Y,north,east,south,west,squareType, color);
        this.ammoCards = new OrderedCardList<>("normalSquare-"+X+"-"+Y);
    }

    /**the card on the square*/
    private transient OrderedCardList<AmmoCard> ammoCards;

    /**@return ammoCards*/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }

    /**build equivalent structure for view purposes
     * @param normalSquare, the type of square to be build
     * @return a reference to the structure created*/
    public NormalSquareV buildNormalSquareV(NormalSquare normalSquare){
        NormalSquareV normalSquareV  = new NormalSquareV();

        normalSquareV.setAmmoCards(normalSquare.getAmmoCards().buildDeckV());
        normalSquareV.setColor(normalSquare.getColor());
        normalSquareV.setSquareType(normalSquare.getSquareType());
        normalSquareV.setX(normalSquare.getCoordinates().getX());
        normalSquareV.setY(normalSquare.getCoordinates().getY());
        normalSquareV.setNorth(normalSquare.getSide(CardinalPoint.north));
        normalSquareV.setEast(normalSquare.getSide(east));
        normalSquareV.setSouth(normalSquare.getSide(south));
        normalSquareV.setWest(normalSquare.getSide(CardinalPoint.west));

        return normalSquareV;
    }
}