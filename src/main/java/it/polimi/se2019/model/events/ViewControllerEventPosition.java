package it.polimi.se2019.model.events;

public class ViewControllerEventPosition extends ViewControllerEvent {

    private int X;

    private int Y;

    public ViewControllerEventPosition(int X, int Y){
        super();
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
