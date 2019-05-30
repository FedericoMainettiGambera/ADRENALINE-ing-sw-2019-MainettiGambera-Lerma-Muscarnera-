package it.polimi.se2019.view.components;

import java.io.Serializable;

public class MarkSlotV implements Serializable {
    private int quantity;

    /**marking player*/
    private String markingPlayer;

    public int getQuantity() {
        return quantity;
    }

    public String getMarkingPlayer() {
        return markingPlayer;
    }

    public void setMarkingPlayer(String markingPlayer) {
        this.markingPlayer = markingPlayer;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
