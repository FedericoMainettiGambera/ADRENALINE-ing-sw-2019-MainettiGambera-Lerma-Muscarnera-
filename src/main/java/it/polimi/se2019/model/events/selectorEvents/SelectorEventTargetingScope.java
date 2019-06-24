package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;

import java.io.Serializable;
import java.util.List;
public class SelectorEventTargetingScope extends SelectorEvent implements Serializable {
    private List<PowerUpCardV> listOfTargetingScopeV;
    private List<Object> possiblePaymentsV;
    private List<PlayerV> damagedPlayersV;
    public SelectorEventTargetingScope(SelectorEventTypes selectorEventType, List<PowerUpCardV> listOfTargetingScopeV, List<Object> possiblePaymentsV, List<PlayerV> damagedPlayersV) {
        super(selectorEventType);
        this.listOfTargetingScopeV =listOfTargetingScopeV;
        this.possiblePaymentsV = possiblePaymentsV;
        this.damagedPlayersV = damagedPlayersV;
    }

    public List<Object> getPossiblePaymentsV() {
        return possiblePaymentsV;
    }

    public List<PlayerV> getDamagedPlayersV() {
        return damagedPlayersV;
    }

    public List<PowerUpCardV> getListOfTargetingScopeV() {
        return listOfTargetingScopeV;
    }
}
