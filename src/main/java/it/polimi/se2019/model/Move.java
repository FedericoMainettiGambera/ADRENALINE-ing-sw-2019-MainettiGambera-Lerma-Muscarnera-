package it.polimi.se2019.model;


import it.polimi.se2019.model.enumerations.CardinalPoint;

/***/
public class Move extends Action {

    /***/
    public void setDefaultSetting() {
        getActionInfo().getActionDetails().getFileSelectedActionDetails().setSquareMovement(1); // the default number of moves is 1
    }
    public void updateSettingsFromFile() {
        setDefaultSetting(); // if not changed it uses the default ones
        Object a = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0); // number of moves
        Integer a_i = 0;
        if (ActionInfo.notNullAndNotDefault(a)) {
            try {
                a_i = (Integer) a;
                getActionInfo().getActionDetails().getFileSelectedActionDetails().setSquareMovement(a_i);
            } catch (Exception e) {
                return;
            }
        } else {
            return;
        }

    }
    public Move(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        /*
        * the user fills the direction field of CARD>actionInfo>userSelectedActionDetails>direction
        * and the exec function takes the Player from the context. ( it is fillen when card played )
        * */
        getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(                  /*it needs this field to be fillen by the user*/
                getActionInfo().getActionDetails().getUserSelectedActionDetails().getDirection()
        );

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