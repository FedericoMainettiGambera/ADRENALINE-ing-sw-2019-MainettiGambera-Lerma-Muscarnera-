package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.virtualView.RMIREDO.RmiInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**represent a player */
public class Player extends Person implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**construct the player with
     * @param thisIsBot to know if they are the terminator or not
     * used just in case the player is the bot*/
    public Player(boolean thisIsBot){
        this.isBot=thisIsBot;
        this.nickname="Terminator";
        this.isAFK=true;
        //initially set to true so the first player will not use it, but from the second it will be reset to false.
        this.botUsed=true;
        this.hand = new PlayerHand();

    }


    /**construct the player with no parameters*/
    public Player() {
        super();
        this.playerHistory = new PlayerHistory(this);
        this.hand = new PlayerHand();
        this.isAFK = false;
        this.isBot=false;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**represents the cards of the player*/
    private transient PlayerHand hand;

    public PlayerHistory getPlayerHistory() {
        return playerHistory;
    }

    public void setPlayerHistory(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
    }

    /***/
    public transient PlayerHistory playerHistory;

    /**represents the IP of the player*/
    private String ip;
    /** indicates whether the bot has already been used during a turn*/
    private boolean botUsed;

    /**object outputstream of the socket*/
    private transient ObjectOutputStream oos;
    /**the interface where to communicate with the client*/
    private transient RmiInterface rmiInterface;

    /***/
    private int rmiIdentifier;

    /**indicates if the player turn comes before of after the one of the starting player, used for FF*/
    private int beforeOrAfterStartingPlayer;
    /**indicates if the player is the last player to play the last turn, used for FF*/
    private boolean isLastPlayingPlayer=false;
    /**indicates if the player is AFK or not*/
    private boolean isAFK;
    /**each player is represented from a color*/
    private PlayersColors color;





    /**tells you if the player is AFK or not
     * @return isAFK*/
    public boolean isAFK() {
        return isAFK;
    }
    /**indicates if the player is the Terminator or not*/
    private boolean isBot;

    private int turnID = 0;

    public int getTurnID() {
        return turnID;
    }

    public void incrementTurnID(){
        this.turnID++;
    }
    /**set the player AFK and communicates it to them
     * @param isAFK boolean*/
    public void setAFKWithNotify(boolean isAFK){
        //notify everybody, even the one just set AFK
        regulateNumberOfConnection(isAFK);
        this.isAFK = isAFK;
        setChanged();
        ModelViewEvent modelViewEvent = new ModelViewEvent(this.isAFK, ModelViewEventTypes.setAFK, nickname);
        //because the player has just been set AFK, he can't be reached with the notify observers, so we force to send him the afk message, so he disconnects
        try {
            if(oos!=null)
            { this.oos.writeObject(modelViewEvent);}

           if(this.getRmiInterface()!=null) {
               this.getRmiInterface().send(modelViewEvent);
           }

        } catch (IOException e) {
            System.err.println("Couldn't reach the player to tell him he is AFK. From method Player.setAFKWithNotify(...)");
        }
        //this notify every other player.
        if(!this.isAFK) {
            notifyObservers(modelViewEvent);
        }
        if(ModelGate.model.getPlayerList().isMinimumPlayerNotAFK()){
            System.out.println("<SERVER> too many AFK players. Game is Corrupted. From method Player.setAFKWithNotify(...)");
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    /**set the player AFK without communicating it to them
     * @param isAFK boolean*/
    public void setAFKWIthoutNotify(boolean isAFK){
        //notify everybody except the one just setted AFK
        regulateNumberOfConnection(isAFK);
        this.isAFK = isAFK;
        setChanged();
        ModelViewEvent modelViewEvent = new ModelViewEvent(this.isAFK, ModelViewEventTypes.setAFK, nickname);
        //this notify every other player.
        if(!this.isAFK) {
            notifyObservers(modelViewEvent);
        }
        if(ModelGate.model.getPlayerList().isMinimumPlayerNotAFK()){
            System.out.println("<SERVER> too many AFK players. Game is Corrupted. From method Player.setAFKWithoutNotify(...)");
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    /**set the new number of connection
     * @param lostConnection boolean*/
    public void regulateNumberOfConnection(boolean lostConnection){
        if(lostConnection){
            ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()-1);
        }
        else{
            ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()+1);
        }
    }



    /*-********************************************************************************************************METHODS*/

    /**set the player Nickname and communicates it to everyone
     * @param nickname string*/
    @Override
    public void setNickname(String nickname){
        setChanged();
        notifyObservers(new ModelViewEvent(nickname, ModelViewEventTypes.newNickname, this.nickname));
        this.nickname = nickname;
        this.hand.setNickname(nickname);
    }

    /**set the player position which is an int that depends on whether they are before of after the starting player
     * @param position negative, positive or 0*/
    public void setBeforeorafterStartingPlayer(int position){
        this.beforeOrAfterStartingPlayer=position;
    }

    /**get the player position which is an int that depends on whether they are before of after the starting player
     * @return int*/
    public int getBeforeorafterStartingPlayer(){
        return this.beforeOrAfterStartingPlayer;
    }

    /**set the player as the last playing player*/
    public void setLastPlayingPlayer(){
        this.isLastPlayingPlayer=true;

    }
    /**return the player who's the last playing player*/
    public boolean getLastPlayingPlayer() {
        return isLastPlayingPlayer;
    }

    /**set the player rmi interface
     * @param rmi is said interface*/
    public void setRmiInterface(RmiInterface rmi){
        this.rmiInterface=rmi;
    }

    /**getter for the player rmi interface
     * @return  rmiinterface*/
    public RmiInterface getRmiInterface(){
        return this.rmiInterface;
    }

    public void setRmiIdentifier(int identifier){
        this.rmiIdentifier=identifier;
    }
    /**set the player
     * @param oos ObjectOutputStream for socket connection*/
    ///socket
    public void setOos(ObjectOutputStream oos){
        this.oos = oos;
        this.addObserver(ViewControllerEventHandlerContext.RMIVV);
        this.addObserver((ViewControllerEventHandlerContext.socketVV));
    }
    /**getter for the player oos
     * @return oos */
    public ObjectOutputStream getOos(){
        return this.oos;
    }

    /**set the player IP
     * @param ip said ip address*/
    public void setIP(String ip){
        this.ip = ip;
    }
    /**getter for the player's IP
     * @return said ip address*/
    public String getIp(){
        return this.ip;
    }

    /**getter for the player's hand
     * @return the hand of the player*/
    public PlayerHand getHand() {
        return this.hand;
    }

    /**getter for the player's weapon cards in hand
     * @return said weapon cards*/
    public OrderedCardList<WeaponCard> getWeaponCardsInHand() {
        return this.hand.getWeaponCards();
    }

    /**getter for the player's power up  cards in hand
     * @return said power up cards*/
    public OrderedCardList<PowerUpCard> getPowerUpCardsInHand() {
        return this.hand.getPowerUpCards();
    }

    /** needed to set whether the player is the terminator or not
     * @param bot boolean value*/
    public void setBot(boolean bot) {
        isBot = bot;
    }
   /**needed to know whether the player is the terminator or not
    * @return true or false
    * */
    public boolean isBot(){
        return isBot;
    }
    /**setter for the player's parameter that indicates if the bot has already been used or not
     * @param botUsed boolean*/
    public void setBotUsed(boolean botUsed) {
        this.botUsed = botUsed;
    }
    /**getter for the player's parameter that indicates if the bot has already been used or not
     * @return said parameter*/
    public boolean isBotUsed() {
        return botUsed;
    }
    /**build the equivalent structure for view usage*/
    public PlayerV buildPlayerV(){
        PlayerV playerV = new PlayerV();
        playerV.setMarksTracker(this.getBoard().getMarksTracker().buildMarksTrackerV());
        playerV.setDamageTracker(this.getBoard().getDamagesTracker().buildDamageTrackerV());
        playerV.setAmmoBox(this.getBoard().getAmmoBox().buildAmmoListV());
        playerV.setHasFinalFrenzyBoard(this.isHasFinalFrenzyBoard());
        playerV.setNumberOfDeaths(this.getBoard().getDeathCounter());
        playerV.setScore(this.getScore());
        if(this.getPosition() != null) {
            playerV.setX(this.getPosition().getX());
            playerV.setY(this.getPosition().getY());
        }
        playerV.setNickname(getNickname());
        playerV.setColor(getColor());
        playerV.setPowerUpCardInHand(this.getPowerUpCardsInHand().buildDeckV());
        playerV.setWeaponCardInHand(this.getWeaponCardsInHand().buildDeckV());

        return playerV;
    }
}