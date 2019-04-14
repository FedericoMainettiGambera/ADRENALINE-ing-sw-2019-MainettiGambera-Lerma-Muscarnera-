package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.AmmoCubesColor;

import java.util.*;
import java.io.*;

public class AmmoCubes {

    /***/
    public AmmoCubes(AmmoCubesColor color){

        quantity=0;
        this.color=color;
    }

    /***/
    private int quantity;

    /***/
    private AmmoCubesColor color;

    /***/
    public int getQuantity() {
        return this.quantity;
    }

    /***/
    public AmmoCubesColor getColor(){
        return this.color;
    }

    /***/
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