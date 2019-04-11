package it.polimi.se2018.model;

/***/
public class PlayerBoard {

    /***/
    public PlayerBoard() {
        this.ammoBox = new AmmoList();
        this.damagesTracker = new DamagesTracker();
        this.marksTracker = new MarksTracker();
        this.deathCounter = 0;
    }

    /***/
    private AmmoList ammoBox;

    /***/
    private DamagesTracker damagesTracker;

    /***/
    private MarksTracker marksTracker;

    /***/
    private int deathCounter;

    /***/
    public void addDeath() {
        deathCounter++;
    }

    /***/
    public int getDeathCounter() {
        return deathCounter;
    }

    /***/
    public AmmoList getAmmoBox() {
        return ammoBox;
    }

    /***/
    public DamagesTracker getDamagesTracker() {
        return damagesTracker;
    }

    /***/
    public MarksTracker getMarksTracker() {
        return marksTracker;
    }
}