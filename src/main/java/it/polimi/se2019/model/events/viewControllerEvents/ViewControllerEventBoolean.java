package it.polimi.se2019.model.events.viewControllerEvents;

/**implements a event that contains an answer from the user of boolean type
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class ViewControllerEventBoolean extends ViewControllerEvent {
    /**boolean input from user*/
    private boolean input;

    /**constructor,
     * @param input to set input*/
    public ViewControllerEventBoolean(boolean input){
        super();
        this.input = input;
    }

    /**@return input*/
    public boolean getInput(){
        return this.input;
    }
}
