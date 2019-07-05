package it.polimi.se2019.model.events.selectorEvents;

import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import java.util.List;

/** * *
 *  @author FedericoMainettiGambera
 *  @author LudoLerma
 *  this event is set when a position is needed as an input
 *  the player is asked to choose beetwen a list of positions
 *  */
public class SelectorEventPositions extends SelectorEvent {

    /**the list of positions the player has to choose between*/
    private List<Position> positions;

    /**constructor,
     * @param selectorEventType the type of the selector event
     * @param positions to set the positions attribute */
    public SelectorEventPositions(SelectorEventTypes selectorEventType, List<Position> positions) {
        super(selectorEventType);
        this.positions = positions;
    }

    /**@return positions*/
    public List<Position> getPositions(){
        return this.positions;
    }
}
