package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.ArrayList;
import java.util.List;


/***/
public class AmmoList{

    /***/
    public AmmoList(){
        this.ammoCubesList = new ArrayList<>();
    }

    /***/
    private List<AmmoCubes> ammoCubesList;

    /***/
    public List<AmmoCubes> getAmmoCubesList() {
        return ammoCubesList;
    }

    /***/
    public void addAmmoCubesOfColor(AmmoCubesColor color, int quantity) {
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.getAmmoCubesList().get(i).getColor() == color){
                this.getAmmoCubesList().get(i).addQuantity(quantity);
                return;
            }
        }
        this.ammoCubesList.add(new AmmoCubes(color));
    }

}