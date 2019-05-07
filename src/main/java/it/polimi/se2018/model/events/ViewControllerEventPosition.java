package it.polimi.se2018.model.events;

import it.polimi.se2018.model.Position;

public class ViewControllerEventPosition extends ViewControllerEvent {

    private int X;

    private int Y;

    public ViewControllerEventPosition(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
