package it.polimi.se2018.view;

public class Player {

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
}

