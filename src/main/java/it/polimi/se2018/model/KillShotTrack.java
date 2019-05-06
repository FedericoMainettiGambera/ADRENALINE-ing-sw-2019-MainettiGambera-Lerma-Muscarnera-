package it.polimi.se2018.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**regular killshot track*/
public class KillShotTrack extends Observable {
    /* **************************************************************************CONSTRUCTOR*/

    /**
     * CONSTRUCTOR
     * @param numberOfStartingSkulls
     * as many skulls as indicated by number
     * */
    public KillShotTrack(int numberOfStartingSkulls){
        kills = new ArrayList<>();

        if((numberOfStartingSkulls >= GameConstant.minStartingSkulls )&&(numberOfStartingSkulls<=GameConstant.maxStartingSkulls)){
            for (int index = 0; index < numberOfStartingSkulls; index++) {
                kills.add(new Kill());
            }
        }
        else{
            for (int index = 0; index < GameConstant.maxStartingSkulls; index++) {
                kills.add(new Kill());
            }
        }
        numberOfRemainingSkulls = numberOfStartingSkulls;
    }


    /*-****************************************************************************************************ATTRIBUTES*/
    /**list of kills and skulls*/
    private List<Kill> kills;

    /**number of remaining skulls*/
    private int numberOfRemainingSkulls;
    /*-*********************************************************************************************************METHODS*/

    /**as the number of skull is set once for all at the beginning of the game, it is only allowed to delete'em
     * @param isOverKill
     * @param killingPlayer
     * */
    public void deathOfPlayer(Player killingPlayer, boolean isOverKill) throws IllegalStateException {
        if (numberOfRemainingSkulls>0) {
            try {
                kills.get(numberOfRemainingSkulls).setKillingPlayer(killingPlayer);
                if (isOverKill) {
                    kills.get(numberOfRemainingSkulls).setOverkillingPlayer(killingPlayer);
                }
                numberOfRemainingSkulls--;
                setChanged();
                notifyObservers();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else throw new IllegalStateException();
    }


}