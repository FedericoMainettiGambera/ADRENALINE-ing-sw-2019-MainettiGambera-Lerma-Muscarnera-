package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.view.components.NormalSquareV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;

/***/
public class NormalSquare extends Square implements Serializable {

    /***/
    public NormalSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(X,Y,north,east,south,west,squareType, color);
        this.ammoCards = new OrderedCardList<>("normalSquare-"+X+"-"+Y);
    }

    /***/
    private transient OrderedCardList<AmmoCard> ammoCards;

    /***/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }

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