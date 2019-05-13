package it.polimi.se2019.model;

import java.io.Serializable;

/***/
public class Bot extends Person implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * uses the Person constructor, also set isBotActive and set owner to null
     * @param isBotActive
     * */
    public Bot(boolean isBotActive) {
        super();
        this.isBotActive = isBotActive;
        this.owner = null;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**is bot active*/
    private boolean isBotActive;

    /**owner*/
    private Player owner;

    /*-********************************************************************************************************METHODS*/
    /**@return
     * */
    public Player getOwner() {
        return owner;
    }

    /**sets the bot owner to the next playing Player
     * @param nextPlayingPlayer
     * */
    public void setToNextOwner(Player nextPlayingPlayer){
        /*TODO*/
        setChanged();
        notifyObservers();
    }

    /**@return isBotActive
     * */
    public boolean isBotActive() {
        return isBotActive;
    }
}