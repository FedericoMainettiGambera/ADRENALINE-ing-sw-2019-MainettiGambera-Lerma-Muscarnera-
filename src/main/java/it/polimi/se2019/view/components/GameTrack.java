package it.polimi.se2019.view.components;

import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;

public class GameTrack implements ViewComponent {

    private KillShotTrack killShotTrack;
    private DominationModeTrack dominationModeTrack;
    private TurretsModeTrack turretsModeTrack;

    public GameTrack(){

        this.killShotTrack=new KillShotTrack();
        this.dominationModeTrack=new DominationModeTrack();
        this.turretsModeTrack=new TurretsModeTrack();



    }


    public DominationModeTrack getDominationModeTrack() {
        return dominationModeTrack;
    }

    public TurretsModeTrack getTurretsModeTrack() {
        return turretsModeTrack;
    }

    public KillShotTrack getKillShotTrack() {
        return killShotTrack;
    }

    @Override
    public void display(ModelViewEvent MVE) {

    }
}
