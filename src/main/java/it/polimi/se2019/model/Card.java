package it.polimi.se2019.model;


import java.io.Serializable;

/***/
public abstract class Card implements Serializable {

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