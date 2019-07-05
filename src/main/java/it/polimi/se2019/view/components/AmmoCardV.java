package it.polimi.se2019.view.components;
/**equivalent view class of the AmmoCard class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class AmmoCardV extends CardV{

    private boolean isPowerUp;

    private AmmoListV ammoList;
 /** @return  isPowerUp*/
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
