package it.polimi.se2018.model.events;

import it.polimi.se2018.model.DamagesTracker;
import it.polimi.se2018.model.MarksTracker;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.enumerations.ModelViewEventType;

public class MarksTrackerMVEvent extends ModelViewEvent {
    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public MarksTrackerMVEvent(MarksTracker marksTracker, Player player){
        super(ModelViewEventType.MarksTracker);
        this.marksTracker = marksTracker;
        this.player = player;
    }

    public MarksTrackerMVEvent(String informations){
        super(ModelViewEventType.MarksTracker);
        this.parse(informations);
    }

    /*-*****************************************************************************************************ATTRIBUTES*/

    private MarksTracker marksTracker;

    private Player player;

    /*-********************************************************************************************************METHODS*/

    /**@return a string that contains all the information needed*/
    @Override
    public String stringify() {
        return marksTracker.stringify() + player.stringify();
    }

    /**builds the event from a String ( inverse of toString())
     * */
    @Override
    public void parse(String informations){
        this.marksTracker = MarksTracker.parse(informations);
        this.player =  Player.parse(informations);
    }
}
