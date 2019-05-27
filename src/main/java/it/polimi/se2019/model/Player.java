package it.polimi.se2019.model;

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
    private ObjectOutputStream oos;

    private RMIInterface rmiInterface;

    private int rmiIdentifier;

    private int beforeOrAfterStartingPlayer;

    private boolean isLastPlayingPlayer=false;

    /*-********************************************************************************************************METHODS*/

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
        setChanged();
        notifyObservers();
    }

    public ObjectOutputStream getOos(){
        return this.oos;
    }

    public void setIP(String IP){
        this.IP = IP;
        setChanged();
        notifyObservers();
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

}