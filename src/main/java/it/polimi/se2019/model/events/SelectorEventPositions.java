package it.polimi.se2019.model.events;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;

import java.util.ArrayList;

public class SelectorEventPositions extends SelectorEvent {

    ArrayList<Position> positions;

    public SelectorEventPositions(SelectorEventTypes selectorEventType, ArrayList<Position> positions) {
        super(selectorEventType);
        this.positions = positions;
    }

    public ArrayList<Position> getPositions(){
        return this.positions;
    }
}
