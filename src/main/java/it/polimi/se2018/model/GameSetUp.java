package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.GameMode;
import java.lang.*;


/***/
public class GameSetUp {

    /***/
    public GameSetUp(){
        this.gameMode=null;
        this.numberOfStartingSkulls=0;
        this.isDoubleKill=false;
        this.isFinalFrenzy=false;
        this.mapChoice=null;
    }

    /***/
    private GameMode gameMode;

    /***/
    private String mapChoice;

    /***/
    private boolean isFinalFrenzy;

    /***/
    private boolean isDoubleKill;

    /***/
    private int numberOfStartingSkulls;



    /** setters  */
    public void setGameMode(GameMode gameMode){

        this.gameMode=gameMode;
    }

    public void setMapChoice(String chosenMap){

        this.mapChoice=chosenMap;

    }

    public void setDoubleKill(boolean isDoubleKill){

        this.isDoubleKill=isDoubleKill;

    }

    public void setNumberOfStartingSkulls(int numberOfStartingSkulls){

        this.numberOfStartingSkulls=numberOfStartingSkulls;;

    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        isFinalFrenzy = finalFrenzy;
    }

    /**getters*/

    public GameMode getGameMode(){return this.gameMode; }

    public  String getMapChoice(){ return this.mapChoice;}

    public boolean getIsDoubleKill(){ return this.isDoubleKill;}

    public int getNumberOfStartingSkulls(){ return this.numberOfStartingSkulls;}

    public boolean getIsFinalFrenzy(){ return this.isFinalFrenzy; }
}