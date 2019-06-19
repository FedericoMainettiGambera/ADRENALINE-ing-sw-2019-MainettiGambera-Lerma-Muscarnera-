package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.AmmoListV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectorEventPaymentInformation extends SelectorEvent implements Serializable {
    private List<PowerUpCardV> possibilities;
    private boolean canPayWithoutPowerUps;
    private AmmoListV amount;
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
