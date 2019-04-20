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
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.ammoCubesList.get(i).getColor()==color){
                return this.ammoCubesList.get(i).canSubQuantity(quantity);
            }
        }
        return false;
    }

    /***/
    public boolean canPayAmmoCubes(AmmoList cost){
        /*checks if the payment can be done*/
        for(int i = 0; i < cost.getAmmoCubesList().size(); i++){
            for(int j = 0; j < ammoCubesList.size(); j++){
                if(cost.getAmmoCubesList().get(i).getColor() == ammoCubesList.get(j).getColor()){
                    if(!ammoCubesList.get(j).canSubQuantity( cost.getAmmoCubesList().get(i).getQuantity() )){
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }

    /***/
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        if(!canPayAmmoCubes(color, quantity)){
            return false;
        }
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.ammoCubesList.get(i).getColor()==color){
                this.ammoCubesList.get(i).addQuantity(quantity);
            }
        }
        return true;
    }

    /**Same method as the above one, but the parameter is an ammoList("cost"), es. weapon's cards pick up cost...*/
    public boolean payAmmoCubes(AmmoList cost) {

        if(!canPayAmmoCubes(cost)){
            return false;
        }

        /*makes the payment*/
        for(int i = 0; i < cost.getAmmoCubesList().size(); i++){
            for(int j = 0; j < ammoCubesList.size(); j++){
                if(cost.getAmmoCubesList().get(i).getColor() == ammoCubesList.get(j).getColor()){
                    ammoCubesList.get(j).subQuantity(cost.getAmmoCubesList().get(i).getQuantity());
                }
            }
            /*to return true at the end*/
            if(i == cost.getAmmoCubesList().size()-1){
                return true;
            }
        }

        /*default case*/
        return true;
    }

}