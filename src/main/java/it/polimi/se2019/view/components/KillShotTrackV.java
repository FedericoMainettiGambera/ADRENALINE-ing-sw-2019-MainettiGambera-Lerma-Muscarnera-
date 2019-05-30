package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class KillShotTrackV implements Serializable {

    private int numberOfStartingSkulls;

    private List<KillsV> killsV;

    public List<KillsV> getKillsV() {
        return killsV;
    }

    public void setKillsV(List<KillsV> killsV) {
        this.killsV = killsV;
    }

    public int getNumberOfStartingSkulls() {
        return numberOfStartingSkulls;
    }

    public void setNumberOfStartingSkulls(int numberOfStartingSkulls) {
        this.numberOfStartingSkulls = numberOfStartingSkulls;
    }
}
