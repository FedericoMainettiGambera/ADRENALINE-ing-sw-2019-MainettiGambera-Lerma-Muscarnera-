package it.polimi.se2019.model.events.viewControllerEvents;

/**a event that contains the starting player choices to set up the game
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class ViewControllerEventGameSetUp extends ViewControllerEvent {

    /**contains a string indicating the game mode*/
    private String gameMode;
    /**contains a string indicating which map has been chosen*/
    private String mapChoice;
    /**contains the number of starting skulls selected*/
    private int numberOfStartingSkulls;
    /**contains a value that indicates whether the final frenzy as been selected or not*/
    private boolean isFinalFrenzy;
    /**contains a value that indicates whether the bot isActive or not*/
    private boolean isBotActive;

    /**constructor,
     * @param isFinalFrenzy to set isFinalFrenzy
     * @param numberOfStartingSkulls to set numberOfStartingSkulls
     * @param gameMode to set gameMode
     * @param isBotActive to set isBotActive
     * @param mapChoice to set mapChoice*/
    public ViewControllerEventGameSetUp(String gameMode, String mapChoice, int numberOfStartingSkulls, boolean isFinalFrenzy, boolean isBotActive){
        super();
        this.gameMode = gameMode;
        this.mapChoice = mapChoice;
        this.numberOfStartingSkulls = numberOfStartingSkulls;
        this.isFinalFrenzy = isFinalFrenzy;
        this.isBotActive = isBotActive;
    }

    /**@deprecated */
    public String getGameMode(){
        return this.gameMode;
    }
    /**@return mapChoice*/
    public String getMapChoice(){
        return this.mapChoice;
    }
    /**@return numberOfStartingSkulls*/
    public int getNumberOfStartingSkulls(){
        return this.numberOfStartingSkulls;
    }
    /**@return isFinalFrenzy*/
    public boolean isFinalFrezy(){
        return isFinalFrenzy;
    }
    /**@return isBotActive*/
    public boolean isBotActive(){ return isBotActive;}
}
