package it.polimi.se2019.view.components;

import it.polimi.se2019.model.OrderedCardList;

public class NormalSquareV extends SquareV {

    private OrderedCardList<AmmoCardV> ammoCards;

    public OrderedCardList<AmmoCardV> getAmmoCards() {
        return ammoCards;
    }

    public void setAmmoCards(OrderedCardList<AmmoCardV> ammoCards) {
        this.ammoCards = ammoCards;
    }
}
