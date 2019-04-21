package it.polimi.se2018.model;

import java.util.List;

/**this class is an ordered card list of a specified type of cards, it is used to represents WeaponCards and
 * PowerUpCards in game decks or players hands.
 * */
public class OrderedCardList<Card> {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * */
    public OrderedCardList() {
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**cards*/
    private List<Card> cards;

    /*-********************************************************************************************************METHODS*/
    /**@return
     * */
    public List<Card> getCards() {
        return cards;
    }

    /***/
    public void moveTo(OrderedCardList to, Card card) {
    }

    /***/
    public void moveAllTo(OrderedCardList from, OrderedCardList to) {
    }

    /***/
    public void shuffle() {
    }

}