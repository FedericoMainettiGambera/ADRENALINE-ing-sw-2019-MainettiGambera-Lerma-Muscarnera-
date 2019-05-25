package it.polimi.se2019.model.events.viewControllerEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

/**this class holds all the user-input information*/
public class ViewControllerEvent extends Event implements Serializable {

    public ViewControllerEvent(){
        this.setEventType(EventTypes.ViewControllerEvent);
    }

}
