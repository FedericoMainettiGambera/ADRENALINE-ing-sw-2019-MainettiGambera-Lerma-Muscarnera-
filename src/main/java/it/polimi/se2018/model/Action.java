package it.polimi.se2018.model;


/*_______**/
public abstract class Action {

    /***/
    public Action(ActionInfo actionInfo){
        this.actionInfo = actionInfo;
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    /***/
    private ActionInfo actionInfo;

    /***/
    public void Exec(){
    }

}