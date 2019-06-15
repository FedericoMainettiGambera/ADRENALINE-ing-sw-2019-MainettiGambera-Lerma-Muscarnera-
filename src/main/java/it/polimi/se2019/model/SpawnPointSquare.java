package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.view.components.SpawnPointSquareV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;


/***/
public class SpawnPointSquare extends Square implements Serializable {

    /***/
    public SpawnPointSquare(int X, int Y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(X,Y,north,east,south,west,squareType, color);
        this.color = color;
        weaponCards = new OrderedCardList<WeaponCard>("spawnPoint-"+X+"-"+Y);
    }

    /***/
    private char color;

    /***/
    private transient OrderedCardList<WeaponCard> weaponCards;

    /***/
    public char getColor() {
        return color;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }

    public SpawnPointSquareV builSpawnPointSquareV(SpawnPointSquare spawnPointSquare){
        SpawnPointSquareV spawnPointSquareV = new SpawnPointSquareV();

        spawnPointSquareV.setWeaponCards(spawnPointSquare.getWeaponCards().buildDeckV());
        spawnPointSquareV.setColor(spawnPointSquare.getColor());
        spawnPointSquareV.setColor(spawnPointSquare.getColor());
        spawnPointSquareV.setSquareType(spawnPointSquare.getSquareType());
        spawnPointSquareV.setX(spawnPointSquare.getCoordinates().getX());
        spawnPointSquareV.setY(spawnPointSquare.getCoordinates().getY());
        spawnPointSquareV.setNorth(spawnPointSquare.getSide(CardinalPoint.north));
        spawnPointSquareV.setEast(spawnPointSquare.getSide(east));
        spawnPointSquareV.setSouth(spawnPointSquare.getSide(south));
        spawnPointSquareV.setWest(spawnPointSquare.getSide(CardinalPoint.west));

        return spawnPointSquareV;
    }
}