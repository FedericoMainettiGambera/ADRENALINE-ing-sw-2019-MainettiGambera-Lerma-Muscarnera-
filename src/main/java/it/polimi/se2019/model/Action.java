package it.polimi.se2019.model;


import java.io.Serializable;

/*_______**/
public class Action implements Serializable {

    /***/

    public Action() {
        this.actionInfo = new ActionInfo();
        this.actionInfo.setActionDetails(new ActionDetails());
        this.actionInfo.setActionContext(new ActionContext());
    }
    public Action(ActionInfo actionInfo){
        this.actionInfo = actionInfo;
    }
    public void updateSettingsFromFile() {          // this function takes the fileSetting and uses them to fill the parameters of setters of actionInfo


    }
    public void setDefaultSetting() {
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