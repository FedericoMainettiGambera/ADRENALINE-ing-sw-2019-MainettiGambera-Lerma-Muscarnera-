package it.polimi.se2019.model;

public class ActionContext {
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ActionContext() {


    }
    public ActionContext ( Player player) {
        setPlayer(player);
    }
    private Player player;          // context of the action: initialized during Effect.exec()
}