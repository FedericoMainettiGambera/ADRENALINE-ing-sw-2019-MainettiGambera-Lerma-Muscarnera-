package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.AmmoCardV;
import it.polimi.se2019.view.components.OrderedCardListV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;

import java.io.Serializable;
import java.util.*;

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

    public String getContext(){
        return this.context;
    }

    public void setContext(String context){
        this.context = context;
    }
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
        checkDeckEnded();
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
            if( ((Card)this.cards.get(i)).getID().equals(ID) ){
                this.cards.remove(i);
                return true;
            }
        }
        return false;
    }

    public void checkDeckEnded(){
        //reshuffles back powerup and ammo from the respective discards pile
        if(this.cards.size() == 0 && this.context.contains("powerUpDeck")){
            ModelGate.model.getPowerUpDiscardPile().moveAllCardsTo(this);
            this.shuffle();
        }
        else if(this.cards.size() == 0 && this.context.contains("ammoDeck")){
            ModelGate.model.getAmmoDiscardPile().moveAllCardsTo(this);
            this.shuffle();
        }
    }

    /**Moves a specific card from this ordered card list to another one
     * @param cardID
     * @param to
     * @return true if the card exist, false if it doesn't.
     * */
    public boolean moveCardTo(OrderedCardList to, String cardID) {
        checkDeckEnded();

        if(this.getCard(cardID) != null) {
            to.getCards().add(this.getCard(cardID));

            if(!this.removeCard(cardID)){
                System.err.println("<SERVER-OrderedCardList> Couldn't move the card properly, only added the card to the destination, but not removed from the origin");
            }

            setChanged();
            OrderedCardListV cards = this.buildDeckV();
            OrderedCardListV cards2 = to.buildDeckV();
            ModelViewEvent MVE = new ModelViewEvent(cards, ModelViewEventTypes.movingCardsAround, cards2);
            notifyObservers(MVE);

            return true;
        }
        else{
            System.err.println("<SERVER-OrderedCardList> Couldn't move the card properly, the card is null.");
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
        }

        setChanged();
        notifyObservers(new ModelViewEvent(this.buildDeckV(), ModelViewEventTypes.movingCardsAround, to.buildDeckV()));
    }

    /**shuffles all cards in this ordered card list*/
    public void shuffle() {
        Collections.shuffle(this.cards);

        setChanged();
        notifyObservers(new ModelViewEvent(this.buildDeckV(), ModelViewEventTypes.shufflingCards));
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