package it.polimi.se2018.model;

/***/
public class PlayerHand {

    /***/
    public PlayerHand() {
        powerUpCards = new OrderedCardList<>();
        weaponCards = new OrderedCardList<>();
    }

    /***/
    private OrderedCardList<PowerUpCard> powerUpCards;

    /***/
    private OrderedCardList<WeaponCard> weaponCards;

    /***/
    public OrderedCardList<WeaponCard> getWeaponCards() {
        return weaponCards;
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpCards() {
        return powerUpCards;
    }


}