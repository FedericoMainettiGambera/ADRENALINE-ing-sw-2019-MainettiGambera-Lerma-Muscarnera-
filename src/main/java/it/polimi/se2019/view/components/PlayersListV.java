package it.polimi.se2019.view.components;


import java.io.Serializable;
import java.util.List;

public class PlayersListV implements Serializable {
    private List<PlayerV> players;

    private PlayerV currentPlayingPlayer;

    private PlayerV startingPlayer;

    public void setPlayers(List<PlayerV> players) {
        this.players = players;
    }

    public List<PlayerV> getPlayers() {
        return players;
    }

    public PlayerV getCurrentPlayingPlayer() {
        return currentPlayingPlayer;
    }

    public PlayerV getStartingPlayer() {
        return startingPlayer;
    }

    public void setCurrentPlayingPlayer(PlayerV currentPlayingPlayer) {
        this.currentPlayingPlayer = currentPlayingPlayer;
    }

    public void setStartingPlayer(PlayerV startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
