package it.polimi.se2018.model;

class ActionContext {
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ActionContext ( Player player) {
        setPlayer(player);
    }
    private Player player;          // context of the action: initialized during Effect.exec()
}