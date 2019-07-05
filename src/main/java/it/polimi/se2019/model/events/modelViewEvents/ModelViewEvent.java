package it.polimi.se2019.model.events.modelViewEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
import java.util.Observable;

/**this class holds all the information needed to update the view when the Model changes
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class ModelViewEvent extends Event implements Serializable {

    /**the object of the model that changed and which changement need to be notified to the client*/
    private Object component;
    /**specify how to decode the event*/
    private ModelViewEventTypes information;
    /**primary object information to send to client*/
    private Object extraInformation1;
    /**primary object information to send to client*/
    private Object extraInformation2;

    /**constructor
     * @param component to set the component attribute
     * @param information to set the information attribute
     * set the eventType as ModelViewEvent*/
    public ModelViewEvent(Object component, ModelViewEventTypes information){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
    }

    /**constructor
     * @param component to set the component attribute
     * @param information to set the information attribute
     * @param extraInformation1 to set extrainformation1*/
    public ModelViewEvent(Object component, ModelViewEventTypes information, Object extraInformation1){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
        this.extraInformation1 = extraInformation1;
    }

    /**constructor
     * @param component to set the component attribute
     * @param information to set the information attribute
     * @param extraInformation1 to set extrainformation1 attribute
     * @param extraInformation2  to set extrainformation2 attribute
     * */
    public ModelViewEvent(Object component, ModelViewEventTypes information, Object extraInformation1, Object extraInformation2){
        this.setEventType(EventTypes.ModelViewEvent);
        this.component = component;
        this.information = information;
        this.extraInformation1 = extraInformation1;
        this.extraInformation2 = extraInformation2;
    }

    /**@return extraInformation2*/
    public Object getExtraInformation2() {
        return extraInformation2;
    }
    /**@return extraInformation1*/
    public Object getExtraInformation1(){ return  this.extraInformation1;}
    /**@return component*/
    public Object getComponent(){
        return this.component;
    }
    /**@return information*/
    public ModelViewEventTypes getInformation(){
        return this.information;
    }
}
