package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.PlayersColors;

/***/
public class Bot extends Person {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * uses the Person constructor, also set isBotActive and set owner to null
     * @param color
     * @param nickname
     * @param isBotActive
     * */
    public Bot(boolean isBotActive, String nickname, PlayersColors color) {
        super(nickname, color);
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
     * @param turns
     * */
    public void setToNextOwner(Turns turns){
        /*TODO*/
    }

    /**@return isBotActive
     * */
    public boolean isBotActive() {
        return isBotActive;
    }
}