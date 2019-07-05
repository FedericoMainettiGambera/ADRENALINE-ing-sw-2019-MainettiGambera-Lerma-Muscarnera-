package it.polimi.se2019.model.events.viewControllerEvents;

/**a event containing a position chosen by the user
 * @author FedericoMaineetiGambera
 * @author LudoLerma */
public class ViewControllerEventPosition extends ViewControllerEvent {
    /**component x of the coordinates that individuate the position*/
    private int X;
    /**component y of the coordinates that individuate the position*/
    private int Y;
    /**constructor,
     * @param Y to set up Y attribute
     * @param X to set up X attribute*/
    public ViewControllerEventPosition(int X, int Y){
        super();
        this.X = X;
        this.Y = Y;
    }
    /**@return X*/
    public int getX() {
        return X;
    }
    /**@return Y*/
    public int getY() {
        return Y;
    }
}
