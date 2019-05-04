package it.polimi.se2018.model;


import it.polimi.se2018.model.enumerations.CardinalPoint;

/***/
public class Move extends Action {

    /***/
    public Move(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        /*
        * the user fills the direction field of CARD>actionInfo>userSelectedActionDetails>direction
        * and the exec function takes the Player from the context. ( it is fillen when card played )
        * */
        CardinalPoint direction = getActionInfo().getActionDetails().getUserSelectedActionDetails().getDirection();
        int a = 0;
        int b = 0;
        switch(direction) {
            case east: {
                a = 1;
            }
            break;
            case west: {
                a = -1;
            }
            break;
            case north: {
                b = 1;
            }
            break;
            case south: {
                b = -1;
            }
            break;
        }
        getActionInfo().getActionContext().getPlayer().setPosition(
                getActionInfo().getActionContext().getPlayer().getPosition().getX() + a,
                getActionInfo().getActionContext().getPlayer().getPosition().getY() + b
                );
    }
}