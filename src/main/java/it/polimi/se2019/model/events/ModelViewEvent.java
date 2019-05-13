package it.polimi.se2019.model.events;

import it.polimi.se2019.model.enumerations.EventTypes;

import java.io.Serializable;

/**this class holds all the information needed to update the view when the Model changes*/
public class ModelViewEvent extends Event implements Serializable {

    public ModelViewEvent(){
        this.setEventType(EventTypes.ModelViewEvent);
    }
}
