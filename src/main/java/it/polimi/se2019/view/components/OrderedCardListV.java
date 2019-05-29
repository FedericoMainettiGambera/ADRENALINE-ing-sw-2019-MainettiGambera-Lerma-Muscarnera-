package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class OrderedCardListV<T> implements Serializable {

    private List<T> cards;

    public List<T> getCards() {
        return cards;
    }

    public void setCards(List<T> cards) {
        this.cards = cards;
    }
}
