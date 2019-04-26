package it.polimi.se2018.model;


/** each instance of this class represents a single box of the killshot track */
public class Kill {
    /*-****************************************************************************************************CONSTRUCTOR*/

    /** any boxes is constructed with a skull in it, any other parameter is set false*/
    public Kill(){
        isSkull=true;
        killingPlayer=null;
        isOverKill=false;
        overKillingPlayer=null;
    }

    /*-****************************************************************************************************ATTRIBUTES*/
    /** keeps track whether there is a skull or not (for graphical matters) */
    private boolean isSkull;

    /***/
    private Player killingPlayer;

    /***/
    private boolean isOverKill;

    /***/
    private Player overKillingPlayer;

    /*-****************************************************************************************************METHODS*/

    /***/
    public boolean isOverKill() {
        return isOverKill;
    }

    /***/
    public Player getKillingPlayer() {
        return killingPlayer;
    }

    /**
     * @author Ludovica Lerma
     */
    public void setKillingPlayer(Player player){
        killingPlayer=player;
        isSkull=false;
    }


    /***/
    public Player getOverKillingPlayer() {
        return overKillingPlayer;
    }


    /**
     * Keeps track of the OverkillingPlayer for scores purposes
     */
    public void setOverkillingPlayer(Player player){
        isOverKill= false;
        overKillingPlayer=player;
    }



}
