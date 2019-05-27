package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;
import java.util.Observable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The PlayerBoard class represents a player's board and keeps track of the current ammunitions, the current damage
 * taken and the number of marks and deaths.
 * @author FedericoMainettiGambera
 * */
public class PlayerBoard implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * Initialize the AmmoBox with "GameConstant.NumberOfStartingAmmos" ammos for each color.
     * The damagesTracker and marksTracker are two empty ArrayList.
     * The deathCounter is set to zero.
     * */
    public PlayerBoard() {
        this.ammoBox = new AmmoList();

        this.damagesTracker = new DamagesTracker();
        this.marksTracker = new MarksTracker();

        this.deathCounter = 0;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**ammo box*/
    private AmmoList ammoBox;

    /**damages tracker*/
    private DamagesTracker damagesTracker;

    /**marks tracker*/
    private MarksTracker marksTracker;

    /**number of times the player has died*/
    private int deathCounter;



    /*-********************************************************************************************************METHODS*/
    /*Do not to use this methods directly. Instead use methods from the "Person" class.*/

    /*DEATH COUNTER*/
    /**increment the deathCounter by one*/
    public void addDeath() {
        deathCounter++;
    }

    /**@return number of times the player has died
     * */
    public int getDeathCounter() {
        return deathCounter;
    }

    public void resetDeathCounter(){
        this.deathCounter = 0;
    }

    /*AMMO BOX*/
    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public AmmoList getAmmoBox() {
        return ammoBox;
    }

    public void addAmmoList(AmmoList ammoList){
        this.getAmmoBox().addAmmoList(ammoList);
    }

    /**add ammo cubes
     * @param color
     * @param quantity
     * */
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.ammoBox.addAmmoCubesOfColor(color, quantity);
    }
    /**add ammo cubes
     * @param ammoList
     * */
    public void addAmmoCubes(AmmoList ammoList) {
        for(int i = 0; i < ammoList.getAmmoCubesList().size(); i++){
            this.ammoBox.addAmmoCubesOfColor( ammoList.getAmmoCubesList().get(i).getColor(),
                                              ammoList.getAmmoCubesList().get(i).getQuantity() );
        }
    }

    /**pay ammo cubes
     * @param quantity
     * @param color
     * @return */
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        if(this.ammoBox.payAmmoCubes(color, quantity)){
            return true;
        }
        else {
            return false;
        }
    }
    /**pay ammo cubes
     * @param cost
     * @return */
    public boolean payAmmoCubes(AmmoList cost){
        if(this.ammoBox.payAmmoCubes(cost)){
            return true;
        }
        else{
            return false;
        }
    }

    /**checks if it is possible to pay an amount of ammo
     * @param color
     * @param quantity
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        return this.ammoBox.canPayAmmoCubes(color,quantity);
    }
    /**checks if it is possible to pay an amount of ammo
     * @param cost
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoList cost){
        return this.ammoBox.canPayAmmoCubes(cost);
    }

    /*DAMAGES TRACKER*/
    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public DamagesTracker getDamagesTracker(){
        return this.damagesTracker;
    }

    /**return a specific slot from the DamageSlot
     * @param slotNumber
     * @return null if the specified slotNumber doesn't exists.
     * @return the specified slot.
     * */
    public DamageSlot getDamagesSlot(int slotNumber) {
        return damagesTracker.getDamageSlot(slotNumber);
    }

    /**add damages
     * @param shootingPlayer
     * @param numberOfDamages
     * */
    public void addDamages (Player shootingPlayer, int numberOfDamages){
        this.damagesTracker.addDamages(shootingPlayer,numberOfDamages);
    }

    /**empty the damages Tracker*/
    public void emptyDamagesTracker(){
        this.damagesTracker.emptyList();
    }

    /*MARKS TRACKER*/
    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public MarksTracker getMarksTracker() {
        return marksTracker;
    }

    /**add Marks
     * @param quantity
     * @param markingPlayer
     * */
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.marksTracker.addMarksFrom(markingPlayer,quantity);
    }

    /**@param markingPlayer
     * @return the number of marks received from the specified player*/
    public int getMarksFrom(Player markingPlayer){
        return this.marksTracker.getNumberOfMarksSlotFrom(markingPlayer);
    }

    /**delete all the marks received from the markingPlayer
     * @param markingPlayer
     * */
    public void deleteMarksFromPlayer(Player markingPlayer){
        this.marksTracker.deleteMarksFromPlayer(markingPlayer);
    }

    public void setAmmoBox(AmmoList ammoBox){
        this.ammoBox = ammoBox;
    }

    public void setDamagesTracker(DamagesTracker damagesTracker){
        this.damagesTracker = damagesTracker;
    }

    public void setMarksTracker (MarksTracker marksTracker){
        this.marksTracker = marksTracker;
    }
}