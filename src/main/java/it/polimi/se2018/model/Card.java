package it.polimi.se2018.model;


/***/
public abstract class Card {

    /***/
    public Card(String ID) {
        this.ID = ID;
    }

    /***/
    private String ID;

    /***/
    public String getID() {
        return ID;
    }
}