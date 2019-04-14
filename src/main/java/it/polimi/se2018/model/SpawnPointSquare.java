package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SpawnPointColors;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;


/***/
public class SpawnPointSquare extends Square {

    /***/
    public SpawnPointSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, SpawnPointColors color) {
        super(X,Y,north,east,south,west,squareType);
        this.color = color;
        weaponCards = new OrderedCardList<WeaponCard>();
    }

    /***/
    private SpawnPointColors color;

    /***/
    private OrderedCardList<WeaponCard> weaponCards;

    /***/
    public SpawnPointColors getColor() {
        return color;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
}