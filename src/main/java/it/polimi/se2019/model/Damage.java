package it.polimi.se2019.model;

import java.io.Serializable;

/**
 * This is one of the three actions that are used to build all the effects of cards in the game.
 * It gives a damage of a certain "quantity of damage" to a "target"
 *
 * @author LucaMuscarnera
 * */
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

    /**
     * default constructor
     * */

    public Damage() {
        super();

        //getActionInfo().setPreConditionMethodName("thereAreNotWallsBetweenTargetAndPlayer");
    }
    /**
     * @param actionInfo contains the actionInfo to put in the current action
     * */
    public Damage(ActionInfo actionInfo) {
        super(actionInfo);
    }

    /**
     * Executes the damage
     * */
    public void Exec() {
        /*@*/
        //System.out.println("Eseguo azione");
        /*@*/
        // rimozione dell'eventuale player
        if( getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().contains(getActionInfo().getActionContext().getPlayer()))
            getActionInfo().getActionDetails().getUserSelectedActionDetails().getTargetList().remove(getActionInfo().getActionContext().getPlayer());

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

                    //System.out.println("colpisco " + t.getNickname() + ": " + damageEntity + " danno (i) " );
                    target.addDamages(shooter,
                            damageEntity);
                } else {

                    //System.out.println("non puoi colpirti da solo!");
                }
             }
        }
    }
    
}