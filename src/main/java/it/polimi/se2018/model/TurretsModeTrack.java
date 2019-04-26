package it.polimi.se2018.model;

/***/
public class TurretsModeTrack extends KillShotTrack{
    /*-****************************************************************************************************CONSTRUCTOR*/

    /***/
    public TurretsModeTrack(int numberOfStartingSkulls) throws Exception {
        super(numberOfStartingSkulls);
        ammoCards=null;
    }
    /*-****************************************************************************************************ATTRIBUTES*/

    /***/
    private OrderedCardList ammoCards;
    /*-****************************************************************************************************METHODS*/

    /***/
    public OrderedCardList getAmmoCards() {
        return ammoCards;
    }

    /***/
    public void setAmmoCards(OrderedCardList ammoCards){
        this.ammoCards = ammoCards;
    }

}
