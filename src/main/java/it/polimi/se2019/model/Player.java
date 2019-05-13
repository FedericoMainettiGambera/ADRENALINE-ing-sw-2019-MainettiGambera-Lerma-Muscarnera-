package it.polimi.se2019.model;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/***/
public class Player extends Person implements Serializable {

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