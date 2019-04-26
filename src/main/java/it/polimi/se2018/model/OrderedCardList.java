package it.polimi.se2018.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**this class is an ordered card list of a specified type of cards, it is used to represents WeaponCards and
 * PowerUpCards in game decks or players hands.
 * */
public class OrderedCardList<T>{

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * */
    public OrderedCardList() {
        cards = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**cards*/
    private List<T> cards;

    /*-********************************************************************************************************METHODS*/
    /**@return
     * */
    public List<T> getCards() {
        return this.cards;
    }

    /**Problem with the Card.getID() method, so i had to cast the cards and do some weird hack to get around the problem.
     * @param ID
     * @return the card or null if the card doesn't exist.
     * */
    public T getCard(String ID){
        for (int i = 0; i < this.cards.size(); i++) {
            if( ((Card)this.cards.get(i)).getID() == ID ){
                return this.cards.get(i);
            }
        }
        return null;
    }

    /**Add a card to the list
     * @param card
     */
    public void addCard(T card){
        this.cards.add(card);
    }

    /**Remove card from the list
     * @param ID
     * @return true if the card has been removed; false if it isn't;
     */
    public boolean removeCard(String ID){
        for (int i = 0; i < this.cards.size(); i++) {
            if( ((Card)this.cards.get(i)).getID() == ID ){
                this.cards.remove(i);
                return true;
            }
        }
        return false;
    }

    /**Moves a specific card from this ordered card list to another one
     * @param cardID
     * @param to
     * @return true if the card exist, false if it doesn't.
     * */
    public boolean moveCardTo(OrderedCardList to, String cardID) {
        if(this.getCard(cardID) != null) {
            to.addCard(this.getCard(cardID));
            this.removeCard(cardID);
            return true;
        }
        else{
            return false;
        }
    }

    /**Moves all cards from this ordered card list to another one
     * @param to
     * */
    public void moveAllCardsTo(OrderedCardList to) {
        for (int i = 0; i < this.cards.size(); i++) {
            to.addCard(this.cards.get(i));
            this.cards.remove(i);
        }
    }

    /**shuffles all cards in this ordered card list*/
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

}