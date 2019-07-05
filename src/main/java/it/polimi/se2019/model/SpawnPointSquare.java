package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.view.components.SpawnPointSquareV;

import java.io.Serializable;

import static it.polimi.se2019.model.enumerations.CardinalPoint.east;
import static it.polimi.se2019.model.enumerations.CardinalPoint.south;


/**this class implements a spawnPointSquare
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class SpawnPointSquare extends Square implements Serializable {

    /**constructor
     * @param x to set x value on board
     * @param color to set the color of the square
     * @param y  to set y value on board
     * @param east to set what there is of the east
     * @param north to set what there is on the north
     * @param south to set what there is on the south
     * @param squareType to set it is a spawn point square
     * @param west to set what there is on the west*/
    public SpawnPointSquare(int x, int y, SquareSide north, SquareSide east, SquareSide south, SquareSide west, SquareTypes squareType, char color) {
        super(x,y,north,east,south,west,squareType, color);
        this.color = color;
        weaponCards = new OrderedCardList<>("spawnPoint-"+x+"-"+y);
    }

    /**the color of the spawn point*/
    private char color;

    /**the list of weapon cards on the spawn point*/
    private transient OrderedCardList<WeaponCard> weaponCards;

    /**@return  color*/
    @Override
    public char getColor() {
        return color;
    }

    /**@return weaponcards*/
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
/**build equivalent structure for view purposes
     * @param spawnPointSquare, the type of square to be build
     * @return a reference to the structure created*/
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