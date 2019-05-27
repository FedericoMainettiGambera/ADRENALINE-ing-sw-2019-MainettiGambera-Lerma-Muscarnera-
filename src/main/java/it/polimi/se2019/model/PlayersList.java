package it.polimi.se2019.model;

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
        notifyObservers("CURRENT PLAYING PLAYER");
    }

    public Player getStartingPlayer(){
        return this.startingPlayer;
    }

    public void setStartingPlayer(Player startingPlayer){
        this.startingPlayer = startingPlayer;
        setChanged();
        notifyObservers("STARTING PLAYER");
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
        setChanged();
        notifyObservers("NEXT PLAYING PLAYER");
    }


    /**add a player to the player list
     * @param player
     * */
    public void addPlayer(Player player) {
        this.players.add(player);
        setChanged();
        notifyObservers("NEW PLAYER");
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

}