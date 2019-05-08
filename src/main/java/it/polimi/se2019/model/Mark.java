package it.polimi.se2019.model;


/***/
public class Mark extends Action {

    @Override
    public void setDefaultSetting() {
        getActionInfo().getActionDetails().getFileSelectedActionDetails().setMarksQuantity(1);
    }

    @Override
    public void updateSettingsFromFile() {
       setDefaultSetting();
       Object a = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0);
       if(ActionInfo.notNullAndNotDefault(a)) {

           getActionInfo().getActionDetails().getFileSelectedActionDetails().setMarksQuantity((Integer) a); // if there is a valid value it replace the default value 1.

       } else {

           return;

       }
    }

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