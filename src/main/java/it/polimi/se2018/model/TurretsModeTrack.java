package it.polimi.se2018.model;
import java.util.*;

/***/
public class TurretsModeTrack{
    /*-****************************************************************************************************CONSTRUCTOR*/

    public TurretsModeTrack() {
           for(int index=0; index< 8; index++){
               skulls.add(new Kill());
           }
           ammos=new OrderedCardList();
    }

    /*-****************************************************************************************************ATTRIBUTES*/
    /***/
    private OrderedCardList<AmmoCard> ammos;

    private List<Kill> skulls;

    /*-****************************************************************************************************METHODS*/

    /***/
    public OrderedCardList getAmmos() {
        return ammos;
    }

    /***/
    public void setAmmoCards(OrderedCardList ammoCards){
        this.ammos = ammoCards;
    }

}
