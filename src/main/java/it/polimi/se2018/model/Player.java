package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.PlayersColors;


/***/
public class Player extends Person {

    /***/
    public Player(String nickname, PlayersColors color) {
        super(nickname, color);
    }

    /***/
    private PlayerHand hand;

    /***/
    public PlayerHand getHand() {
        return this.hand;
    }

}