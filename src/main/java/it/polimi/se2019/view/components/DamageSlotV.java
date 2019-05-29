package it.polimi.se2019.view.components;

import java.io.Serializable;

public class DamageSlotV implements Serializable {

    private PlayerV shootingPlayer;

    public PlayerV getShootingPlayer() {
        return shootingPlayer;
    }

    public void setShootingPlayer(PlayerV shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }
}
