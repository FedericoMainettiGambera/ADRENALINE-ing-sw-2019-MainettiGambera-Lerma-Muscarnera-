package it.polimi.se2019.model.events;

import it.polimi.se2019.model.enumerations.PlayersColors;

public class ViewControllerEventPlayerSetUp extends ViewControllerEvent {

    private String nickname;

    private PlayersColors color;

    public ViewControllerEventPlayerSetUp(String nickname, PlayersColors color){
        super();
        this.nickname = nickname;
        this.color = color;
    }

    public String getNickname(){
        return this.nickname;
    }

    public PlayersColors getColor(){
        return this.color;
    }
}
