package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.*;
import java.io.*;

public class AmmoCubes {

    /***/
    public AmmoCubes(AmmoCubesColor color){

        quantity=0;
        color=color;
    }

    /***/
    private int quantity;

    /***/
    private AmmoCubesColor color;

    public int getQuantity() {
        return quantity;
    }

    public AmmoCubesColor getColor(){
        return color;
    }


    public void addQuantity(int quantity) throws Exception{

        this.quantity+=quantity;
        if(this.quantity>3){

            this.quantity=3;

        }


            if(this.quantity < 0){



                throw new Exception("Ammo aint sufficient for payment");




            }



    }

}