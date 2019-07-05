package it.polimi.se2019.model;


import java.io.Serializable;

/**abstact constructor for cards
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public abstract class Card implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     *
     * @param ID  to set the ID attribute
     *
     * */
    public Card(String ID) {
        this.ID = ID;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**ID*/
    private String ID;

    /*-********************************************************************************************************METHODS*/
    /**@return ID
     * */
    public String getID() {
        return this.ID;
    }
}