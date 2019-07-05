package it.polimi.se2019.model.events.viewControllerEvents;

/**a event containing a string from the user
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class ViewControllerEventString extends ViewControllerEvent {
    /**the string from the user*/
    private String input;
    /**constructor,
     * @param input needed to set up input attribute*/
    public ViewControllerEventString(String input){
        super();
        this.input = input;
    }
    /**@return input*/
    public String getInput() {
        return input;
    }
}
