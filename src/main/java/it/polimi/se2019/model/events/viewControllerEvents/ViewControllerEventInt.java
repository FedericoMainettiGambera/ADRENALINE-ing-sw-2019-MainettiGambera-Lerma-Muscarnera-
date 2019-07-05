package it.polimi.se2019.model.events.viewControllerEvents;

/**a event that contains a int sent by the user
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class ViewControllerEventInt extends ViewControllerEvent {
    /**the input sent by the user*/
    private int input;
    /**constructor,
     * @param input to set input attribute*/
    public ViewControllerEventInt(int input){
        super();
        this.input = input;
    }
    /**@return input*/
    public int getInput(){
        return this.input;
    }
}
