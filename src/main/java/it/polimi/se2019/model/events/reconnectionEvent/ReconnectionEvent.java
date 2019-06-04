package it.polimi.se2019.model.events.reconnectionEvent;

import it.polimi.se2019.model.events.Event;

public class ReconnectionEvent extends Event {
    private String oldNickname;
    public ReconnectionEvent(String oldNickname){
        this.oldNickname=oldNickname;
    }
    public String getOldNickname() {
        return oldNickname;
    }
}

