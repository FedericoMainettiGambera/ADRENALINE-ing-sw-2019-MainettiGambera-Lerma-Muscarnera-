package it.polimi.se2019.model.events.viewControllerEvents;

import java.io.Serializable;
import java.util.List;
/**event that receive events information from player
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class ViewControllerEventPaymentInformation extends ViewControllerEvent implements Serializable {
    /**list of object composing the payment information sent by player */
    private List<Object> answer;
    /**constructor,
     * @param answer to set answer attribute*/
    public ViewControllerEventPaymentInformation(List<Object> answer){
        this.answer = answer;
    }
    /**@return answer*/
    public List<Object> getAnswer() {
        return answer;
    }
}
