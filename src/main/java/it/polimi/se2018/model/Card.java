package it.polimi.se2018.model;


/**This is an abstract class that represent a card. it is extended in PowerUpCard, WeaponCard and AmmoCard*/
public abstract class Card{

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets the ID.
     * */
    public Card(String ID) {
        this.ID = ID;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**ID*/
    private String ID;

    /*-********************************************************************************************************METHODS*/
    /**@return
     * */
    public String getID() {
        return this.ID;
    }
}