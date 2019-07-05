package it.polimi.se2019.model;

import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PlayersListV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**keeps track of the Players playing the current game
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
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

    /**reference to the current playing player*/
    private Player currentPlayingPlayer;
    /**reference to the starting player*/
    private Player startingPlayer;

    /*-********************************************************************************************************METHODS*/
    /**@return currentPlayingPlayer*/
    public Player getCurrentPlayingPlayer() {
        return this.currentPlayingPlayer;
    }
    /**@param currentPlayingPlayer to set currentPlayingPlayer attribute*/
    public void setCurrentPlayingPlayer(Player currentPlayingPlayer){
        currentPlayingPlayer.incrementTurnID();
        this.currentPlayingPlayer = currentPlayingPlayer;
        setChanged();
        notifyObservers(new ModelViewEvent(this.currentPlayingPlayer.getNickname(), ModelViewEventTypes.setCurrentPlayingPlayer));
    }
    /**@return the startingPlayer*/
    public Player getStartingPlayer(){
        return this.startingPlayer;
    }
    /**@param startingPlayer to set startingPlayer attribute*/
    public void setStartingPlayer(Player startingPlayer){
        this.startingPlayer = startingPlayer;
        setChanged();
        notifyObservers(new ModelViewEvent(this.startingPlayer.getNickname(), ModelViewEventTypes.setStartingPlayer));
    }
    /**@param p a list of players to be added to the playerList*/
    public void setAll(List<Object> p) {
        this.players = new ArrayList<>();
        for(Object x: p)
            players.add((Player) x);
    }

    /**set the next playing player*/
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
        if(this.getCurrentPlayingPlayer().isAFK()||this.getCurrentPlayingPlayer().isBot()){
            this.setNextPlayingPlayer();
        }

    }

    /**@return a boolean value that indicates if there is a minimum number of player that are not AFK in order to keep
     * playing the game*/
    public boolean isMinimumPlayerNotAFK(){
        int numberOfPlayerNotAFK = 0;
        for (Player p: this.players) {
            if(!p.isAFK()){
                numberOfPlayerNotAFK++;
            }
        }
        return numberOfPlayerNotAFK < GameConstant.MIN_NUMBER_OF_PLAYER_PER_GAME;
    }

    /**checks if all of the players are AFK
     * @return boolean value*/
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

    /**checks if there is some player AFK,
     * if there is at least one
     * @return true
     * else falseM*/
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
     * @param player the player to add
     * */
    public void addPlayer(Player player) {
        this.players.add(player);
        if(ViewControllerEventHandlerContext.getSocketVV()!=null && ViewControllerEventHandlerContext.getRmiVirtualView()!=null) {
            player.addObserver(ViewControllerEventHandlerContext.getSocketVV());
            player.addObserver(ViewControllerEventHandlerContext.getRmiVirtualView());
            player.getPowerUpCardsInHand().addObserver(ViewControllerEventHandlerContext.getSocketVV());
            player.getPowerUpCardsInHand().addObserver(ViewControllerEventHandlerContext.getRmiVirtualView());
            player.getWeaponCardsInHand().addObserver(ViewControllerEventHandlerContext.getSocketVV());
            player.getWeaponCardsInHand().addObserver(ViewControllerEventHandlerContext.getRmiVirtualView());
        }
        setChanged();
        notifyObservers(new ModelViewEvent(this.buildPlayersListV(), ModelViewEventTypes.newPlayersList));
    }

    /**@param nickname player nickname
     * @return the desired player from the playersList
     * */
    public Player getPlayer(String nickname) {
        for (Player player : this.players) {
            if (player.getNickname().equals(nickname)) {
                return player;
            }
        }
        /*Player with that nickname doesn't exist*/
        return null; // ?
    }

    /**@return players*/
    public List<Player> getPlayers(){
        return this.players;
    }

    /**@return the players that are on the board already */
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


    /**build the equivalent structure for view purposes
     * @return a reference to said structure*/
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