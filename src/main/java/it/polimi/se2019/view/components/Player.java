package it.polimi.se2019.view.components;

import it.polimi.se2019.model.events.ModelViewEvent;

public class Player implements ViewComponent {

    private PlayerHand playerHand;
    private PlayerBoard playerBoard;
    private PlayerInfo playerInfo;

    public Player(){

        this.playerBoard=new PlayerBoard();
        this.playerInfo=new PlayerInfo();
        this.playerHand=new PlayerHand();

    }


    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public PlayerHand getPlayerHand(){

        return playerHand;


    }

    @Override
    public void display(ModelViewEvent MVE) {

    }
}

