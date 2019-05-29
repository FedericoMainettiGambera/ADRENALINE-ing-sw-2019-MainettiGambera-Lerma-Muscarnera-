package it.polimi.se2019.view.components;

import java.io.Serializable;

public class MarkSlotV implements Serializable {
    private int quantity;

    /**marking player*/
    private PlayerV markingPlayer;

    public int getQuantity() {
        return quantity;
    }

    public PlayerV getMarkingPlayer() {
        return markingPlayer;
    }

    public void setMarkingPlayer(PlayerV markingPlayer) {
        this.markingPlayer = markingPlayer;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
