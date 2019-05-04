package it.polimi.se2018.model;


/***/
public class Teleport extends Action {

    /***/
    public Teleport() {

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