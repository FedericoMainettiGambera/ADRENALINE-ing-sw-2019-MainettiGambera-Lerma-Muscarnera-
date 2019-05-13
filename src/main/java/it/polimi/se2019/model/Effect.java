package it.polimi.se2019.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***/
public class Effect implements Serializable {
    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
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