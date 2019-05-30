package it.polimi.se2019.model.events.modelViewEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
import java.util.Observable;

/**this class holds all the information needed to update the view when the Model changes*/
public class ModelViewEvent extends Event implements Serializable {

    private Object component;
    private ModelViewEventTypes information;
    private Object extraInformation1;
    private Object extraInformation2;

    public ModelViewEvent(Object component, ModelViewEventTypes information){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
    }

    public ModelViewEvent(Object component, ModelViewEventTypes information, Object extraInformation1){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
        this.extraInformation1 = extraInformation1;
    }

    public ModelViewEvent(Object component, ModelViewEventTypes information, Object extraInformation1, Object extraInformation2){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
        this.extraInformation1 = extraInformation1;
        this.extraInformation2 = extraInformation2;
    }

    public Object getExtraInformation2() {
        return extraInformation2;
    }

    public Object getExtraInformation1(){ return  this.extraInformation1;}

    public Object getComponent(){
        return this.component;
    }

    public ModelViewEventTypes getInformation(){
        return this.information;
    }
}
