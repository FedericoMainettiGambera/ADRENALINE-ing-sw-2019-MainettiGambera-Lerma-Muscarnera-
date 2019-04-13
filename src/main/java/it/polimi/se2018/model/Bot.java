package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.PlayersColors;

/***/
public class Bot extends Person {

    /***/
    public Bot(boolean isBotActive, String nickname, PlayersColors color, PlayerBoard board) {
        super(nickname, color, board);
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