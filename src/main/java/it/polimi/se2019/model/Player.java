package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.virtualView.RMI.RMIInterface;
import it.polimi.se2019.virtualView.RMIREDO.RmiInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/***/
public class Player extends Person implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
    public Player(boolean thisIsBot){
        this.isBot=thisIsBot;
        this.nickname="Terminator";
        this.isAFK=true;
        //initially set to true so the first player will not use it, but from the second it will be reset to false.
        this.botUsed=true;
        this.hand = new PlayerHand();

    }


    /***/
    public Player() {
        super();
        this.playerHistory = new PlayerHistory(this);
        this.hand = new PlayerHand();
        this.isAFK = false;
        this.isBot=false;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private transient PlayerHand hand;

    public PlayerHistory getPlayerHistory() {
        return playerHistory;
    }

    public void setPlayerHistory(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
    }

    /***/
    public transient PlayerHistory playerHistory;

    /***/
    private String IP;
    /** indicates whether the bot has already been used during a turn*/
    private boolean botUsed;

    /***/
    private transient ObjectOutputStream oos;

    private transient RmiInterface rmiInterface;

    private int rmiIdentifier;

    private int beforeOrAfterStartingPlayer;

    private boolean isLastPlayingPlayer=false;

    private boolean isAFK;

    public boolean isAFK() {
        return isAFK;
    }

    private boolean isBot;

    private int turnID = 0;

    public int getTurnID() {
        return turnID;
    }

    public void incrementTurnID(){
        this.turnID++;
    }

    public void setAFKWithNotify(boolean isAFK){
        //notify everybody, even the one just set AFK
        regulateNumberOfConnection(isAFK);
        this.isAFK = isAFK;
        setChanged();
        ModelViewEvent MVE = new ModelViewEvent(this.isAFK, ModelViewEventTypes.setAFK, nickname);
        //because the player has just been set AFK, he can't be reached with the notify observers, so we force to send him the afk message, so he disconnects
        try {
            if(oos!=null)
            { this.oos.writeObject(MVE);}

           else  this.getRmiInterface().send(MVE);
        } catch (IOException e) {
            System.err.println("Couldn't reach the player to tell him he is AFK. From method Player.setAFKWithNotify(...)");
        }
        //this notify every other player.
        if(!this.isAFK) {
            notifyObservers(MVE);
        }
        if(ModelGate.model.getPlayerList().isMinimumPlayerNotAFK()){
            System.out.println("<SERVER> too many AFK players. Game is Corrupted. From method Player.setAFKWithNotify(...)");
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public void setAFKWIthoutNotify(boolean isAFK){
        //notify everybody except the one just setted AFK
        regulateNumberOfConnection(isAFK);
        this.isAFK = isAFK;
        setChanged();
        ModelViewEvent MVE = new ModelViewEvent(this.isAFK, ModelViewEventTypes.setAFK, nickname);
        //this notify every other player.
        if(this.isAFK == false) {
            notifyObservers(MVE);
        }
        if(ModelGate.model.getPlayerList().isMinimumPlayerNotAFK()){
            System.out.println("<SERVER> too many AFK players. Game is Corrupted. From method Player.setAFKWithoutNotify(...)");
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public void regulateNumberOfConnection(boolean lostConnection){
        if(lostConnection){
            ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()-1);
        }
        else{
            ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()+1);
        }
    }



    /*-********************************************************************************************************METHODS*/

    @Override
    public void setNickname(String nickname){
        setChanged();
        notifyObservers(new ModelViewEvent(nickname, ModelViewEventTypes.newNickname, this.nickname));
        this.nickname = nickname;
        this.hand.setNickname(nickname);
    }

    public void setBeforeorafterStartingPlayer(int position){
        this.beforeOrAfterStartingPlayer=position;
    }

    public int getBeforeorafterStartingPlayer(){
        return this.beforeOrAfterStartingPlayer;
    }

    public void setLastPlayingPlayer(){
        this.isLastPlayingPlayer=true;

    }

    public boolean getLastPlayingPlayer() {
        return isLastPlayingPlayer;
    }

    ////rmi
    public void setRmiInterface(RmiInterface rmi){
        this.rmiInterface=rmi;
    }

    public RmiInterface getRmiInterface(){
        return this.rmiInterface;
    }

    public void setRmiIdentifier(int identifier){
        this.rmiIdentifier=identifier;
    }

    public int getRmiIdentifier(){
        return rmiIdentifier;
    }

    ///socket
    public void setOos(ObjectOutputStream oos){
        this.oos = oos;
        this.addObserver(ViewControllerEventHandlerContext.RMIVV);
        this.addObserver((ViewControllerEventHandlerContext.socketVV));
    }

    public ObjectOutputStream getOos(){
        return this.oos;
    }

    public void setIP(String IP){
        this.IP = IP;
    }

    public String getIp(){
        return this.IP;
    }

    /***/
    public PlayerHand getHand() {
        return this.hand;
    }

    /***/
    public OrderedCardList<WeaponCard> getWeaponCardsInHand() {
        return this.hand.getWeaponCards();
    }

    /***/
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

    public void setBotUsed(boolean botUsed) {
        this.botUsed = botUsed;
    }

    public boolean isBotUsed() {
        return botUsed;
    }

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