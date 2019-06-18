package it.polimi.se2019.view.components;

import it.polimi.se2019.model.enumerations.PlayersColors;

import java.io.Serializable;

public class DamageSlotV implements Serializable {

    private PlayersColors shootingPlayerColor;

    public PlayersColors getShootingPlayerColor() {
        return shootingPlayerColor;
    }

    private String shootingPlayerNickname;

    public void setShootingPlayerNickname(String shootingPlayerNickname) {
        this.shootingPlayerNickname = shootingPlayerNickname;
    }

    public String getShootingPlayerNickname() {
        return shootingPlayerNickname;
    }

    public void setShootingPlayerColor(PlayersColors shootingPlayerColor) {
        this.shootingPlayerColor = shootingPlayerColor;
    }
}
