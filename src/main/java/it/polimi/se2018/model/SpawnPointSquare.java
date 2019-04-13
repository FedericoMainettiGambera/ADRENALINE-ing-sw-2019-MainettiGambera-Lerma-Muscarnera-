package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.SpawnPointColors;


/***/
public class SpawnPointSquare extends Square {

    /***/
    public SpawnPointSquare() {
    }

    /***/
    private SpawnPointColors color;

    /***/
    private OrderedCardList<WeaponCard> weaponCards;

}