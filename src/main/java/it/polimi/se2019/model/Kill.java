package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.Observable;

/** each instance of this class represents a single box of the killshot track */
public class Kill implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/

    /**COnstructor:
     * only isSkull is true
     * */
    public Kill(){
        isSkull=true;
        killingPlayer=null;
        isOverKill=false;
        overKillingPlayer=null;
        occurance=0;
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

    /**needs for scoring, index aka occurance**/
    private int occurance;

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
    public Player getKillingPlayer() {
        if(!isSkull && this.killingPlayer!=null){
            return killingPlayer;
        }
        return null;
    }

    /**
     * @param player
     */
    public void setKillingPlayer(Player player){
        if(isSkull && player!=null) {
            killingPlayer = player;
            isSkull = false;
        }
    }


    /**@return
     * */
    public Player getOverKillingPlayer() {
        if(!isSkull && this.overKillingPlayer!=null) {
            return overKillingPlayer;
        }
        return null;
    }


    /**Keeps track of the OverkillingPlayer for scores purposes
     * @param player
     */
    public void setOverkillingPlayer(Player player) {
        if(!isSkull && player!=null) {
            isOverKill = true;
            overKillingPlayer = player;
        }
    }
    /**@return occurance, which is meant to be useful for tiebreaking
     * */
    public int getOccurance() {
        return occurance;
    }

    public void setOccurance(int occurance) {
        this.occurance = occurance;
    }
}
