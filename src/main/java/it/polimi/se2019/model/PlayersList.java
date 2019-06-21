package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
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
            if(this.players.get(i).getNickname().equals(this.currentPlayingPlayer.getNickname())){
                if(i==this.players.size()-1){
                    setCurrentPlayingPlayer(this.players.get(0));
                }
                else {
                    setCurrentPlayingPlayer(this.players.get(i + 1));
                }
                break;
            }
        }
        if(this.getCurrentPlayingPlayer().isAFK()){
            this.setNextPlayingPlayer();
        }
    }

    public boolean isMinimumPlayerNotAFK(){
        int numberOfPlayerNotAFK = 0;
        for (Player p: this.players) {
            if(!p.isAFK()){
                numberOfPlayerNotAFK++;
            }
        }
        return numberOfPlayerNotAFK < GameConstant.minNumberOfPlayerPerGame;
    }

    public boolean areAllAFK(){
        boolean areAllAFK = true;
        for (Player p: this.players) {
            if(!p.isAFK()){
                areAllAFK = false;
                break;
            }
        }
        return areAllAFK;
    }

    public boolean isSomeoneAFK(){
        boolean isSOmeoneAFK = false;
        for (Player p: this.players) {
            if(p.isAFK()){
                isSOmeoneAFK = true;
                break;
            }
        }
        return isSOmeoneAFK;
    }

    /**add a player to the player list
     * @param player
     * */
    public void addPlayer(Player player) {
        this.players.add(player);
        if(ViewControllerEventHandlerContext.socketVV!=null && ViewControllerEventHandlerContext.RMIVV!=null) {
            player.addObserver(ViewControllerEventHandlerContext.socketVV);
            player.addObserver(ViewControllerEventHandlerContext.RMIVV);
        }
        setChanged();
        notifyObservers(new ModelViewEvent(this.buildPlayersListV(), ModelViewEventTypes.newPlayersList));
    }

    /**@param nickname player nickname
     * @return the desired player from the playersList
     * */
    public Player getPlayer(String nickname) {
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).getNickname().equals(nickname)){
                return this.players.get(i);
            }
        }
        /*Player with that nickname doesn't exist*/
        return null; // ?
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public List<Player> getPlayersOnBoard(){
        ArrayList<Player> playersOnBoard = new ArrayList<>();
        for (Player p: this.players) {
            if(p.getPosition() != null){
                playersOnBoard.add(p);
            }
        }
        return playersOnBoard;
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