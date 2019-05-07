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
        setChanged();
        notifyObservers();
    }

    public ObjectOutputStream getOos(){
        return this.oos;
    }

    public void setIP(String IP){
        this.IP = IP;
        setChanged();
        notifyObservers();
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