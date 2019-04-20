package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;

/**
 * The PlayerBoard class represents the player's board and keeps track of the current ammunitions, the current damage
 * taken and the number of marks.
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
public class PlayerBoard {

    /*CONSTRUCTOR*/

    /**
     * Initialize the the AmmoBox with "GameConstant.NumberOfStartingAmmos" ammos for each color.
     * The damagesTracker and marksTracker are two empty ArrayList.
     * The deathCounter is set to zero.
     * */
    public PlayerBoard() {
        this.ammoBox = new AmmoList();
        for (AmmoCubesColor color: AmmoCubesColor.values() ) {
            addAmmoCubes(color, GameConstant.NumberOfStartingAmmos);
        }

        this.damagesTracker = new DamagesTracker();
        this.marksTracker = new MarksTracker();

        this.deathCounter = 0;
    }



    /*ATTRIBUTES*/

    /***/
    private AmmoList ammoBox;

    /***/
    private DamagesTracker damagesTracker;

    /***/
    private MarksTracker marksTracker;

    /***/
    private int deathCounter;



    /*METHODS*/

                /*DeathCounter*/

    /***/
    public void addDeath() {
        deathCounter++;
    }

    /***/
    public int getDeathCounter() {
        return deathCounter;
    }

                /*AmmoBox*/

    /** USELESS (?) -> probably YES*/
    public AmmoList getAmmoBox() {
        return ammoBox;
    }

    /***/
    public void addAmmoCubes(AmmoCubesColor color, int quantity) {
        this.ammoBox.addAmmoCubesOfColor(color, quantity);
    }

    /***/
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        return this.ammoBox.payAmmoCubes(color, quantity);
    }

    /***/
    public boolean payAmmoCubes(AmmoList cost){
        return this.ammoBox.payAmmoCubes(cost);
    }

    /***/
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        return this.ammoBox.canPayAmmoCubes(color,quantity);
    }

    /***/
    public boolean canPayAmmoCubes(AmmoList cost){
        return this.ammoBox.canPayAmmoCubes(cost);
    }

    /*DamagesTracker*/

    /** USELESS (?) -> probably YES*/
    public DamageSlot getDamagesSlot(int slotNumber) {
        return damagesTracker.getDamageSlot(slotNumber);
    }

    /***/
    public void addDamages (Player shootingPlayer, int numberOfDamages){
        this.damagesTracker.addDamages(shootingPlayer,numberOfDamages);
    }

    /***/
    public void emptyDamagesTracker(){
        this.damagesTracker.emptyList();
    }

                /*MarksTraker*/

    /** USELESS (?) -> probably YES*/
    public MarksTracker getMarksTracker() {
        return marksTracker;
    }

    /***/
    public void addMarksFrom(Player markingPlayer, int quantity){
        this.marksTracker.addMarksFrom(markingPlayer,quantity);
    }

    /***/
    public void getMarksFrom(Player markingPlayer){
        this.marksTracker.getNumberOfMarksSlotFrom(markingPlayer);
    }
}