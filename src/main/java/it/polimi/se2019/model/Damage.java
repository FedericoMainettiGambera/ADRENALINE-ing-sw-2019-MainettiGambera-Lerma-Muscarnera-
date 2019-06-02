package it.polimi.se2019.model;

import java.io.Serializable;

/***/
public class Damage extends Action implements Serializable {

    @Override
    public void updateSettingsFromFile() {
        Object a = getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0);
        if(ActionInfo.notNullAndNotDefault(a)) {
            /*nothing*/
        } else {
            return;
        }
        getActionInfo().getActionDetails().getFileSelectedActionDetails().setDamage(Integer.parseInt((String)a));
    }

    /***/

    public Damage() {
        super();

        //getActionInfo().setPreConditionMethodName("thereAreNotWallsBetweenTargetAndPlayer");
    }
    public Damage(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /***/
    public void Exec() {
        /*@*/
        System.out.println("Eseguo azione");
        /*@*/
         for (Player t : getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList()) {
         //Player t =    getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().get(0);
             if(t != null) {

                if(!t.equals(getActionInfo().getActionContext().getPlayer())) {

                    getActionInfo().getActionDetails().getUserSelectedActionDetails().itNeeds(                  /*it needs this field to be fillen by the user*/
                            t
                    );

                    Player shooter = getActionInfo().getActionContext().getPlayer();
                    Player target = t;

                    int damageEntity = Integer.parseInt((String) getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData().get(0));
                    System.out.println("colpisco " + t.getNickname() + ": " + damageEntity + " danno (i) " );
                    target.addDamages(shooter,
                            damageEntity);
                } else {

                    System.out.println("non puoi colpirti da solo!");
                }
             }
        }
    }
    
}