package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;

/***/
public class NormalSquare extends Square {

    /***/
    public NormalSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType) {
        super(X,Y,north,east,south,west,squareType);
        this.ammoCards = null;
    }

    /***/
    private OrderedCardList ammoCards;

    /***/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }
}