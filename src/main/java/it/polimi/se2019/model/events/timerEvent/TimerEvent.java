package it.polimi.se2019.model.events.timerEvent;

import it.polimi.se2019.model.events.Event;

import java.io.Serializable;
/**event that tells the player that a count down has been triggered
 * and perform the count down
 * @author FedericoMainettiGambera
 * @author LudoLerma */
public class TimerEvent extends Event implements Serializable {
    /**value that contains the current time when the timerEvent has been called*/
    private int currentTime;
    /**value that contains the total time*/
    private int totalTime;
    /**string that contains the context in which the timer has been triggered*/
    private String context;
    /**constructor,
     * @param currentTime to set the currentTime attribute
     * @param context to set the context attribute
     * @param totalTime to set the totalTime attribute*/
    public TimerEvent(int currentTime, int totalTime, String context){
        this.currentTime= currentTime;
        this.totalTime = totalTime;
        this.context = context;
    }
    /**@return currentTime*/
    public int getCurrentTime() {
        return currentTime;
    }
    /**return totalTime*/
    public int getTotalTime() {
        return totalTime;
    }
    /**return context*/
    public String getContext() {
        return context;
    }
}
