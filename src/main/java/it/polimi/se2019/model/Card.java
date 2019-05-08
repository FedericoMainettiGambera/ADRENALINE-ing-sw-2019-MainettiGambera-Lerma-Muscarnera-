package it.polimi.se2019.model;


/***/
public abstract class Card {

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