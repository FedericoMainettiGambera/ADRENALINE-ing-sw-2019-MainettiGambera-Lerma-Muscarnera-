package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
/**event that asks the player what they want to do
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class SelectorEventTurnAction extends SelectorEvent {

    /**if it's action number 1 or number 2*/
    private int actionNumber;

    /**indicates if the user is allowed to use powerUp*/
    private boolean canUsePowerUp;

    /**indicates if the user is allowed to use the bot*/
    private boolean canUseBot;

    /**@param selectorEventType the type of input event
     * @param actionNumber to set action number attribute
     * @param canUsePowerUp to set canUsePowerUp attribute
     * @param canUseBot to set canUseBot attribute
     * */
    public SelectorEventTurnAction(SelectorEventTypes selectorEventType, int actionNumber, boolean canUsePowerUp, boolean canUseBot){
        super(selectorEventType);
        this.actionNumber = actionNumber;
        this.canUsePowerUp = canUsePowerUp;
        this.canUseBot = canUseBot;
    }

    /**@return canUseBot*/
    public boolean canUseBot() {
        return canUseBot;
    }

    /**@return canUsePowerUp*/
    public boolean canUsePowerUp() {
        return canUsePowerUp;
    }

    /**@return actionNumber*/
    public int getActionNumber() {
        return actionNumber;
    }
}
