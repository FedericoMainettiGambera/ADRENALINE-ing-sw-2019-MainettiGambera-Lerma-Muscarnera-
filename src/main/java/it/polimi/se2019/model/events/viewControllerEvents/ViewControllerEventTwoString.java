package it.polimi.se2019.model.events.viewControllerEvents;

/**event containing two string sent by the player
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class ViewControllerEventTwoString extends ViewControllerEvent {
    /**attribute containing first string that was sent*/
    private String input1;
    /**attribute containing second string that was sent*/
    private String input2;
    /**constructor,
     * @param input1 to set input1 attribute
     * @param input2 to set input2 attribute*/
    public ViewControllerEventTwoString(String input1, String input2){
        super();
        this.input1 = input1;
        this.input2 = input2;

    }
    /**@return input1*/
    public String getInput1() {
        return input1;
    }
    /**@return input2*/
    public String getInput2() {
        return input2;
    }
}
