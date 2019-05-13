package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.GameMode;

import java.io.Serializable;
import java.lang.*;
import java.util.Observable;


/***/
public class GameSetUp extends Observable implements Serializable {

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
        setChanged();
        notifyObservers();
    }

    public void setMapChoice(String chosenMap){
        this.mapChoice=chosenMap;
        setChanged();
        notifyObservers();
    }

    public void setDoubleKill(boolean isDoubleKill){
        this.isDoubleKill=isDoubleKill;
        setChanged();
        notifyObservers();
    }

    public void setNumberOfStartingSkulls(int numberOfStartingSkulls){
        this.numberOfStartingSkulls=numberOfStartingSkulls;;
        setChanged();
        notifyObservers();
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        isFinalFrenzy = finalFrenzy;
        setChanged();
        notifyObservers();
    }

    /**getters*/

    public GameMode getGameMode(){
        return this.gameMode;
    }

    public  String getMapChoice(){ return this.mapChoice;}

    public boolean getIsDoubleKill(){ return this.isDoubleKill;}

    public int getNumberOfStartingSkulls(){ return this.numberOfStartingSkulls;}

    public boolean getIsFinalFrenzy(){ return this.isFinalFrenzy; }
}