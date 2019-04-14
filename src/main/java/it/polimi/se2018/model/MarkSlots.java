package it.polimi.se2018.model;


/***/
public class MarkSlots {

    /***/
    public MarkSlots(Player player){
        this.quantity=0;
        this.markingPlayer = player;
    }

    /***/
    private int quantity;

    /***/
    private Player markingPlayer;

    /***/
    public int getQuantity() {
        return quantity;
    }

    /***/
    public Player getMarkingPlayer() {
        return markingPlayer;
    }

    /***/
    public void addQuantity(int quantity){
        /*TODO*/
    }
}