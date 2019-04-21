package it.polimi.se2018.model;


import java.util.List;


/***/
public class OrderedCardList<Card> {

    /***/
    public OrderedCardList() {
    }

    /***/
    private List<Card> cards;

    /***/
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