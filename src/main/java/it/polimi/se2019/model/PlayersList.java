package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PlayersListV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**keeps track of the Players playing the current game*/
public class PlayersList extends Observable implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * create a new Arraylist of Player
     * */
    public PlayersList() {
        players = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**list of players*/
    private List<Player> players;

    private Player currentPlayingPlayer;

    private Player startingPlayer;

    /*-********************************************************************************************************METHODS*/

    public Player getCurrentPlayingPlayer() {
        return this.currentPlayingPlayer;
    }

    public void setCurrentPlayingPlayer(Player currentPlayingPlayer){
        this.currentPlayingPlayer = currentPlayingPlayer;
        setChanged();
        notifyObservers(new ModelViewEvent(this.currentPlayingPlayer.getNickname(), ModelViewEventTypes.setCurrentPlayingPlayer));
    }

    public Player getStartingPlayer(){
        return this.startingPlayer;
    }

    public void setStartingPlayer(Player startingPlayer){
        this.startingPlayer = startingPlayer;
        setChanged();
        notifyObservers(new ModelViewEvent(this.startingPlayer.getNickname(), ModelViewEventTypes.setStartingPlayer));
    }
    public void setAll(List<Object> p) {
        this.players = new ArrayList<>();
        for(Object x: p)
            players.add((Player) x);
    }

    public void setNextPlayingPlayer(){
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).getNickname() == this.currentPlayingPlayer.getNickname()){
                if(i==this.players.size()-1){
                    setCurrentPlayingPlayer(this.players.get(0));
                }
                else {
                    setCurrentPlayingPlayer(this.players.get(i + 1));
                }
                break;
            }
        }
    }


    /**add a player to the player list
     * @param player
     * */
    public void addPlayer(Player player) {
        this.players.add(player);
        setChanged();
        notifyObservers(new ModelViewEvent(player.buildPlayerV(), ModelViewEventTypes.newPlayer));
    }

    /**@param nickname
     * @return the desired player from the playersList
     * */
    public Player getPlayer(String nickname) {
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).getNickname().equals(nickname)){
                return this.players.get(i);
            }
        }
        /*Wrong Nickname*/
        return null; // ?
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    /**@return the number of players in the playersList
     * */
    public int getNumberOfPlayers(){
        return this.players.size();
    }

    public PlayersListV buildPlayersListV(){
        PlayersListV playersListV = new PlayersListV();
        List<PlayerV> listOfPlayerV = new ArrayList<>();
        PlayerV tempPlayerV;
        for (Player p : this.players) {
            tempPlayerV = p.buildPlayerV();
            listOfPlayerV.add(tempPlayerV);
        }
        playersListV.setPlayers(listOfPlayerV);
        if(this.startingPlayer!=null) {
            playersListV.setStartingPlayer(this.startingPlayer.getNickname());
        }
        if(this.currentPlayingPlayer!=null) {
            playersListV.setCurrentPlayingPlayer(this.currentPlayingPlayer.getNickname());
        }

        return playersListV;
    }

}