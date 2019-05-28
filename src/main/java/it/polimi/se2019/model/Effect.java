package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2019.model.enumerations.EffectInfoType.*;

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
    public void handleInput(Object[][] input) {
        int i= 0;
        for(EffectInfoElement e: this.getEffectInfo().getEffectInfoElement()) {
            for(Integer position: e.getEffectInfoTypeDestination()) {
                //parser dell'input
                System.out.println("<"+e.getEffectInfoTypelist().toString()+">");
                if(e.getEffectInfoTypelist().toString().equals(singleTarget.toString())) {
                   this.getActions().get(position).getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget((Player)input[i][0]);
                   /***/

                   for(Action a: this.getActions()) /*aggiunge la cronologia degli input ad ogni azione*/
                            a.getActionInfo().getActionContext().getActionContextFilteredInputs().add(new ActionContextFilteredInput(input[i],"Target"));
                }
                i++;
            }


        }
    }
    /***/
    public boolean Exec() {
        boolean isExecutable = true;
        /*gestione effect info */

        for(Action a:this.actions){
            if(a.getActionInfo().preCondition() == false ) {            // checks if all the preConditions are true
                isExecutable = false;
            }
        }
        if(isExecutable) {
            for (Action a : this.actions) {
                /*@*/ System.out.println("esecuzione" + a.toString());
                //System.out.println("> " + a.getActionInfo().getActionContext().getActionContextFilteredInputs().get(0));
                a.Exec();
                /*refreshing the context of the action*/
                for(Action b: this.actions) {
                  //  a.getActionInfo().setActionContext();
                }
            }
            return true;
        }
        return false;
    }

}