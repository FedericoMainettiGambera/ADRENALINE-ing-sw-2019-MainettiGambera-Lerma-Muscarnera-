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
     * @param selectorEventType
     * @param amount
     * */
    public SelectorEventPaymentInformation(SelectorEventTypes selectorEventType, AmmoListV amount) {
        super(selectorEventType);
        possibilities = new ArrayList<>();
        this.amount = amount;
    }
    public void setCanPayWithoutPowerUps(boolean canPayWithoutPowerUps){
        this.canPayWithoutPowerUps = canPayWithoutPowerUps;
    }

    public AmmoListV getAmount() {
        return amount;
    }

    public boolean canPayWithoutPowerUps() {
        return canPayWithoutPowerUps;
    }

    public List<PowerUpCardV> getPossibilities() {
        return possibilities;
    }

    public void addPossibility(PowerUpCardV p){
        possibilities.add(p);
    }
}
