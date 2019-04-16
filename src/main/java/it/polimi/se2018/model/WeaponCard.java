package it.polimi.se2018.model;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/***/
public class WeaponCard extends Card {

    /***/
   public WeaponCard(String ID, AmmoList pickUpCost, AmmoList reloadCost, List<Effect> effects) {
        super(ID);
        this.isLoaded = true;
        this.effects = effects;
        this.pickUpCost = pickUpCost;
        this.reloadCost = reloadCost;
    }
    /***/
    // WeaponCard from File, polymorphic constructor





    /***/
    private AmmoList pickUpCost;

    /***/
    private AmmoList reloadCost;

    /***/
    private boolean isLoaded;

    /***/
    public List<Effect> effects;

    /***/
    public AmmoList getPickUpCost() {
        return pickUpCost;
    }

    /***/
    public AmmoList getReloadCost() {
        return reloadCost;
    }

    /***/
    public boolean isLoaded() {
        return isLoaded;
    }

    /***/
    public List<Effect> getEffects() {
        return effects;
    }
}