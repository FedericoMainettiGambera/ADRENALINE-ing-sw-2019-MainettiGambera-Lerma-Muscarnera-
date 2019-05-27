package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.AmmoCubesColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The AmmoList class keeps track of the current number of ammos a player has.
 * @author FedericoMainettiGambera
 * */
public class AmmoList implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * initialize the ammoCubesLists with a new ArrayList of AmmoCubes
     * */
    public AmmoList(){
        this.ammoCubesList = new ArrayList<>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**ammo cubes list*/
    private List<AmmoCubes> ammoCubesList;

    /*-********************************************************************************************************METHODS*/

    public void addAmmoList(AmmoList ammoList){
        for (int i = 0; i < ammoList.getAmmoCubesList().size(); i++) {
            this.addAmmoCubesOfColor(ammoList.getAmmoCubesList().get(i).getColor(), ammoList.getAmmoCubesList().get(i).getQuantity());
        }
    }


    /**avoid this method if possible, do not access directly attributes, but use method that interact with them for you.
     * @return
     * */
    public List<AmmoCubes> getAmmoCubesList() {
        return ammoCubesList;
    }

    /**add a specific amount of ammo of a specific color to the ArrayList of AmmoCubes.
     * this method uses the AmmoCubes.addQuantity(int quantity) method that makes sure that the resulting quantity
     * is never more than GameConstant.MaxNumberOfAmmoCubes.
     * @param quantity
     * @param color
     * */
    public void addAmmoCubesOfColor(AmmoCubesColor color, int quantity){
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.getAmmoCubesList().get(i).getColor() == color){
                this.getAmmoCubesList().get(i).addQuantity(quantity);
                return;
            }
        }
        int i=0;
        this.ammoCubesList.add(new AmmoCubes(color));
        while( i<this.ammoCubesList.size()){i++;}
        this.getAmmoCubesList().get(i-1).addQuantity(quantity);
    }

    /**checks if an amount of ammos can be payed with the current this.ammoCubesList status
     * @param color
     * @param quantity
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoCubesColor color, int quantity){
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.ammoCubesList.get(i).getColor()==color){
                return this.ammoCubesList.get(i).canSubQuantity(quantity);
            }
        }
        return false;
    }

    /**checks if an amount of ammos can be payed with the current this.ammoCubesList status
     * @param cost
     * @return
     * */
    public boolean canPayAmmoCubes(AmmoList cost){
        /*checks if the payment can be done*/
        for(int i = 0; i < cost.getAmmoCubesList().size(); i++){
            for(int j = 0; j < ammoCubesList.size(); j++){
                if(cost.getAmmoCubesList().get(i).getColor() == ammoCubesList.get(j).getColor()){
                    if(ammoCubesList.get(j).canSubQuantity( cost.getAmmoCubesList().get(i).getQuantity() )){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**subtract a specified amount of ammos to the current this.ammoCubesList of the player.
     * Also this method checks if the payment can be done or not using the canPayAmmoCubes(...) method.
     * @param quantity
     * @param color
     * @return
     * */
    public boolean payAmmoCubes(AmmoCubesColor color, int quantity){
        if(!canPayAmmoCubes(color, quantity)){
            return false;
        }
        for(int i = 0; i < this.ammoCubesList.size(); i++){
            if(this.ammoCubesList.get(i).getColor()==color){
                if(this.ammoCubesList.get(i).subQuantity(quantity)){
                    return true;
                }
            }
        }
        /*error case*/
        return false;
    }

    /**subtract a specified amount of ammos to the current this.ammoCubesList of the player.
     * Also this method checks if the payment can be done or not using the canPayAmmoCubes(...) method.
     * @param cost
     * @return
     * */
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
        /*error case*/
        return false;
    }

    public String toString(){
        String s = "     ";
        for (int i = 0; i < this.ammoCubesList.size() ; i++) {
            s += this.ammoCubesList.get(i).getColor() + ": " + this.ammoCubesList.get(i).getQuantity() + "\n    ";
        }
        return s;
    }
}