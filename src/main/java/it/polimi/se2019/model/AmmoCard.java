package it.polimi.se2019.model;


import it.polimi.se2019.view.components.AmmoCardV;

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

    public AmmoCardV buildAmmoCardV(){
        AmmoCardV ammoCardV=new AmmoCardV();
        ammoCardV.setAmmoList(this.ammunitions.buildAmmoListV());
        ammoCardV.setPowerUp(this.isPowerUp());
        ammoCardV.setID(this.getID());
        return ammoCardV;
    }
}