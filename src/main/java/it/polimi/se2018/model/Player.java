package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.PlayersColors;

import java.io.ObjectOutputStream;

/***/
public class Player extends Person{

    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
    public Player() {
        super();
        /*sets the starting ammo cubes: GameConstant.numberOfStartingAmmoCubesForEachColor AmmoCubes for each color*/
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

    /***/
    private String IP;

    /***/
    private ObjectOutputStream oos;

    /*-********************************************************************************************************METHODS*/

    public void setOos(ObjectOutputStream oos){
        this.oos = oos;
    }

    public ObjectOutputStream getOos(){
        return this.oos;
    }

    public void setIP(String IP){
        this.IP = IP;
    }

    public String getIp(){
        return this.IP;
    }

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