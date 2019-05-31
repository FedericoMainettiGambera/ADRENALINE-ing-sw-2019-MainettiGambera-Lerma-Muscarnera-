package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.PowerUpCardV;
import it.polimi.se2019.view.components.WeaponCardV;
import it.polimi.se2019.virtualView.RMI.RMIInterface;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/***/
public class Player extends Person implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
    public Player() {
        super();
        this.hand = new PlayerHand();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private PlayerHand hand;

    /***/
    private String IP;

    /***/
    private transient ObjectOutputStream oos;

    private transient RMIInterface rmiInterface;

    private int rmiIdentifier;

    private int beforeOrAfterStartingPlayer;

    private boolean isLastPlayingPlayer=false;

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
    public void setRmiInterface(RMIInterface rmi){
        this.rmiInterface=rmi;
    }

    public RMIInterface getRmiInterface(){
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