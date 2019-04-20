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
    public void addQuantity(int quantity){
        if(this.quantity+quantity <= GameConstant.MaxNumberOfAmmoCubes){
            this.quantity += quantity;
        }
        else{
            this.quantity = GameConstant.MaxNumberOfAmmoCubes;
        }
    }
}