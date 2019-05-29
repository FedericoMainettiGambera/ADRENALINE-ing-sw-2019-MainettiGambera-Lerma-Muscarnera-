package it.polimi.se2019.view.components;

import it.polimi.se2019.model.OrderedCardList;

public class SpawnPointSquareV extends SquareV{
    /***/
    private char color;

    /***/
    private OrderedCardList<WeaponCardV> weaponCards;

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public OrderedCardList<WeaponCardV> getWeaponCards() {
        return weaponCards;
    }

    public void setWeaponCards(OrderedCardList<WeaponCardV> weaponCards) {
        this.weaponCards = weaponCards;
    }
}
