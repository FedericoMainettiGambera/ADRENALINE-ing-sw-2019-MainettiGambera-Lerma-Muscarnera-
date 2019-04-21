package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.PlayersColors;

/***/
public class Player extends Person {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
    public Player(String nickname, PlayersColors color) {
        super(nickname, color);

        /*sets GameConstant.numberOfStartingAmmoCubesForEachColor AmmoCubes for each color*/
        AmmoList list = new AmmoList();
        for(AmmoCubesColor i: AmmoCubesColor.values()){
            list.addAmmoCubesOfColor(i, GameConstant.numberOfStartingAmmoCubesForEachColor);
        }
        this.addAmmoCubes(list);

        this.hand = new PlayerHand();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private PlayerHand hand;

    /*-********************************************************************************************************METHODS*/
    /***/
    public PlayerHand getHand() {
        return this.hand;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponCardsInHand() {
        return this.hand.getWeaponCards();
    }

    /***/
    public OrderedCardList<PowerUpCard> getPowerUpCardsInHand() {
        return this.hand.getPowerUpCards();
    }

}