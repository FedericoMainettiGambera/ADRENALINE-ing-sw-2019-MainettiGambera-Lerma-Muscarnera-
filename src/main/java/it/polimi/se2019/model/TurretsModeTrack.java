package it.polimi.se2019.model;


/**Turrets mode killshot track*/
public class TurretsModeTrack extends KillShotTrack{
    /*-****************************************************************************************************CONSTRUCTOR*/

    /**Constructor:
     * sets an orderedCardList for the ammos
     */
    public TurretsModeTrack() {
        super(GameConstant.numberOfStartingSkullsTurretsMode);

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
