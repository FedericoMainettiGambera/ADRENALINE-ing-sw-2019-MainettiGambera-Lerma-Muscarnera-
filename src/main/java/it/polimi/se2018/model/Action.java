package it.polimi.se2018.model;


/***/
public abstract class Action {

    /***/
    public Action(ActionInfo actionInfo){
        this.actionInfo = actionInfo;
    }

    /***/
    private ActionInfo actionInfo;

    /***/
    public void Exec(){
    }

}