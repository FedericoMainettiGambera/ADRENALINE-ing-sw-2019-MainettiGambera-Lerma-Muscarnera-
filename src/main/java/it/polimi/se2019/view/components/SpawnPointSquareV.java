package it.polimi.se2019.view.components;

import it.polimi.se2019.model.OrderedCardList;
/**equivalent view class of SpawnPoint class in the model
 *  @author FedericoMainettiGambera
 *  @author LudoLerma*/
public class SpawnPointSquareV extends SquareV{
    /***/
    private char color;

    /***/
    private OrderedCardListV<WeaponCardV> weaponCards;

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public OrderedCardListV<WeaponCardV> getWeaponCards() {
        return weaponCards;
    }

    public void setWeaponCards(OrderedCardListV<WeaponCardV> weaponCards) {
        this.weaponCards = weaponCards;
    }
}
