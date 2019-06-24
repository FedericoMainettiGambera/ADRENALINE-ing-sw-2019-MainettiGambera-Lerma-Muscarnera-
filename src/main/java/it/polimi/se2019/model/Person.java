package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.enumerations.PlayersColors;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**Abstract class that represents a character.
 * There are all the methods needed to do any action required during the game.
 * @author FedericoMainettiGambera
 * */
public abstract class Person extends Observable implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor
     * */
    public Person(){
        this.hasFinalFrenzyBoard = false;
        this.board = new PlayerBoard();
        this.score=0;
        this.position=null;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**nickname*/
    protected String nickname;

    private boolean hasFinalFrenzyBoard;

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
    private transient PlayerBoard board;

    /*-********************************************************************************************************METHODS*/

    public void setColor(PlayersColors color) {
        this.color = color;
        setChanged();
        notifyObservers(new ModelViewEvent(color, ModelViewEventTypes.newColor, nickname));
    }

    public void setNickname(String nickname){
        setChanged();
        notifyObservers(new ModelViewEvent(nickname, ModelViewEventTypes.newNickname, this.nickname));
        this.nickname = nickname;
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
            notifyObservers(new ModelViewEvent(position, ModelViewEventTypes.newPosition, this.nickname));
        }
        catch (IllegalArgumentException e){
            System.out.println(e.toString());
        }
    }

    public void setPosition(Position position){
        this.position = position;
        setChanged();
        notifyObservers(new ModelViewEvent(position, ModelViewEventTypes.newPosition, this.nickname));
    }

    /**@return person's position+
     * */
    public Position getPosition() {
        return position;
    }

    /*POINTS*/
    /***/
    public void addPoints(int points) {
        this.score+=points;
        System.out.println("<SERVER-model> player " + this.getNickname() + " has " + this.score + " points");
        setChanged();
        notifyObservers(new ModelViewEvent(score, ModelViewEventTypes.newScore, nickname));
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
     * This method should be used as less as possible, look for the right method instead.
     * @return
     * */
    public PlayerBoard getPlayerBoard() { return board; }

    /**increment the death counter by one*/
    public void addDeath() {
        this.board.addDeath();
        setChanged();
        notifyObservers(new ModelViewEvent(this.getDeathCounter(), ModelViewEventTypes.addDeathCounter, nickname));
    }

    public void setScore(int score){
        this.score = score;
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
    public ArrayList<Integer> getPointsList(){
        if(this.hasFinalFrenzyBoard){
            ArrayList<Integer> pointsList = new ArrayList<>();
            if(this.getDeathCounter() > 0){
                for (int i = 0; i < 20; i++) {
                    pointsList.add(1);
                }
            }
            else{
                pointsList.add(2);
                for (int i = 0; i < 19; i++) {
                    pointsList.add(1);
                }
            }
            System.out.println("<SERVER-model> PointsList:");
            String s = "               ";
            for (Integer i:pointsList) {
                s+= i + "   ";
            }
            System.out.println(s);
            return pointsList;
        }
        else {
            ArrayList<Integer> pointsList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                pointsList.add(Math.max((8 - (2 * i) - (this.getDeathCounter() * 2)), 1));
            }
            for (int i = 0; i < 10; i++) {
                pointsList.add(1);
            }
            System.out.println("<SERVER-model> PointsList of player " + this.getNickname());
            String s = "               ";
            for (Integer i:pointsList) {
                s+= i + "   ";
            }
            System.out.println(s);
            return pointsList;
        }
    }

    public void makePlayerBoardFinalFrenzy(){
        this.hasFinalFrenzyBoard = true;
        this.board.resetDeathCounter();
        setChanged();
        notifyObservers(new ModelViewEvent(this.hasFinalFrenzyBoard, ModelViewEventTypes.setFinalFrenzyBoard, nickname));
    }

    public boolean isHasFinalFrenzyBoard() {
        return hasFinalFrenzyBoard;
    }

    public ArrayList<Player> getPlayersDamageRank(){

        class PlayerScore{
            Player player;
            int quantity;

            public PlayerScore(Player player){
                this.player = player;
                this.quantity = 0;
            }
        }

        class ListOfPlayerScore{
            ArrayList<PlayerScore> list = new ArrayList<>();

            public void add(PlayerScore p){
                this.list.add(p);
            }

            public int size(){
                return this.list.size();
            }

            public PlayerScore get(int i){
                return this.list.get(i);
            }

            public boolean contains(Player player){
                for (int i = 0; i < list.size() ; i++) {
                    if(list.get(i).player == player){
                        return true;
                    }
                }
                return false;
            }

            public void swapElements(ArrayList<PlayerScore> list, int i, int j){
                Collections.swap(list, i, j);
            }
        }

        ListOfPlayerScore playersShooting = new ListOfPlayerScore();

        for (int i = 0; i < this.board.getDamagesTracker().getDamageSlotsList().size(); i++) {
            if(!playersShooting.contains(this.board.getDamagesTracker().getDamageSlotsList().get(i).getShootingPlayer())){
                playersShooting.add(new PlayerScore(
                        this.board.getDamagesTracker().getDamageSlotsList().get(i).getShootingPlayer()
                        ));
            }
        }

        for (int i = 0; i < this.board.getDamagesTracker().getDamageSlotsList().size() ; i++) {
            for(int j = 0; j < playersShooting.size(); j++){
                Player tempI = this.board.getDamagesTracker().getDamageSlotsList().get(i).getShootingPlayer();
                Player tempJ = playersShooting.get(j).player;
                if(tempI == tempJ){
                    playersShooting.get(j).quantity++;
                    break;
                }
            }
        }


        for (int i = 0; i < playersShooting.size()-1; i++) {
            for (int j = i+1; j < playersShooting.size(); j++) {
                if(playersShooting.list.get(i).quantity < playersShooting.list.get(j).quantity){
                    playersShooting.swapElements(playersShooting.list, i, j);
                }
            }
        }

        ArrayList<Player> finalResult = new ArrayList<>();

        for(int i = 0; i<playersShooting.size(); i++){
            finalResult.add(playersShooting.list.get(i).player);
        }

        System.out.println("<SERVER-model> list of players ordered by number of damage made to " + this.nickname);
        String s = "               ";
        for (Player p : finalResult) {
            s+= p.getNickname() + "   ";
        }
        System.out.println(s);

        return finalResult;
    }

    public DamageSlot getDamageSlot(int i){
        return this.board.getDamagesSlot(i);
    }

    public DamageSlot getLastDamageSlot(){
        return this.board.getDamagesTracker().getLastDamageSlot();
    }

    public PlayerBoard getBoard(){
        return this.board;
    }



    /**adding ammo to the player ammo box
     * @param color
     * @param quantity
     * */
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.board.addAmmoCubes(color, quantity);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
    }

    /**adding ammo to the player ammo box
     * @param ammoList
     */
    public void addAmmoCubes(AmmoList ammoList){
        this.board.addAmmoCubes(ammoList);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
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
            notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
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
            notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
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
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        boolean canPay= this.board.canPayAmmoCubes(color,quantity);
        if(canPay){
            return true;
        }
        else{
            return false;
        }
    }

    /**checks if it is possible to subtract a specific amount of ammos
     * @param cost
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoList cost){
        boolean canPay = this.board.canPayAmmoCubes(cost);
        if(canPay){
            return true;
        }
        else{
            return false;
        }
    }

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
        notifyObservers(new ModelViewEvent(this.board.getDamagesTracker().buildDamageTrackerV(), ModelViewEventTypes.newDamageTracker, nickname));
    }

    /** takes away all the damages from the player board.
     * (this method will be used when the player dies)*/
    public void emptyDamagesTracker(){
        this.board.emptyDamagesTracker();
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getDamagesTracker().buildDamageTrackerV(), ModelViewEventTypes.newDamageTracker, nickname));
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
        notifyObservers(new ModelViewEvent(this.board.getMarksTracker().buildMarksTrackerV(), ModelViewEventTypes.newMarksTracker, nickname));
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
    }

    /**takes all the marks from a player and tranform them in dadmages
     * @param shootingPlayer
     * */
    public void applyMarksFromPlayer(Player shootingPlayer){
        addDamages(shootingPlayer, this.getMarksFrom(shootingPlayer));
        deleteMarksFromPlayer(shootingPlayer);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getMarksTracker().buildMarksTrackerV(), ModelViewEventTypes.newMarksTracker, nickname));
    }
}
