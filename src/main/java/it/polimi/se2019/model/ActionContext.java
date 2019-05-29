package it.polimi.se2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionContext  implements Serializable {
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ActionContext() {
        actionContextFilteredInputs = new ArrayList<>();
    }
    public ActionContext ( Player player) {
        setPlayer(player);
    }

    public List<ActionContextFilteredInput> getActionContextFilteredInputs() {
        return actionContextFilteredInputs;
    }

    List<ActionContextFilteredInput>  actionContextFilteredInputs;

    private Player player;          // context of the action: initialized during Effect.exec() -- current player

    public PlayersList getPlayerList() {
        return playerList;
    }

    public void setPlayerList(PlayersList playerList) {
        this.playerList = playerList;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;
    private PlayersList playerList; // context of action :    initialized during effect.exec() -- list of players
}