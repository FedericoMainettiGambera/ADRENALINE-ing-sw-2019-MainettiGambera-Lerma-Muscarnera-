package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;


/***/
public class SpawnPointSquare extends Square {

    /***/
    public SpawnPointSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(X,Y,north,east,south,west,squareType, color);
        this.color = color;
        weaponCards = new OrderedCardList<WeaponCard>();
    }

    /***/
    private char color;

    /***/
    private OrderedCardList<WeaponCard> weaponCards;

    /***/
    public char getColor() {
        return color;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
}