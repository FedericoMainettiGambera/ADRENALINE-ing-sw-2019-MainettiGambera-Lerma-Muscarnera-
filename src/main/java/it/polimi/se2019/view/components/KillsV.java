package it.polimi.se2019.view.components;

import it.polimi.se2019.model.Player;

import java.io.Serializable;
/**equivalent view class of Kills class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class KillsV implements Serializable {
    private boolean isSkull;

    private String killingPlayer;

    private boolean isOverKill;

    private String overKillingPlayer;

    public boolean isSkull() {
        return isSkull;
    }

    public boolean isOverKill() {
        return isOverKill;
    }

    public String getKillingPlayer() {
        return killingPlayer;
    }

    public String getOverKillingPlayer() {
        return overKillingPlayer;
    }

    public void setKillingPlayer(String killingPlayer) {
        this.killingPlayer = killingPlayer;
    }

    public void setOverKillingPlayer(String overKillingPlayer) {
        this.overKillingPlayer = overKillingPlayer;
    }

    public void setOverKill(boolean overKill) {
        isOverKill = overKill;
    }

    public void setSkull(boolean skull) {
        isSkull = skull;
    }
}
