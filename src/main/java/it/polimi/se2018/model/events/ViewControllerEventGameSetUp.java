package it.polimi.se2018.model.events;

public class ViewControllerEventGameSetUp extends ViewControllerEvent {

    private String gameMode;

    private String mapChoice;

    private int numberOfStratingSkulls;

    private boolean isFinalFrenzy;

    private boolean isDoubleKill;

    public ViewControllerEventGameSetUp(String gameMode, String mapChoice, int numberOfStratingSkulls, boolean isFinalFrenzy, boolean isDoubleKill){
        this.gameMode = gameMode;
        this.mapChoice = mapChoice;
        this.numberOfStratingSkulls = numberOfStratingSkulls;
        this.isFinalFrenzy = isFinalFrenzy;
        this.isDoubleKill = isDoubleKill;
    }

    public String getGameMode(){
        return this.gameMode;
    }
    public String getMapChoice(){
        return this.mapChoice;
    }
    public int getNumberOfStratingSkulls(){
        return this.numberOfStratingSkulls;
    }
    public boolean getIsFinalFrezy(){
        return isFinalFrenzy;
    }

    public boolean getisDoubleKill() {
        return isDoubleKill;
    }
}
