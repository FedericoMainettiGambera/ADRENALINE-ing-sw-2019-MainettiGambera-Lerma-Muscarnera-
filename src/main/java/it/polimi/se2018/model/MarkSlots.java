package it.polimi.se2018.model;


/**The MarksSlots class keeps track of the number of marks a player has given to another player
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
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
        if(this.quantity+quantity <= GameConstant.MaxNumberOfMarkFromPlayer) {
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfMarkFromPlayer;
        }
    }
}