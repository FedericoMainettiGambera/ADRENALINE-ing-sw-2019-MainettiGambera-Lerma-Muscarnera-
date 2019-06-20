package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        this.damagesTracker=new DamagesTracker();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**is bot active*/
    protected boolean isBotActive;

    private DamagesTracker damagesTracker;


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