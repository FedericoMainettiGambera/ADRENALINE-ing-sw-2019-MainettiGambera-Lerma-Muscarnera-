package it.polimi.se2018.model;


/***/
public class AmmoCard extends Card {

    /***/
    public AmmoCard(AmmoList ammunitions, boolean isPowerUp){
        this.ammunitions = ammunitions;
        this.isPowerUp = isPowerUp;
    }

    /***/
    private AmmoList ammunitions;

    /***/
    private boolean isPowerUp;

    /***/
    public AmmoList getAmmunitions() {
        return ammunitions;
    }

    /***/
    public boolean isPowerUp() {
        return isPowerUp;
    }
}