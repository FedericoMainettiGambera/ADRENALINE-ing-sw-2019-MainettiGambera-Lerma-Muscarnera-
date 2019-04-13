package it.polimi.se2018.model;


import java.util.List;


/***/
public class KillShotTrack{

    /***/
    private List<Kill> kills;

    /***/
    public KillShotTrack(int number) throws Exception{
       if((number>4)&&(number<8)){
           for (int index = 0; index < number; index++) {
               kills.add(new Kill());
           }
       }
       else {
           throw new Exception("illegal numberOfStartingSkulls");
       }
    }

}