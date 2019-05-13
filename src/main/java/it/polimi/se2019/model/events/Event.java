package it.polimi.se2019.model.events;


import it.polimi.se2019.model.enumerations.EventTypes;

public abstract class Event {

    private EventTypes eventType ;

    public void setEventType(EventTypes eventType){
        this.eventType = eventType;
    }

    public EventTypes getEventType(){
        return this.eventType;
    }
}
