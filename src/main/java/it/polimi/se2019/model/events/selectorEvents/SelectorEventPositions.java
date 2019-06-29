package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import java.util.List;

public class SelectorEventPositions extends SelectorEvent {

    List<Position> positions;

    public SelectorEventPositions(SelectorEventTypes selectorEventType, List<Position> positions) {
        super(selectorEventType);
        this.positions = positions;
    }

    public List<Position> getPositions(){
        return this.positions;
    }
}
