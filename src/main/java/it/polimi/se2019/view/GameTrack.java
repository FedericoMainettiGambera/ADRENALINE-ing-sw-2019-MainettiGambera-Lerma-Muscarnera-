package it.polimi.se2019.view;

public class GameTrack {

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
}
