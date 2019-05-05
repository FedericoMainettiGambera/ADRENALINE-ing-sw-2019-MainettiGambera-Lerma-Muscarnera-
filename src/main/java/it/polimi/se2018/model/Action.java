package it.polimi.se2018.model;


/*_______**/
public abstract class Action {

    /***/

    public Action() {}
    public Action(ActionInfo actionInfo){
        this.actionInfo = actionInfo;
    }
    public void updateSettingsFromFile() {          // this function takes the fileSetting and uses them to fill the parameters of setters of actionInfo

        return;

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