package it.polimi.se2019.model;

/**Position is a pure class, it can't be modified.
 * @author FedericoMainettiGambera
 * */
public class Position {

    /*-****************************************************************************************************CONSTRUCTOR*/
    /**Constructor:
     * sets X and Y.
     * @param X must be >= 0
     * @param Y must be >= 0
     * @throws IllegalArgumentException
     * */
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

}