package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.KillShotTrackV;
import it.polimi.se2019.view.components.KillsV;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

/**regular killshot track*/
public class KillShotTrack extends Observable implements Serializable {

    private static Logger logger=Logger.getLogger(KillShotTrack.class.getName());

    /* **************************************************************************CONSTRUCTOR*/


    private int startingSKulls;

    /**
     * CONSTRUCTOR
     * @param numberOfStartingSkulls
     * as many skulls as indicated by number
     * */
    public KillShotTrack(int numberOfStartingSkulls, VirtualView VVSocket, VirtualView VVRMI){

        this.addObserver(VVSocket);
        this.addObserver(VVRMI);

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
        startingSKulls=numberOfStartingSkulls;
    }


    /*-****************************************************************************************************ATTRIBUTES*/
    /**list of kills and skulls*/
    private List<Kill> kills;

    /**number of remaining skulls*/
    private int numberOfRemainingSkulls;
    /*-*********************************************************************************************************METHODS*/

    /**as the number of skull is set once for all at the beginning of the game, it is only allowed to delete'em
     * @param isOverKill @tracks down if player committed an overkill
     * @param killingPlayer @tracks down whom committed a kill
     * */
    public void deathOfPlayer(Player killingPlayer, boolean isOverKill){

        //TODO: read following comment
        //prendiamo per ipotesi un gioco a 5 startingSkulls e quindi una killshot track a 5 Kills
        //durante il gioco muoiono le 5 persone e viene triggerata la FF
        //se nella FF muore qualcuno quest'ultimo non viene inserito nella killshot track ! problema !
        //è importante sistemare questa cosa perchè può fare la differenza nell'assegnazione dei punti nel FinalScoringState

        if (numberOfRemainingSkulls>0) {
            try {
                kills.get(startingSKulls-numberOfRemainingSkulls).setKillingPlayer(killingPlayer);
                if (isOverKill) {
                    kills.get(startingSKulls-numberOfRemainingSkulls).setOverkillingPlayer(killingPlayer);
                    kills.get(startingSKulls-numberOfRemainingSkulls).setOccurance(startingSKulls-numberOfRemainingSkulls);
                }
                numberOfRemainingSkulls--;
                setChanged();
                notifyObservers(new ModelViewEvent(this.buildKillshotTrackV(), ModelViewEventTypes.deathOfPlayer));
            }
            catch(Exception e){
                logger.severe("Error occured in KillShotTrack during deathOfPlayer: "+ e.getCause()+ Arrays.toString(e.getStackTrace()));
            }
        }
        else throw new IllegalStateException("Exceeded the maximum number of skulls");
    }
    /**@return kills, a list of kill
     * */
    public List<Kill> returnKills(){
        return this.kills;
    }

    /**@return boolean value that tells us whether the skulls are over or not
     * */
    public boolean areSkullsOver(){
        return (this.numberOfRemainingSkulls <= 0);
    }

    /**@return int value that tells us how many skulls are left to play
     * */
    public int getNumberOfRemainingSkulls() {
        return numberOfRemainingSkulls;
    }

    /**Build the equivalent structure for view purposes
     * tracks down just as much information as needed for view to show
     @return killShotTrackV
     * */
    public KillShotTrackV buildKillshotTrackV(){
        KillShotTrackV killShotTrackV = new KillShotTrackV();
        List<KillsV> listOfKillsV = new ArrayList<>();
        KillsV tempKill;
        for (Kill k: this.kills) {
            if(k!=null) {
                tempKill = new KillsV();
                try {
                    if (k.getKillingPlayer() != null) {
                        tempKill.setKillingPlayer(k.getKillingPlayer().getNickname());
                    }
                    tempKill.setSkull(k.isSkull());
                    tempKill.setOverKill(k.isOverKill());
                    if (k.getOverKillingPlayer() != null) {
                        tempKill.setOverKillingPlayer(k.getOverKillingPlayer().getNickname());
                    }
                    listOfKillsV.add(tempKill);
                } catch (Exception e) {
                   logger.severe("Error occurred" + Arrays.toString(e.getStackTrace())+e.getCause()+e.getClass());
                }
            }
        }
        killShotTrackV.setKillsV(listOfKillsV);
        killShotTrackV.setNumberOfStartingSkulls(startingSKulls);

        return killShotTrackV;
    }
}