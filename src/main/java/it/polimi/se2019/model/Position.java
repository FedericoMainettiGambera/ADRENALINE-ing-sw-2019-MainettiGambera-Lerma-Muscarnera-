package it.polimi.se2019.model;

import java.io.Serializable;

/**Position is a pure class, it can't be modified.
 * @author FedericoMainettiGambera
 * */
public class Position implements Serializable {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets X and Y.
     * @param X must be >= 0
     * @param Y must be >= 0
     * @throws IllegalArgumentException
     * */
    public String humanString() {
        return "[y:" + this.getY() + " , x: " +this.getX() + "]";
    }
    public Position(int X, int Y) throws IllegalArgumentException {
        if(X<0 || Y<0){
            throw new IllegalArgumentException("Coordinates can't be negative.");
        }
        this.X = X;
        this.Y = Y;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /**X*/
    private int X;

    /**Y*/
    private int Y;

    /*-********************************************************************************************************METHODS*/
    /**@return X value
     * */
    public int getX(){
        return this.X;
    }

    /**@return Y value
     * */
    public int getY(){
        return this.Y;
    }

    public boolean equals(Position position){
        return this.X == position.getX() && this.Y == position.getY();
    }

}