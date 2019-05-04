package it.polimi.se2018.model;


import java.util.List;

/***/
public class Effect {
    /*-****************************************************************************************************CONSTRUCTOR*/
    /***/
    public Effect(String description, List<Action> actions) {
        this.description = description;
        this.actions = actions;
    }

    /*TODO: delete*/
    public Effect() {
        this.actions = null;
    }

    /*-*****************************************************************************************************ATTRIBUTES*/
    /***/
    private String description;

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
    public void Exec() {
        boolean isExecutable = true;
        for(Action a:this.actions){
            if(a.getActionInfo().preCondition() == false ) {            // checks if all the preConditions are true
                isExecutable = false;
            }
        }

        if(isExecutable)
            for(Action a:this.actions) {
                a.Exec();
            }
    }

}