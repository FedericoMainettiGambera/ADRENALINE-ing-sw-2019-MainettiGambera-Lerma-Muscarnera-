package it.polimi.se2019.model.events.modelViewEvents;

import it.polimi.se2019.model.enumerations.EventTypes;
import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
import java.util.Observable;

/**this class holds all the information needed to update the view when the Model changes*/
public class ModelViewEvent extends Event implements Serializable {

    private Observable o;
    private Object arg;

    public ModelViewEvent(Observable o, Object arg){
        this.setEventType(EventTypes.ModelViewEvent);
        this.o = o;
        this.arg = arg;
    }

    public Observable getO(){
        return  this.o;
    }

    public Object getArg(){
        return  this.arg;
    }
}
