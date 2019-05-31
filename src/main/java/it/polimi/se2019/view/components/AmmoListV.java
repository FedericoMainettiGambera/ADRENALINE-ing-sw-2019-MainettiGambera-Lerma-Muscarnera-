package it.polimi.se2019.view.components;

import java.io.Serializable;
import java.util.List;

public class AmmoListV implements Serializable {
    private List<AmmoCubesV> ammoCubesList;

    public List<AmmoCubesV> getAmmoCubesList() {
        return ammoCubesList;
    }

    public void setAmmoCubesList(List<AmmoCubesV> ammoCubesList) {
        this.ammoCubesList = ammoCubesList;
    }

    @Override
    public String toString(){
        String s = "";
        for (AmmoCubesV a: ammoCubesList) {
            s+= a.getColor() + "->" + a.getQuantity() + "; ";
        }
        return s;
    }
}
