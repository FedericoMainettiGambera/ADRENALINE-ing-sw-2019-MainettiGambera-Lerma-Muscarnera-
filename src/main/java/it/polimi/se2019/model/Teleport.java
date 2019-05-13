package it.polimi.se2019.model;


import java.io.Serializable;

/***/
public class Teleport extends Action implements Serializable {

    /***/
    @Override
    public void setDefaultSetting() {
        return;
        /*it doesn't need anything from file specification*/
    }
    public void updateSettingsFromFile() {
        return;
       /*it doesn't need anything from file specification*/
    }
    public Teleport() {
        super();
        getActionInfo().setPreConditionMethodName("itsValidPosition");

    }
    public Teleport(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        Player player = getActionInfo().getActionContext().getPlayer();
        Position newPosition = getActionInfo().getActionDetails().getUserSelectedActionDetails().getNewPosition();
        getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(
                newPosition
                   );
        player.setPosition(
                newPosition.getX(),
                newPosition.getY()
        );


    }
}