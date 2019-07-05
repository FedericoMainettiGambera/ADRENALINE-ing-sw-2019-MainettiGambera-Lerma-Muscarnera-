package it.polimi.se2019.view.components;

/**equivalent view class of NormalSquare class in the model
 * @author FedericoMainettiGambera
 * @author LudoLerma*/
public class NormalSquareV extends SquareV {

    private OrderedCardListV<AmmoCardV> ammoCards;

    public OrderedCardListV<AmmoCardV> getAmmoCards() {
        return ammoCards;
    }

    public void setAmmoCards(OrderedCardListV<AmmoCardV> ammoCards) {
        this.ammoCards = ammoCards;
    }
}
