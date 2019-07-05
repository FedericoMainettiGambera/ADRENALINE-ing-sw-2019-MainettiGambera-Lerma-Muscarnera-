package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.Serializable;
import java.util.List;
/**@author LudoLerma
 *  @author FedericoMainettiGambera
 *  event that asks the player the needed input for targeting scope usage
 *  */
public class SelectorEventTargetingScope extends SelectorEvent implements Serializable {
   /**a list of targeting scopes the player can choose beetwen*/
    private List<PowerUpCardV> listOfTargetingScopeV;
    /**the possible way those targeting scope can be paid for*/
    private List<Object> possiblePaymentsV;
    /**a list of damaged player to ask if they want to use targeting scope*/
    private List<PlayerV> damagedPlayersV;

    /**@param selectorEventType indicates the kind of selectorEvent
     * @param damagedPlayersV to set damagedPlayerV attribute
     * @param listOfTargetingScopeV to set listOfTargetingScopeV attribute
     * @param possiblePaymentsV to set possiblePaymentsV attribute
     * */
    public SelectorEventTargetingScope(SelectorEventTypes selectorEventType, List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        super(selectorEventType);
        this.listOfTargetingScopeV =listOfTargetingScopeV;
        this.possiblePaymentsV = possiblePaymentsV;
        this.damagedPlayersV = damagedPlayersV;
    }

    /**@return possiblePaymentV*/
    public List<Object> getPossiblePaymentsV() {
        return possiblePaymentsV;
    }

    /**@return damagedPlayersV*/
    public List<PlayerV> getDamagedPlayersV() {
        return damagedPlayersV;
    }

    /**@return listOfTargetingScopeV
     * */
    public List<PowerUpCardV> getListOfTargetingScopeV() {
        return listOfTargetingScopeV;
    }
}
