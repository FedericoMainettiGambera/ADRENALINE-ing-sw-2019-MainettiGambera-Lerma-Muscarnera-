package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.AmmoCubesColor;


/***/
public class PowerUpCard extends Card {

    /***/
    public PowerUpCard(String ID, AmmoCubesColor color, Effect specialEffect) {
        super(ID);
        this.color = color;
        this.specialEffect = specialEffect;
    }

    /***/
    private AmmoCubesColor color;

    /***/
    private Effect specialEffect;

    /***/
    public AmmoCubesColor getColor() {
        return color;
    }

    /***/
    public Effect getSpecialEffect() {
        return specialEffect;
    }

}
