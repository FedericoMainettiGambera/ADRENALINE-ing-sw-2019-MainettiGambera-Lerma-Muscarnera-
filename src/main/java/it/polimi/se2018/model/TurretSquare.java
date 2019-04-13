package it.polimi.se2018.model;


/***/
public class TurretSquare extends Square {

    /***/
    public TurretSquare(){
        owner=null;
    }

    /***/
    private Player owner;

    /***/
    public Player getOwner() {
        return owner;
    }

    /***/
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}