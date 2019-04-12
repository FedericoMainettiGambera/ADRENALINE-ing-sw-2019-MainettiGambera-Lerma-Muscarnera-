package it.polimi.se2018.model;


import java.util.List;


/***/
public class WeaponCard extends Card {

    /***/
    public WeaponCard() {
    }

    /***/
    private AmmoList pickUpCost;

    /***/
    private AmmoList reloadCost;

    /***/
    private boolean isLoaded;

    /***/
    public List<Effect> effects;

}