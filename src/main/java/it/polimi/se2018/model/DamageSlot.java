package it.polimi.se2018.model;


/**The DamageSlot class keeps track of a single damage taken from a player.
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
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