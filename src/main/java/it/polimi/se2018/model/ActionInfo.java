package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.CardinalPoint;

/***/
public class ActionInfo {
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
    class ActionDetails {
        class UserSelectedActionDetails {
            public CardinalPoint getDirection() {
                return direction;
            }
            public void setDirection(CardinalPoint direction) {
                this.direction = direction;
            }
            private CardinalPoint direction;
            public Square getChosenSquare() {
                return chosenSquare;
            }



            private Square chosenSquare;                // selected square



            public void setChosenSquare(Square chosenSquare) {
                this.chosenSquare = chosenSquare;
            }
            public Player getTarget() {
                return target;
            }

            public void setTarget(Player target) {
                this.target = target;
            }

            private Player target;

        }
        class FileSelectedActionDetails {

            public int getDamage() {
                return damage;
            }
            public void setDamage(int damage) {
                this.damage = damage;
            }
            private int damage;             // specified in card file

            public int getTargetsQuantity() {
                return targetsQuantity;
            }

            public void setTargetsQuantity(int targetsQuantity) {
                this.targetsQuantity = targetsQuantity;
            }

            private int targetsQuantity;

            public int getSquareMovement() {
                return squareMovement;
            }

            public void setSquareMovement(int squareMovement) {
                this.squareMovement = squareMovement;
            }

            private int squareMovement;                             // maximum movements possible

        }

        public UserSelectedActionDetails getUserSelectedActionDetails() {
            return userSelectedActionDetails;
        }
        public FileSelectedActionDetails getFileSelectedActionDetails() {
            return fileSelectedActionDetails;
        }

        private UserSelectedActionDetails userSelectedActionDetails;
        private FileSelectedActionDetails fileSelectedActionDetails;

    }

    class ActionContext {
        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

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