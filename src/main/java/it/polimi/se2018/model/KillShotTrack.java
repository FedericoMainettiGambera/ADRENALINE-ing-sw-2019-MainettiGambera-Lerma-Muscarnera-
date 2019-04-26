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
       if((numberOfStartingSkulls>4)&&(numberOfStartingSkulls<8)){
           for (int index = 0; index < numberOfStartingSkulls; index++) {
               kills.add(new Kill());
           }
       }
       else {
           throw new Exception("illegal numberOfStartingSkulls");
       }
    }


    /*-****************************************************************************************************ATTRIBUTES*/

    private List<Kill> kills;
    /*-*********************************************************************************************************METHODS*/

    public boolean areThereSkulls()
    {
        return (kills.size() != 0);
    }

    public  void deleteSkull() throws IllegalStateException {
        if (!areThereSkulls()) {
            kills.remove(kills.size()-1);
        }
        else throw new IllegalStateException();
    }


}