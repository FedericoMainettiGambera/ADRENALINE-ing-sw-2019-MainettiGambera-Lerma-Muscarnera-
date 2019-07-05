package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PlayerV;

import java.io.Serializable;
import java.util.List;

/** @author LudoLerma
 *   @author FedericoMainettiGambera
 *  this event is used to ask the user which player they want the bot to shoot */
public class SelectorEventPlayers extends SelectorEvent implements Serializable{
   /**a list of the possible players the bot can shoot*/
    private List<PlayerV> playerVList;

    /**@param selectorEventType the type of selector event
     * @param playerVList to set the playerVList attribute*/
    public SelectorEventPlayers(SelectorEventTypes selectorEventType, List<PlayerV> playerVList) {
        super(selectorEventType);
        this.playerVList=playerVList;
    }

    /**@return playerVList*/
    public List<PlayerV> getPlayerVList() {
        return playerVList;
    }
}
