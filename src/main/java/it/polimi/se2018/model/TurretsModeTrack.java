package it.polimi.se2018.model;

/***/
public class TurretsModeTrack extends KillShotTrack{

    /***/
    public TurretsModeTrack(){
        ammoCards=null;
    }

    /***/
    private OrderedCardList ammoCards;

    /***/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }

    /***/
    public void setAmmoCards(OrderedCardList ammoCards){
        this.ammoCards = ammoCards;
    }

}
