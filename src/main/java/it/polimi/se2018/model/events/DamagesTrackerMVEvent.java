package it.polimi.se2018.model.events;

import it.polimi.se2018.model.DamagesTracker;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.enumerations.ModelViewEventType;

public class DamagesTrackerMVEvent extends ModelViewEvent{
    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public DamagesTrackerMVEvent(DamagesTracker damagesTracker, Player player){
        super(ModelViewEventType.DamagesTracker);
        this.damagesTracker = damagesTracker;
        this.player = player;
    }

    public DamagesTrackerMVEvent(String informations){
        super(ModelViewEventType.DamagesTracker);
        this.parse(informations);
    }

    /*-*****************************************************************************************************ATTRIBUTES*/

    private DamagesTracker damagesTracker;

    private Player player;

    /*-********************************************************************************************************METHODS*/

    /**@return a string that contains all the information needed*/
    @Override
    public String stringify() {
        return damagesTracker.stringify() + player.stringify();
    }

    /**builds the event from a String ( inverse of toString())
     * */
    @Override
    public void parse(String informations){
        try {
            this.damagesTracker = DamagesTracker.parse(informations);
            this.player = Player.parse(informations);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
