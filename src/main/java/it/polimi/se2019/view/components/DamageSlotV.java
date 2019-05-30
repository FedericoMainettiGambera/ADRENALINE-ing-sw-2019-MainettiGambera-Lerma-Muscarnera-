package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.PlayersColors;

import java.io.Serializable;

public class DamageSlotV implements Serializable {

    private PlayersColors shootingPlayer;

    public PlayersColors getShootingPlayer() {
        return shootingPlayer;
    }

    public void setShootingPlayer(PlayersColors shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }
}
