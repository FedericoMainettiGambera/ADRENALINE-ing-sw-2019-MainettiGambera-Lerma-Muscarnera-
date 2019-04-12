package it.polimi.se2018.model;

/***/
public class Bot extends Person {

    /***/
    public Bot(boolean isBotActive) {
        super();
        this.isBotActive = isBotActive;
    }

    /***/
    private boolean isBotActive;

    /***/
    private Player owner;

    /***/
    public Player getOwner() {
        return owner;
    }


    /***/
    public boolean isBotActive() {
        return isBotActive;
    }

    /***/
    public void setToNextOwner(Turns turns){

    }

}