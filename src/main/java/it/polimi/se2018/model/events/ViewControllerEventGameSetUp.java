package it.polimi.se2018.model.events;

public class ViewControllerEventGameSetUp extends ViewControllerEvent {

    private String gameMode;

    private String mapChoice;

    private int numberOfStartingSkulls;

    private boolean isFinalFrenzy;

    private boolean isBotActive;

    public ViewControllerEventGameSetUp(String gameMode, String mapChoice, int numberOfStartingSkulls, boolean isFinalFrenzy, boolean isBotActive){
        this.gameMode = gameMode;
        this.mapChoice = mapChoice;
        this.numberOfStartingSkulls = numberOfStartingSkulls;
        this.isFinalFrenzy = isFinalFrenzy;
        this.isBotActive = isBotActive;
    }

    public String getGameMode(){
        return this.gameMode;
    }
    public String getMapChoice(){
        return this.mapChoice;
    }
    public int getNumberOfStartingSkulls(){
        return this.numberOfStartingSkulls;
    }
    public boolean isFinalFrezy(){
        return isFinalFrenzy;
    }
    public boolean isBotActive(){ return isBotActive;}
}
