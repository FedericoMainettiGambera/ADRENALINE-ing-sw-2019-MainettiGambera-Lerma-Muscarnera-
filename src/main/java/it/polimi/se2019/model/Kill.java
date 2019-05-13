package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.Observable;

/** each instance of this class represents a single box of the killshot track */
public class Kill extends Observable implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/

    /**COnstructor:
     * only isSkull is true
     * */
    public Kill(){
        isSkull=true;
        killingPlayer=null;
        isOverKill=false;
        overKillingPlayer=null;
    }

    /*-****************************************************************************************************ATTRIBUTES*/
    /** keeps track whether there is a skull or not*/
    private boolean isSkull;

    /**killing player*/
    private Player killingPlayer;

    /**if the kill was an over kill*/
    private boolean isOverKill;

    /**over killing player*/
    private Player overKillingPlayer;

    /*-****************************************************************************************************METHODS*/

    /**@return if it is overkill*/
    public boolean isOverKill() {
        return isOverKill;
    }

    /**@return is it is still a Skull*/
    public boolean isSkull() {
        return isSkull;
    }

    /**@return the killing player if ther is, or an exception*/
    public Player getKillingPlayer() throws Exception{
        if(!isSkull && this.killingPlayer!=null){
            return killingPlayer;
        }
        else {
            throw new Exception();
        }
    }

    /**
     * @param player
     */
    public void setKillingPlayer(Player player) throws Exception{
        if(isSkull && player!=null) {
            killingPlayer = player;
            isSkull = false;
            setChanged();
            notifyObservers();
        }
        else {
            throw new Exception();
        }
    }


    /**@return
     * */
    public Player getOverKillingPlayer() throws Exception {
        if(!isSkull && this.overKillingPlayer!=null) {
            return overKillingPlayer;
        }
        else {
            throw new Exception();
        }
    }


    /**Keeps track of the OverkillingPlayer for scores purposes
     * @param player
     */
    public void setOverkillingPlayer(Player player) throws Exception{
        if(!isSkull && player!=null) {
            isOverKill = true;
            overKillingPlayer = player;
        }
        else {
            throw new Exception();
        }
    }



}
