package it.polimi.se2019.model;


import java.io.Serializable;

/***/
public class AmmoCard extends Card implements Serializable {

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