package it.polimi.se2019.model.events.viewControllerEvents;

import java.io.Serializable;

public class ViewControllerEventNickname extends ViewControllerEvent implements Serializable {
    private String nickname;
    public ViewControllerEventNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
}
