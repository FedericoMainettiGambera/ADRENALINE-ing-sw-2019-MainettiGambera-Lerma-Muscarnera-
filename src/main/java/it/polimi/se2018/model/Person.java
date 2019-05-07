package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;
import it.polimi.se2018.model.enumerations.PlayersColors;

import java.util.Observable;

/**Abstract class that represents a character.
 * There are all the methods needed to do any action required during the game.
 * @author FedericoMainettiGambera
 * */
public abstract class Person extends Observable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public Person(){

        this.board = new PlayerBoard();
        this.score=0;
        this.position=null;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**nickname*/
    private String nickname;

    /**color*/
    private PlayersColors color;

    /**current player score*/
    private int score;

    /**
     * Current player position.
     * Null only if the game hasn't already started
     * */
    private Position position;

    /**player board*/
    private PlayerBoard board;

    /*-********************************************************************************************************METHODS*/

    public void setColor(PlayersColors color) {
        this.color = color;
        setChanged();
        notifyObservers();
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
        setChanged();
        notifyObservers();
    }

    /*POSITION*/
    /**sets person position
     * @param x
     * @param y
     * */
    public void setPosition(int x, int y){
        try{
            this.position = new Position(x,y);
            setChanged();
            notifyObservers();
        }
        catch (IllegalArgumentException e){
            System.out.println(e.toString());
        }
    }

    public void setPosition(Position position){
        this.position = position;
    }

    /**@return person's position
     * @throws IllegalStateException
     * */
    public Position getPosition() throws IllegalStateException {
        if(this.position == null){
            throw new IllegalStateException("Position hasn't been set, it is NULL.");
        }
        return position;
    }

    /*POINTS*/
    /***/
    public void AddPoints(int points) {
        this.score+=points;
        setChanged();
        notifyObservers();
    }

    /***/
    public int getScore(){
        return score;
    }

    /*NICKNAME*/
    /***/
    public String getNickname(){
      return this.nickname;
    }

    /*COLOR*/
    /***/
    public PlayersColors getColor(){
        return color;
    }

    /*PLAYER BOARD*/
    /**returns the player board.
     * This method should be used as less as possible, instead search for the right method.
     * @return
     * */
    public PlayerBoard getPlayerBoard() { return board; }

    /**increment the death counter by one*/
    public void addDeath() {
        this.board.addDeath();
        setChanged();
        notifyObservers();
    }

    /**return the number of times the player has died.
     * @return
     * */
    public int getDeathCounter() {
        return this.board.getDeathCounter();
    }

    /**@return returns an array of integer that represents the amount of points the first (second, third, etc..) player
     * with more hits will get. (es: 8,6,4,2,1,1,1,1,1,1)
     * */
    public int[] getPointsList(){
        int[] pointsList = new int[10];
        for(int i = 0; i < 10; i++){
            pointsList[i]= Math.max( (8-(2*i)-(this.getDeathCounter()*2)) , 1);
        }
        return pointsList;
    }

    /**adding ammo to the player ammo box
     * @param color
     * @param quantity
     * */
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.board.addAmmoCubes(color, quantity);
        setChanged();
        notifyObservers();
    }

    /**adding ammo to the player ammo box
     * @param ammoList
     */
    public void addAmmoCubes(AmmoList ammoList){
        this.board.addAmmoCubes(ammoList);
        setChanged();
        notifyObservers();
    }

    /**subtract a specific amount of ammos.
     * Before doing any operation checks if it is possible to subtract the specified amount.
     * @param color
     * @param quantity
     * @return
     * */
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        if(this.board.payAmmoCubes(color, quantity)) {
            setChanged();
            notifyObservers();
            return true;
        }
        else return false;
    }
    /**subtract a specific amount of ammos
     * Before doing any operation checks if it is possible to subtract the specified amount.
     * @param cost
     * @return
     * */
    public boolean payAmmoCubes(AmmoList cost){
        if(this.board.payAmmoCubes(cost)){
            setChanged();
            notifyObservers();
            return true;
        }
        else{
            return false;
        }
    }
    /**checks if it is possible to subtract a specific amount of ammos
     * @param color
     * @param quantity
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){ return this.board.canPayAmmoCubes(color,quantity); }

    /**checks if it is possible to subtract a specific amount of ammos
     * @param cost
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoList cost){ return this.board.canPayAmmoCubes(cost); }

    /** add a specific amount of blood drops (damages) of the shootingPlayer to the (this) player board.
     * if adding damages overkills the this player, it marks the shootingPlayer.
     * @param numberOfDamages
     * @param shootingPlayer
     * */
    public void addDamages (Player shootingPlayer, int numberOfDamages){
        this.board.addDamages(shootingPlayer,numberOfDamages);
        if(isOverkilled()){
            shootingPlayer.addMarksFrom( (Player)this, 1);
        }
        setChanged();
        notifyObservers();
    }

    /** takes away all the damages from the player board.
     * (this method will be used when the player dies)*/
    public void emptyDamagesTracker(){
        this.board.emptyDamagesTracker();
        setChanged();
        notifyObservers();
    }

    /**checks if the player has received at least 11 damages (is dead)
     * @return */
    public boolean isDead(){
        if(this.board.getDamagesTracker().getDamageSlotsList().size()>10){
            return true;
        }
        return false;
    }

    /**checks if the player has received at least 12 damages (is overKilled)
     * @return */
    public boolean isOverkilled(){
        if(this.board.getDamagesTracker().getDamageSlotsList().size()>11){
            return true;
        }
        return false;
    }

    /**checks if the player has received at least 3 damages
     * @return */
    public boolean hasAdrenalineGrabAction(){
        if(this.board.getDamagesTracker().getDamageSlotsList().size()>2){
            return true;
        }
        return false;
    }

    /**checks if the player has received at least 6
     * @return */
    public boolean hasAdrenalineShootAction(){
        if(this.board.getDamagesTracker().getDamageSlotsList().size()>5){
            return true;
        }
        return false;
    }

    /**add a specific amount of marks from the markingPlayer to (this) player board
     * @param markingPlayer
     * @param quantity
     * */
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.board.addMarksFrom(markingPlayer,quantity);
        setChanged();
        notifyObservers();
    }

    /**return the number of marks the player has received from the markingPlayer
     * @param markingPlayer
     * @return
     * */
    public int getMarksFrom(Player markingPlayer){
        return this.board.getMarksFrom(markingPlayer);
    }

    /**delete all the marks received from the markingPlayer
     * @param markingPlayer
     * */
    public void deleteMarksFromPlayer(Player markingPlayer){
        this.board.deleteMarksFromPlayer(markingPlayer);
        setChanged();
        notifyObservers();
    }

    /**takes all the marks from a player and tranform them in dadmages
     * @param shootingPlayer
     * */
    public void applyMarksFromPlayer(Player shootingPlayer){
        addDamages(shootingPlayer, this.getMarksFrom(shootingPlayer));
        deleteMarksFromPlayer(shootingPlayer);
        setChanged();
        notifyObservers();
    }
}
