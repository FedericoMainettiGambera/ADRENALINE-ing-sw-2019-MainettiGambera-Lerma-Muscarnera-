package it.polimi.se2018.model;

/***/
public class Position {

    /***/
    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /***/
    private int X;

    /***/
    private int Y;

    /***/
    public int getX(){
        return this.X;
    }

    /***/
    public int getY(){
        return this.Y;
    }

}