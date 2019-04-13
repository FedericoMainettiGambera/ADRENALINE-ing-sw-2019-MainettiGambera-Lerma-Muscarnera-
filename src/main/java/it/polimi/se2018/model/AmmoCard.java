package it.polimi.se2018.model;


/***/
public class AmmoCard extends Card {

    /***/
    public AmmoCard(String ID, AmmoList ammunitions, boolean isPowerUp){
        super(ID);
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