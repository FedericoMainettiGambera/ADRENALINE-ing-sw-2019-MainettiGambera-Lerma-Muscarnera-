package it.polimi.se2019.model;


import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;

/**Turrets mode killshot track*/
public class TurretsModeTrack extends KillShotTrack implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/

    /**Constructor:
     * sets an orderedCardList for the ammos
     */
    public TurretsModeTrack(VirtualView VV) {
        super(GameConstant.numberOfStartingSkullsTurretsMode, VV);

        ammos=new OrderedCardList<>();
    }

    /*-****************************************************************************************************ATTRIBUTES*/
    /**Ammos*/
    private OrderedCardList<AmmoCard> ammos;

    /*-****************************************************************************************************METHODS*/

    /**@return
     * */
    public OrderedCardList getAmmos() {
        return ammos;
    }
}
