package it.polimi.se2018.model.events;

import java.net.InetAddress;

public class ViewControllerEventClientIP extends ViewControllerEvent {
    InetAddress inetAddress;

    public ViewControllerEventClientIP(InetAddress inetAddress){
        this.inetAddress = inetAddress;
    }

    public InetAddress getInetAddress(){
        return this.inetAddress;
    }
}
