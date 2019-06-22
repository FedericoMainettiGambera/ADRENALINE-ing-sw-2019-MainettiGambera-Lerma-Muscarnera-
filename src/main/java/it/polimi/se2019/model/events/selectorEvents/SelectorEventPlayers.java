package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.view.components.PlayerV;

import java.io.Serializable;
import java.util.List;

public class SelectorEventPlayers extends SelectorEvent implements Serializable{
    private List<PlayerV> playerVList;

    public SelectorEventPlayers(SelectorEventTypes selectorEventType, List<PlayerV> playerVList) {
        super(selectorEventType);
        this.playerVList=playerVList;
    }

    public List<PlayerV> getPlayerVList() {
        return playerVList;
    }
}
