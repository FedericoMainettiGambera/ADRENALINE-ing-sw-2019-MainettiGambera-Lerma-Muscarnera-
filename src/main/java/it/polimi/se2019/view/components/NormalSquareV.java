package it.polimi.se2019.view.components;

import it.polimi.se2019.model.OrderedCardList;

public class NormalSquareV extends SquareV {

    private OrderedCardListV<AmmoCardV> ammoCards;

    public OrderedCardListV<AmmoCardV> getAmmoCards() {
        return ammoCards;
    }

    public void setAmmoCards(OrderedCardListV<AmmoCardV> ammoCards) {
        this.ammoCards = ammoCards;
    }
}
