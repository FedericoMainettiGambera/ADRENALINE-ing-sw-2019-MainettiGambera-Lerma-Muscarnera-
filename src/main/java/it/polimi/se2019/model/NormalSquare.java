package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;

/***/
public class NormalSquare extends Square implements Serializable {

    /***/
    public NormalSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color, VirtualView VV) {
        super(X,Y,north,east,south,west,squareType, color, VV);
        this.ammoCards = new OrderedCardList<>();
        setChanged();
        notifyObservers("");
    }

    /***/
    private OrderedCardList<AmmoCard> ammoCards;

    /***/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }
}