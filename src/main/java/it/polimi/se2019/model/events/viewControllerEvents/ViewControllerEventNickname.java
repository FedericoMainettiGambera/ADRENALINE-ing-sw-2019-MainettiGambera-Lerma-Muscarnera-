package it.polimi.se2019.model.events.viewControllerEvents;

import java.io.Serializable;
/**event that contains the nickname chosen by the user
 * @author LudoLerma
 * @author FedericoMainettiGambera
 * */
public class ViewControllerEventNickname extends ViewControllerEvent implements Serializable {
    /**string that contains the nickname chosen by the user*/
    private String nickname;
    /**@param nickname to set nickname attribute
     * constructor*/
    public ViewControllerEventNickname(String nickname){
        this.nickname = nickname;
    }
    /**@return nickname*/
    public String getNickname() {
        return nickname;
    }
}
