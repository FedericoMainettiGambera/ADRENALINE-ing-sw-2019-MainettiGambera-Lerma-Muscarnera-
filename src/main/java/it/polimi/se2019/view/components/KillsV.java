package it.polimi.se2019.view.components;

import it.polimi.se2019.model.Player;

import java.io.Serializable;

public class KillsV implements Serializable {
    private boolean isSkull;

    private Player killingPlayer;

    private boolean isOverKill;

    private Player overKillingPlayer;

    public boolean isSkull() {
        return isSkull;
    }

    public boolean isOverKill() {
        return isOverKill;
    }

    public Player getKillingPlayer() {
        return killingPlayer;
    }

    public Player getOverKillingPlayer() {
        return overKillingPlayer;
    }

    public void setKillingPlayer(Player killingPlayer) {
        this.killingPlayer = killingPlayer;
    }

    public void setOverKillingPlayer(Player overKillingPlayer) {
        this.overKillingPlayer = overKillingPlayer;
    }

    public void setOverKill(boolean overKill) {
        isOverKill = overKill;
    }

    public void setSkull(boolean skull) {
        isSkull = skull;
    }
}
