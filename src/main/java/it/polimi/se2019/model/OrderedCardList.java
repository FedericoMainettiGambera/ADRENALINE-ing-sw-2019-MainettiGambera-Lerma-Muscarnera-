package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.AmmoCardV;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**this class is an ordered card list of a specified type of cards, it is used to represents WeaponCards and
 * PowerUpCards in game decks or players hands.
 * @author FedericoMainettiGambera
 * */
public class OrderedCardList<T> extends Observable implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * */
    public OrderedCardList(String context) {
        this.context = context;
        cards = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**cards*/
    private List<T> cards;

    private String context;

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
            if( ((Card)this.cards.get(i)).getID().equals(ID) ){
                return this.cards.get(i);
            }
        }
        return null;
    }

    public T getFirstCard(){
        return this.cards.get(0);
    }

    /**Add a card to the list, TO BE USED ONLY IN GAME CLASS DURING THE BUIDING OF THE DECKS
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
            to.getCards().add(this.getCard(cardID));
            this.removeCard(cardID);

            //TODO
            setChanged();
            notifyObservers(new ModelViewEvent(null, ModelViewEventTypes.movingFknCardsAround));

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
            to.getCards().add(this.cards.get(i));
            this.cards.remove(i);

            //TODO
            setChanged();
            notifyObservers(new ModelViewEvent(null, ModelViewEventTypes.movingFknCardsAround));
        }
    }

    /**shuffles all cards in this ordered card list*/
    public void shuffle() {
        Collections.shuffle(this.cards);

        //TODO
        setChanged();
        notifyObservers(new ModelViewEvent(null, ModelViewEventTypes.shufflingFknCardsAround));
    }

    public OrderedCardListV buildDeckV(){
        if(this.context.equals("ammoDeck")||this.context.equals("ammoDiscardPile")||this.context.contains("normalSquare")){

            OrderedCardListV<AmmoCardV> orderedCardListV= new OrderedCardListV<>();
            orderedCardListV.setContext(this.context);
            List<AmmoCardV> ammoCardsListV=new ArrayList<>();
            for ( Object c :this.getCards()){
                AmmoCard card= (AmmoCard)c;
                ammoCardsListV.add(card.buildAmmoCardV());
            }
            orderedCardListV.setCards(ammoCardsListV);
            return orderedCardListV;
        }
        else if(this.context.equals("powerUpDeck")||this.context.equals("powerUpDiscardPile")||this.context.contains(":powerUpInHand")){
            OrderedCardListV<PowerUpCardV> orderedCardListV= new OrderedCardListV<>();
            orderedCardListV.setContext(this.context);
            List<PowerUpCardV> powerUpCardsV=new ArrayList<>();
            for ( Object c :this.getCards()){
                PowerUpCard card= (PowerUpCard) c;
                powerUpCardsV.add(card.buildPowerUpCardV());
            }
            orderedCardListV.setCards(powerUpCardsV);
            return orderedCardListV;



        }
        else{
            OrderedCardListV<WeaponCardV> orderedCardListV= new OrderedCardListV<>();
            orderedCardListV.setContext(this.context);
            List<WeaponCardV> weaponCardsListV=new ArrayList<>();
            for ( Object c :this.getCards()){
                WeaponCard card= (WeaponCard) c;
                weaponCardsListV.add(card.buildWeapondCardV());
            }
            orderedCardListV.setCards(weaponCardsListV);
            return orderedCardListV;

        }

    }
}