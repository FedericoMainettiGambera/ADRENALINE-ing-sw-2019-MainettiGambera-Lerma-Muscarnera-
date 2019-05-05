package it.polimi.se2018.model;

import java.util.*;

/***/
public class Damage extends Action {

    @Override
    public void updateSettingsFromFile() {
        Object a = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0);
        if(ActionInfo.notNullAndNotDefault(a)) {
            /*nothing*/
        } else {
            return;
        }
        getActionInfo().getActionDetails().getFileSelectedActionDetails().setDamage((Integer) a);
    }

    /***/

    public Damage() {
        super();
        getActionInfo().setPreConditionMethodName("thereAreNotWallsBetweenTargetAndPlayer");
    }
    public Damage(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(                  /*it needs this field to be fillen by the user*/
                getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget()
        );
        Player shooter = getActionInfo().getActionContext().getPlayer();
        Player target = getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
        int    damageEntity = getActionInfo().getActionDetails().getFileSelectedActionDetails().getDamage();

        target.addDamages(shooter,
                        damageEntity);

    }
    
}