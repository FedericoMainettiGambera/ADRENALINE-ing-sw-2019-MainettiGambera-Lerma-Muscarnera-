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
 * @author LudoLerma
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
        this.temporaryPosition=null;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**nickname*/
    protected String nickname;

    /**indicates if board is in the finalFrenzy mode or not*/
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

    /**@param color
     * the color to be set*/
    public void setColor(PlayersColors color) {
        this.color = color;
        setChanged();
        notifyObservers(new ModelViewEvent(color, ModelViewEventTypes.newColor, nickname));
    }

    /**@param nickname to set nickname attribute*/
    public void setNickname(String nickname){
        setChanged();
        notifyObservers(new ModelViewEvent(nickname, ModelViewEventTypes.newNickname, this.nickname));
        this.nickname = nickname;
    }

    /*POSITION*/
    /**sets person position
     * @param x the x coordinate on map
     * @param y the y coordinate on map
     * */
    public void setPosition(int x, int y){
        try{
            this.position = new Position(x,y);
            this.temporaryPosition = new Position(x,y);
            setChanged();
            notifyObservers(new ModelViewEvent(position, ModelViewEventTypes.newPosition, this.nickname));
        }
        catch (IllegalArgumentException e){
            System.out.println(e.toString());
        }
    }

    /**@param position to set position and temporaryPosition attributes
     * with notify to the observers*/
    public void setPosition(Position position){
        this.position = position;
        this.temporaryPosition = position;
        setChanged();
        notifyObservers(new ModelViewEvent(position, ModelViewEventTypes.newPosition, this.nickname));
    }

    /**@param pos to set position attribute
     * without notify to the observers*/
    public void setPositionWithoutNotify(Position pos){
        this.position = pos;
    }

    /**@return person's position+
     * */
    public Position getPosition() {
        return position;
    }

    /*POINTS*/
    /**@param points to be added*/
    public void addPoints(int points) {
        this.score+=points;
        System.out.println("<SERVER-model> player " + this.getNickname() + " has " + this.score + " points");
        setChanged();
        notifyObservers(new ModelViewEvent(score, ModelViewEventTypes.newScore, nickname));
    }


    /**@return temporaryPosition*/
    public Position getTemporaryPosition() {
        return temporaryPosition;
    }
    /**@param  temporaryPosition to set temporaryPosition attribute*/
    public void setTemporaryPosition(Position temporaryPosition) {
        this.temporaryPosition = temporaryPosition;
    }

    /**a temporary position of the player*/
    private Position temporaryPosition;

    /**@return score*/
    public int getScore(){
        return score;
    }

    /*NICKNAME*/
    /**@return nickname*/
    public String getNickname(){
      return this.nickname;
    }

    /*COLOR*/
    /**@return color*/
    public PlayersColors getColor(){
        return color;
    }

    /*PLAYER BOARD*/
    /**returns the player board.
     * This method should be used as less as possible, look for the right method instead.
     * @return player board
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
     * @return int
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
            StringBuilder s = new StringBuilder("               ");
            for (Integer i:pointsList) {
                s.append(i).append("   ");
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
            StringBuilder s = new StringBuilder("               ");
            for (Integer i:pointsList) {
                s.append(i).append("   ");
            }
            System.out.println(s);
            return pointsList;
        }
    }

    /**set the hasFinalFrenzyBoard attribute true,
     * reset the death counter
     * notify observers*/
    public void makePlayerBoardFinalFrenzy(){
        this.hasFinalFrenzyBoard = true;
        this.board.resetDeathCounter();
        setChanged();
        notifyObservers(new ModelViewEvent(this.hasFinalFrenzyBoard, ModelViewEventTypes.setFinalFrenzyBoard, nickname));
    }

    /**@return hasFinalFrenzyBoard*/
    public boolean isHasFinalFrenzyBoard() {
        return hasFinalFrenzyBoard;
    }

    /**@return a list of player in order of rank*/
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
                for (PlayerScore playerScore : list) {
                    if (playerScore.player == player) {
                        return true;
                    }
                }
                return false;
            }

            /**swap to elements of a list
             * @param i index of the first element
             * @param j index of the second element
             * @param list the list*/
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
        StringBuilder s = new StringBuilder("               ");
        for (Player p : finalResult) {
            s.append(p.getNickname()).append("   ");
        }
        System.out.println(s);

        return finalResult;
    }
    /**@return the damage slot of
     * @param i index i*/
    public DamageSlot getDamageSlot(int i){
        return this.board.getDamagesSlot(i);
    }
    /**@return the last damage slot*/
    public DamageSlot getLastDamageSlot(){
        return this.board.getDamagesTracker().getLastDamageSlot();
    }
    /**@return board*/
    public PlayerBoard getBoard(){
        return this.board;
    }



    /**adding ammo to the player ammo box
     * @param color color of the ammos
     * @param quantity amount of ammos
     * */
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.board.addAmmoCubes(color, quantity);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
    }

    /**adding ammo to the player ammo box
     * @param ammoList from which the ammos are taken to be add
     */
    public void addAmmoCubes(AmmoList ammoList){
        this.board.addAmmoCubes(ammoList);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
    }

    /**subtract a specific amount of ammos.
     * Before doing any operation checks if it is possible to subtract the specified amount.
     * @param color the color of the ammos
     * @param quantity the quantity to be paid
     * */
    public void payAmmoCubes(AmmoCubesColor color, int quantity){
        AmmoList ammoList = new AmmoList();
        AmmoCubes ammoCubes = new AmmoCubes(color);
        ammoCubes.setQuantity(quantity);
        ammoList.getAmmoCubesList().add(ammoCubes);
        payAmmoCubes(ammoList);
    }
    /**subtract a specific amount of ammos
     * Before doing any operation checks if it is possible to subtract the specified amount.
     * @param cost amount
     * */
    public void payAmmoCubes(AmmoList cost){
        for (AmmoCubes ammoToPay: cost.getAmmoCubesList()) {
            for (AmmoCubes mineAmmos:this.getPlayerBoard().getAmmoBox().getAmmoCubesList()) {
                if(mineAmmos.getColor().equals(ammoToPay.getColor())){
                    mineAmmos.setQuantity(mineAmmos.getQuantity() - ammoToPay.getQuantity());
                }
            }
        }
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getAmmoBox().buildAmmoListV(), ModelViewEventTypes.newAmmoBox, nickname));
    }
    /**checks if it is possible to subtract a specific amount of ammos
     * @param color color of the ammos
     * @param quantity of ammos
     * @return boolean value
     * */
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        boolean canPay= this.board.canPayAmmoCubes(color,quantity);
        return canPay;
    }

    /**checks if it is possible to subtract a specific amount of ammos
     * @param cost the amount
     * @return boolean value
     * */
    public boolean canPayAmmoCubes(AmmoList cost){
        return this.board.canPayAmmoCubes(cost);
    }

    /** add a specific amount of blood drops (damages) of the shootingPlayer to the (this) player board.
     * if adding damages overkills the this player, it marks the shootingPlayer.
     * @param numberOfDamages the specific amount
     * @param shootingPlayer the shooting player
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
     * @return boolean value*/
    public boolean isDead(){
        return this.board.getDamagesTracker().getDamageSlotsList().size() > 10;
    }

    /**checks if the player has received at least 12 damages (is overKilled)
     * @return boolean value*/
    public boolean isOverkilled(){
        return this.board.getDamagesTracker().getDamageSlotsList().size() > 11;
    }

    /**checks if the player has received at least 3 damages
     * @return boolean value*/
    public boolean hasAdrenalineGrabAction(){
        return this.board.getDamagesTracker().getDamageSlotsList().size() > 2;
    }

    /**checks if the player has received at least 6
     * @return boolean value*/
    public boolean hasAdrenalineShootAction(){
        return this.board.getDamagesTracker().getDamageSlotsList().size() > 5;
    }

    /**add a specific amount of marks from the markingPlayer to (this) player board
     * @param markingPlayer  the marking player
     * @param quantity amount to be added
     * */
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.board.addMarksFrom(markingPlayer,quantity);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getMarksTracker().buildMarksTrackerV(), ModelViewEventTypes.newMarksTracker, nickname));
    }

    /**
     * @param markingPlayer the marking player to return marks of
     * @return the number of marks the player has received from the markingPlayer
     * */
    public int getMarksFrom(Player markingPlayer){
        return this.board.getMarksFrom(markingPlayer);
    }

    /**delete all the marks received from the markingPlayer
     * @param markingPlayer the player to delete the marks from
     * */
    public void deleteMarksFromPlayer(Player markingPlayer){
        this.board.deleteMarksFromPlayer(markingPlayer);
    }

    /**takes all the marks from a player and transforms them in damages
     * @param shootingPlayer
     * @deprecated
     * */
    public void applyMarksFromPlayer(Player shootingPlayer){
        addDamages(shootingPlayer, this.getMarksFrom(shootingPlayer));
        deleteMarksFromPlayer(shootingPlayer);
        setChanged();
        notifyObservers(new ModelViewEvent(this.board.getMarksTracker().buildMarksTrackerV(), ModelViewEventTypes.newMarksTracker, nickname));
    }
}
