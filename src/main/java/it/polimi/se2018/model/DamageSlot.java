package it.polimi.se2018.model;


/***/
public class DamageSlot {

    /***/
    public DamageSlot(Player shootingPlayer) {
        this.shootingPlayer = shootingPlayer;
    }

    /***/
    private Player shootingPlayer;

    /***/
    public Player getShootingPlayer() {
        return shootingPlayer;
    }
}