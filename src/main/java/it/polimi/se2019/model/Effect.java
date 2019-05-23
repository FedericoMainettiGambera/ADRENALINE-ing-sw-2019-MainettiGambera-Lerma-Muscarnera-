package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***/
public class Effect implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/

    public EffectInfo getEffectInfo() {
        return effectInfo;
    }

    public void setEffectInfo(EffectInfo effectInfo) {
        this.effectInfo = effectInfo;
    }

    /***/
    private EffectInfo effectInfo;
    public Effect(String description, List<Action> actions) {
        this.description = description;
        this.actions = actions;
    }


    public Effect() {
        this.actions = new ArrayList<Action>();
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /***/
    private List<Action> actions;

    /*-********************************************************************************************************METHODS*/
    /***/
    public List<Action> getActions() {
        return actions;
    }

    /***/
    public String getDescription() {
        return description;
    }

    /***/
    public boolean Exec() {
        boolean isExecutable = true;
        /*gestione effect info*/
        if(effectInfo.getData() == 0) {     //  input un solo target
            Player target = new Player();
            // TODO inserimento del target
            for(Action a:this.actions){
                if(a.getActionInfo().preCondition() == false ) {
                    a.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(target);
                }
            }
        }

        if(effectInfo.getData() == 1) {     //  input di più target
            List<Player> targetList = new ArrayList<Player>();
            // TODO inserimento della lista di target
            for(Action a:this.actions){
                if(a.getActionInfo().preCondition() == false ) {
                  a.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTargetList(targetList);
                }
            }
        }


        for(Action a:this.actions){
            if(a.getActionInfo().preCondition() == false ) {            // checks if all the preConditions are true
                isExecutable = false;
            }
        }
        if(isExecutable) {
            for (Action a : this.actions) {
                a.Exec();
            }
            return true;
        }
        return false;
    }

}