package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.CardinalPoint;

/***/
public class ActionInfo {
    private String preConditionMethodName;
    public ActionInfo()  {

        preConditionMethodName = new String("alwaysTrue");

    }
    class PreConditionMethods {
            public boolean alwaysTrue() {

                return true;

            }
            public boolean thereAreNotWallsBetweenTargetAndPlayer(ActionDetails actionDetails,ActionContext actionContext) {

                return false;

            }

    }
    private  PreConditionMethods preConditionMethods;
    public  boolean preCondition() {
        try {
            java.lang.reflect.Method method;
            Class<?> c = Class.forName("class name");
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
    class ActionDetails {
        private int damage;             // specified in card file
        private CardinalPoint direction;
        private int targetsQuantity;
        private int squareMovement;
        private Square chosenSquare;                // selected square
        private Player target;
    }

    class ActionContext {
        private Player player;          // context of the action: initialized during Effect.exec()
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