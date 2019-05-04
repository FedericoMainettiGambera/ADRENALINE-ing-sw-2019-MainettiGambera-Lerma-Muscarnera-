package it.polimi.se2018.model;


/***/
public class Mark extends Action {

    /***/
    public Mark() {
        super();
        getActionInfo().setPreConditionMethodName("alwaysTrue");

    }
    public Mark(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        Player target = getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
        Player marker = getActionInfo().getActionContext().getPlayer();
       // MarkSlots markSlots = target.getMarksFrom(marker);
        int quantity = getActionInfo().getActionDetails().getUserSelectedActionDetails().getGenericQuantity();
        getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(target);
        target.addMarksFrom(marker,quantity);
    }
}