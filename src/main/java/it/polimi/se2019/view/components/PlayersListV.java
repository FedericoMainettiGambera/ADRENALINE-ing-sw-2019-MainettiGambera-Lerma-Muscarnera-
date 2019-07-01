package it.polimi.se2019.view.components;


import java.io.Serializable;
import java.util.List;

public class PlayersListV implements Serializable {
    private List<PlayerV> players;


    private String currentPlayingPlayer;

    private String startingPlayer;

    public void setPlayers(List<PlayerV> players) {
        this.players = players;
    }

    public List<PlayerV> getPlayers() {
        return players;
    }

    public PlayerV getPlayer(String nickame){
        for (PlayerV player:players){

            if(player.getNickname().equals(nickame)){
                return player;
            }
        }
    return null;
    }

    public String getCurrentPlayingPlayer() {
        return currentPlayingPlayer;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public void setCurrentPlayingPlayer(String currentPlayingPlayer) {
        this.currentPlayingPlayer = currentPlayingPlayer;
    }

    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
