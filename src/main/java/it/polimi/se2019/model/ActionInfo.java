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

            java.lang.reflect.Method method;

            Class<?> c = Class.forName("it.polimi.se2019.model.PreConditionMethods");

            Class<?>[] paramTypes = {ActionDetails.class, ActionContext.class};

            method = c.getDeclaredMethod(preConditionMethodName, paramTypes);
            System.out.println(">> " + getActionContext().getPlayer().getNickname());
            Object returnValue = method.invoke(preConditionMethods,this.actionDetails,this.actionContext);

            return (boolean) returnValue;
        }
        catch(Exception E) {

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