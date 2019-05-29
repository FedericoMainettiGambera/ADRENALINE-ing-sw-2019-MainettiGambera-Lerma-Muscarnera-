package it.polimi.se2019.model.events.modelViewEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
import java.util.Observable;

/**this class holds all the information needed to update the view when the Model changes*/
public class ModelViewEvent extends Event implements Serializable {

    private Object component;
    private String information;

    public ModelViewEvent(Object component, String information){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
    }

    public Object getComponent(){
        return this.component;
    }

    public String getInformation(){
        return this.information;
    }
}
