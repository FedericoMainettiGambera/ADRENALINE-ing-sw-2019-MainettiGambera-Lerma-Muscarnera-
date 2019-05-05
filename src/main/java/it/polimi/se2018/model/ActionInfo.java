package it.polimi.se2018.model;

/***/
public class ActionInfo {
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
        preConditionMethodName = new String("alwaysTrue");

    }

    private  PreConditionMethods preConditionMethods;
    public  boolean preCondition() {
        try {
            java.lang.reflect.Method method;
            Class<?> c = Class.forName("PreConditionMethods");
            Class<?>[] paramTypes = {ActionDetails.class, ActionContext.class};
            method = c.getDeclaredMethod(preConditionMethodName, null);
            Object returnValue = method.invoke(preConditionMethods,this.actionDetails,this.actionContext);
            return (boolean) returnValue;
        }
        catch(Exception E) {

            ;
        }
        return false;               // if it throws exception it returns false
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