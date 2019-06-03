package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.List;

/***/
public class Mark extends Action implements Serializable {

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

        List<Player> target = getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList();
        Player marker = getActionInfo().getActionContext().getPlayer();
       // MarkSlots markSlots = target.getMarksFrom(marker);
        int quantity = Integer.parseInt((String) getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0));
         for(Player t: target) {
             t.addMarksFrom(marker, quantity);
             System.out.println("marking a " + t.getNickname() + " in: " +  quantity + " mark(s)");

         }
    }
}