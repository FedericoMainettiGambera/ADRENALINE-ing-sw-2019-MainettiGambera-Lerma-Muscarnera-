package it.polimi.se2019.view.components;

import java.io.Serializable;

public class AmmoCardV extends CardV{

    private boolean isPowerUp;

    private AmmoListV ammoList;

    public boolean isPowerUp() {
        return isPowerUp;
    }

    public AmmoListV getAmmoList() {
        return ammoList;
    }

    public void setPowerUp(boolean powerUp) {
        isPowerUp = powerUp;
    }

    public void setAmmoList(AmmoListV ammoList) {
        this.ammoList = ammoList;
    }
}
