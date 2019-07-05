package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;

/**
 * THIS CLASS SHOULD NEVER BE DIRECTLY ACCESSED, INSTEAD USE METHODS FROM THE "Person" CLASS.
 * The PlayerBoard class represents a player's board and keeps track of the current ammunitions, the current damage
 * taken and the number of marks and deaths.
 * @author FedericoMainettiGambera
 * @author LudoLerma
 * */
public class PlayerBoard implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * Initialize the AmmoBox with "GameConstant.NUMBER_OF_STARTING_AMMOS" ammos for each color.
     * The damagesTracker and marksTracker are two empty ArrayList.
     * The deathCounter is set to zero.
     * */
      PlayerBoard() {
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
    void addDeath() {
        deathCounter++;
    }

    /**@return number of times the player has died
     * */
     int getDeathCounter() {
        return deathCounter;
    }

     void resetDeathCounter(){
        this.deathCounter = 0;
    }

    /*AMMO BOX*/
    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return ammoBox
     * */
    public AmmoList getAmmoBox() {
        return ammoBox;
    }


    /**add ammo cubes
     * @param color red, blue or yellow
     * @param quantity number of ammos to add
     * */
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.ammoBox.addAmmoCubesOfColor(color, quantity);
    }
    /**add ammo cubes
     * @param ammoList a list of ammunitions to add
     * */
     public void addAmmoCubes(AmmoList ammoList) {
        for(int i = 0; i < ammoList.getAmmoCubesList().size(); i++){
            this.ammoBox.addAmmoCubesOfColor( ammoList.getAmmoCubesList().get(i).getColor(),
                                              ammoList.getAmmoCubesList().get(i).getQuantity() );
        }
    }

    /**pay ammo cubes
     * @return boolean value*/
     public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        return this.ammoBox.payAmmoCubes(color, quantity);
    }
    /**pay ammo cubes
     * @param cost a list of ammos to be paid
     * @return boolean value */
    public boolean payAmmoCubes(AmmoList cost){
         return this.ammoBox.payAmmoCubes(cost);
    }

    /**checks if it is possible to pay an amount of ammo
     * @param color of the ammos to be paid
     * @param quantity of the ammo to be paid
     * @return boolean value
     * */
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        return this.ammoBox.canPayAmmoCubes(color,quantity);
    }
    /**checks if it is possible to pay an amount of ammo
     * @param cost list of the ammos to be paid
     * @return boolean value
     * */
    public boolean canPayAmmoCubes(AmmoList cost){
        return this.ammoBox.canPayAmmoCubes(cost);
    }

    /*DAMAGES TRACKER*/
    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return the damage tracker
     * */
    public DamagesTracker getDamagesTracker(){
        return this.damagesTracker;
    }

    /**return a specific slot from the DamageSlot
     * @param slotNumber the index of the slot you want access to
     * @return null if the specified slotNumber doesn't exists.
     *the specified slot otherwise.
     * */
    public DamageSlot getDamagesSlot(int slotNumber) {
        return damagesTracker.getDamageSlot(slotNumber);
    }

    /**add damages
     * @param shootingPlayer the player who's shooting
     * @param numberOfDamages the damages to add
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
     * @return the marksTracker
     * */
     MarksTracker getMarksTracker() {
        return marksTracker;
    }

    /**add Marks
     * @param quantity of marks to be added
     * @param markingPlayer the player who's giving the marks
     * */
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.marksTracker.addMarksFrom(markingPlayer,quantity);
    }

    /**@param markingPlayer the player you want to know about the marks he has given
     * @return the number of marks received from the specified player*/
    public int getMarksFrom(Player markingPlayer){
        return this.marksTracker.getNumberOfMarksSlotFrom(markingPlayer);
    }

    /**delete all the marks received from the markingPlayer
     * @param markingPlayer the player you want to delete the marks of
     * */
    public void deleteMarksFromPlayer(Player markingPlayer){
        this.marksTracker.deleteMarksFromPlayer(markingPlayer);
    }

    public void setAmmoBox(AmmoList ammoBox){
        this.ammoBox = ammoBox;
    }


}