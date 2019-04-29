package it.polimi.se2018.model;


import java.util.List;
import java.lang.Exception;

/***/
public class KillShotTrack{
    /* **************************************************************************CONSTRUCTOR*/

    /**
     * CONSTRUCTOR
     * @param numberOfStartingSkulls
     * as many skulls as indicated by number
     * */
    public KillShotTrack(int numberOfStartingSkulls) throws Exception{
       if((numberOfStartingSkulls>4)&&(numberOfStartingSkulls<=8)){
           for (int index = 0; index < numberOfStartingSkulls; index++) {
               kills.add(new Kill());
           }
       }
       else {
           throw new Exception("illegal numberOfStartingSkulls: " + Integer.toString(numberOfStartingSkulls));
       }
    }


    /*-****************************************************************************************************ATTRIBUTES*/

    private List<Kill> kills;
    /*-*********************************************************************************************************METHODS*/

    /**
     * check if there number of skulls is equal to zero*/
    public boolean areThereSkulls()
    {
        return (kills.size() != 0);
    }

    /**
     *as the number of skull is set once for all at the beginning of the game, it is only allowed to delete'em
     * */
    public  void deleteSkull() throws IllegalStateException {
        if (!areThereSkulls()) {
            kills.remove(kills.size()-1);
        }
        else throw new IllegalStateException();
    }


}