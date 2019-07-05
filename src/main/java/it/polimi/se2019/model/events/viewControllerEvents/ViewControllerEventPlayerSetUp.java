package it.polimi.se2019.model.events.viewControllerEvents;

import it.polimi.se2019.model.enumerations.PlayersColors;
/**event that receives data from the player needed to set up a player
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class ViewControllerEventPlayerSetUp extends ViewControllerEvent {
    /**string containing the nickname of the player*/
    private String nickname;
    /**string containing the color of the player*/
    private PlayersColors color;
    /**constructor,
     * @param nickname  needed to set up nickname attribute
     * @param color needed to set up color attribute*/
    public ViewControllerEventPlayerSetUp(String nickname, PlayersColors color){
        super();
        this.nickname = nickname;
        this.color = color;
    }
    /**@return nickname*/
    public String getNickname(){
        return this.nickname;
    }
    /**@®return color*/
    public PlayersColors getColor(){
        return this.color;
    }
}
