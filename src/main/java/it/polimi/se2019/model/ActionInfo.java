package it.polimi.se2019.model;

import java.io.Serializable;

/***/
public class ActionInfo implements Serializable {
    public static boolean notNullAndNotDefault(Object a) {
        if( !(((String) a ).equals("DEFAULT"))  && (a != null) ) {
            return true;
        }
        return false;
    }
    public String getPreConditionMethodName() {
        return preConditionMethodName;
    }

    public void setPreConditionMethodName(String preConditionMethodName) {
        this.preConditionMethodName = preConditionMethodName;
    }

    private String preConditionMethodName;
    public ActionInfo()  {
        actionDetails = new ActionDetails();
        actionContext = new ActionContext();
        preConditionMethods = new PreConditionMethods();
        preConditionMethodName = "alwaysTrue";

    }

    private  PreConditionMethods preConditionMethods;
    public  boolean preCondition()  {
        try {
            System.out.println("## (" +  getActionContext().getPlayer().getNickname() +") verifico " + this.preConditionMethodName + " in " +  actionContext.getPlayer().toString() + ":" + actionContext.getActionContextFilteredInputs().size());
            //System.out.println(".");
            java.lang.reflect.Method method;
            //System.out.println(".");
            Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethods");
            //System.out.println(".");
            Class<?>[] paramTypes = {ActionDetails.class, ActionContext.class};
            //System.out.println(".");
            method = c.getDeclaredMethod(preConditionMethodName, paramTypes);
            //System.out.println(".");
            //System.out.println("Il player corrente " + getActionContext().getPlayer().getNickname());
            //System.out.println(".");
            Object returnValue = method.invoke(preConditionMethods,this.actionDetails,this.actionContext);

            return (boolean) returnValue;
        }
        catch(Exception E) {
            System.out.println("eccezione! " + E.toString());
            return false;

        }
        //return false;               // if it throws exception it returns false
    }




    private ActionContext actionContext;
    private ActionDetails actionDetails;

    public ActionContext getActionContext() {
        return actionContext;
    }

    public void setActionContext(ActionContext actionContext) {
        this.actionContext = actionContext;
    }



    public ActionDetails getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(ActionDetails actionDetails) {
        this.actionDetails = actionDetails;
    }


    // ...
}