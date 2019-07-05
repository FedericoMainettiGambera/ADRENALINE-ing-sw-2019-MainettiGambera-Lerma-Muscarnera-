package it.polimi.se2019.model.events.viewControllerEvents;

import java.util.List;
/**event containing a list of object sent by the user
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class ViewControllerEventListOfObject extends ViewControllerEvent {
    /**contains the list of object sent by the user*/
    private List<Object> answer;
    /**constructor,
     * @param answer to set answer attribute*/
    public ViewControllerEventListOfObject(List<Object> answer){
        this.answer = answer;
    }
    /**@return answer*/
    public List<Object> getAnswer() {
        return answer;
    }
}
