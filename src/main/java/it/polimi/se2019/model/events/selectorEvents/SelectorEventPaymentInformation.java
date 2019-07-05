package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.AmmoListV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**implements a event that allows to ask the player how he wants to pay
 * @author LudoLerma
 * @author FedericoMainettiGambera
 * */
public class SelectorEventPaymentInformation extends SelectorEvent implements Serializable {
    /**a list of powerUp card that could be discarded to pay*/
    private List<PowerUpCardV> possibilities;
    /**if the payment is possible without using powerUps*/
    private boolean canPayWithoutPowerUps;
    /**the amount to be paid*/
    private AmmoListV amount;
    /**constructor,
     * @param selectorEventType the type of event
     * @param amount to set the amount attribute
     * */
    public SelectorEventPaymentInformation(SelectorEventTypes selectorEventType, AmmoListV amount) {
        super(selectorEventType);
        possibilities = new ArrayList<>();
        this.amount = amount;
    }
    /**@param canPayWithoutPowerUps  set the canPayWithoutPowerUps attribute*/
    public void setCanPayWithoutPowerUps(boolean canPayWithoutPowerUps){
        this.canPayWithoutPowerUps = canPayWithoutPowerUps;
    }
    /**@return  amount */
    public AmmoListV getAmount() {
        return amount;
    }
    /**@return canPayWithoutPowerUps attribute*/
    public boolean canPayWithoutPowerUps() {
        return canPayWithoutPowerUps;
    }

    /**@return the possibilities attribute*/
    public List<PowerUpCardV> getPossibilities() {
        return possibilities;
    }

    /**@param p a powerup card to be add to the possibilities list*/
    public void addPossibility(PowerUpCardV p){
        possibilities.add(p);
    }
}
