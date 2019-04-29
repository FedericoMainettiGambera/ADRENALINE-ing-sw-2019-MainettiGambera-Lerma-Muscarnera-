package it.polimi.se2018.model;
import java.util.*;

/***/
public class TurretsModeTrack{
    /*-****************************************************************************************************CONSTRUCTOR*/

    public TurretsModeTrack() {

           for(int index=0; index< 8; index++){

               skulls.add(new Kill());

               }


           ammoCards=new OrderedCardList();
           for(int index=0; index<5; index++) {

               ammoCards.addCard(ammoCard);
           }
           }






    /*-****************************************************************************************************ATTRIBUTES*/

    /***/
    private OrderedCardList ammoCards;
    private List<Kill> skulls;
    private AmmoCard ammoCard;
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
