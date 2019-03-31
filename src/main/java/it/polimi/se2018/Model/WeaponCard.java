package Model;

import java.util.*;

/**
 * 
 */
public class WeaponCard extends Card {

    /**
     * Default constructor
     */
    public WeaponCard() {
    }

    /**
     * 
     */
    private void pickUpCost;

    /**
     * 
     */
    private void reloadCost;

    /**
     * 
     */
    private boolean isLoaded;

    /**
     * 
     */
    private Effect basicEffect;

    /**
     * 
     */
    private Effect optionalEffect;

    /**
     * 
     */
    private Effect alternativeFireMode;


    /**
     * @return
     */
    public AmmoList getPickUpCost() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public AmmoList getRealoadCost() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public boolean isLoaded() {
        // TODO implement here
        return false;
    }

    /**
     * @param value
     */
    public void setLoaded(boolean value) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Effect getBasicEffect() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Effect getOptionalEffect() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Effect getAlternativeFireMode() {
        // TODO implement here
        return null;
    }

}