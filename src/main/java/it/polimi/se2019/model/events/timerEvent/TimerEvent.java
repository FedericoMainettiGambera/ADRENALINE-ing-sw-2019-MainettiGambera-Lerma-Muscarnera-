package it.polimi.se2019.model.events.timerEvent;

import it.polimi.se2019.model.events.Event;

import java.io.Serializable;

public class TimerEvent extends Event implements Serializable {
    private int currentTime;
    private int totalTime;
    private String context;
    public TimerEvent(int currentTime,int totalTime, String context){
        this.currentTime= currentTime;
        this.totalTime = totalTime;
        this.context = context;
    }
    public int getCurrentTime() {
        return currentTime;
    }
    public int getTotalTime() {
        return totalTime;
    }
    public String getContext() {
        return context;
    }
}
