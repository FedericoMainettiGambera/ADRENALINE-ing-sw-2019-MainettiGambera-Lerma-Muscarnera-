package it.polimi.se2019.view.components;


import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;

public class PlayerBoard implements ViewComponent {

    private ActionList actionList;
    private DamageTracker damageTracker;
    private MarksTracker marksTracker;
    private AmmoBox ammoBox;
    private DeathTracker deathTracker;

    public PlayerBoard(){


        this.actionList=new ActionList();
        this.damageTracker=new DamageTracker();
        this.marksTracker=new MarksTracker();
        this.ammoBox=new AmmoBox();
        this.deathTracker=new DeathTracker();




    }


    public ActionList getActionList() {
        return actionList;
    }

    public DamageTracker getDamageTracker() {
        return damageTracker;
    }

    public MarksTracker getMarksTracker() {
        return marksTracker;
    }

    public AmmoBox getAmmoBox() {
        return ammoBox;
    }

    public DeathTracker getDeathTracker() {
        return deathTracker;
    }

    @Override
    public void display(ModelViewEvent MVE) {

    }
}
