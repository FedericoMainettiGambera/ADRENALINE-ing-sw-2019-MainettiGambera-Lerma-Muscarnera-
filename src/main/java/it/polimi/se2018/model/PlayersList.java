package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.PlayersColors;
import java.util.ArrayList;
import java.util.List;

/**keeps track of the Players playing the current game*/
public class PlayersList {

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
    }

    public Player getStartingPlayer(){
        return this.startingPlayer;
    }

    public void setStartingPlayer(Player startingPlayer){
        this.startingPlayer = startingPlayer;
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

    /**@return the number of players in the playersList
     * */
    public int getNumberOfPlayers(){
        return this.players.size();
    }

    /**deletes a player from the playersList
     * @param nickname
     * */
    public boolean removePlayer(String nickname) {
        if(this.players.contains(getPlayer(nickname))){
            this.players.remove(getPlayer(nickname));
            return true;
        }
        else{
            return false;
        }
    }

    /**deletes a player from the playersList
     * @param player
     * @return
     * */
    public boolean removePlayer(Player player){
        if(this.players.contains(player)){
            this.players.remove(player);
            return true;
        }
        else{
            return false;
        }
    }

}