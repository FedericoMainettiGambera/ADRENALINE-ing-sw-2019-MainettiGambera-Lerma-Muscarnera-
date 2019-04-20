package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.ArrayList;
import java.util.List;


/**The AmmoList class keeps track of the current number of ammos a player has.
 * THIS CLASS MUST NEVER BE USED, INSTEAD USE THE "Player" CLASS.
 * */
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

    /***/
    public boolean payAmmoCubesOfColor(AmmoCubesColor color, int quantity){
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.getAmmoCubesList().get(i).getColor() == color){
                return this.getAmmoCubesList().get(i).subQuantity(quantity);
            }
        }
        return false;
    }

}