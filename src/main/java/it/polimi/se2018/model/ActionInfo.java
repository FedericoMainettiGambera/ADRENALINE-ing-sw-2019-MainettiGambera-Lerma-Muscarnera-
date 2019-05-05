package it.polimi.se2018.model;

import it.polimi.se2018.model.enumerations.CardinalPoint;

import java.util.List;

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
    class PreConditionMethods {
            public boolean validPayment(ActionDetails actionDetails,ActionContext actionContext) {
                Player player = actionContext.getPlayer();
                AmmoCubes ammoCost = actionDetails.getFileSelectedActionDetails().getAmmoCubesCost();
                if(player.getPlayerBoard().canPayAmmoCubes(ammoCost.getColor(), ammoCost.getQuantity() )) {

                    return true;

                }

                return false;

            }
            public boolean alwaysTrue() {

                return true;

            }
            public boolean itsValidPosition(ActionDetails actionDetails,ActionContext actionContext) {
                int x = actionDetails.getUserSelectedActionDetails().getNewPosition().getX();
                int y = actionDetails.getUserSelectedActionDetails().getNewPosition().getY();
                try {
                    Board board = new Board(""); // example. TODO: insert board reference here
                    if(board.getBoard()[x][y] != null) {
                        return true;
                    }
                    return false;
                } catch (Exception e) {}
                finally {
                    return false;
                }
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
        public void itNeeds(Object ... neededObjects) {           // notify the model/view that these fields need to be filled by the user
                boolean isOk = true;
                for(Object n: neededObjects ) {
                    if(n == null) {
                        // notify system
                        isOk = false;
                    }
                if(!isOk) {

                    itNeeds(neededObjects);         //TODO: it stops the execution of the program while all the fields are fillen... to improve

                }
                }

            }
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


            public int getGenericQuantity() {
                return genericQuantity;
            }

            public void setGenericQuantity(int genericQuantity) {
                this.genericQuantity = genericQuantity;
            }

            private int genericQuantity;
            private Square chosenSquare;                // selected square


            public Position getNewPosition() {
                return newPosition;
            }

            public void setNewPosition(Position newPosition) {
                this.newPosition = newPosition;
            }

            private Position newPosition;
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
            List<Object> fileSettingData;       // lista dei dati inseriti via file

            public void addFileSettingData(Object o) {
                fileSettingData.add(o);
            }
            public List<Object> getFileSettingData() {
                return fileSettingData;
            }
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


            public AmmoCubes getAmmoCubesCost() {
                return ammoCubesCost;
            }

            public void setAmmoCubesCost(AmmoCubes ammoCubesCost) {
                this.ammoCubesCost = ammoCubesCost;
            }

            private AmmoCubes ammoCubesCost;


            public int getMarksQuantity() {
                return marksQuantity;
            }

            public void setMarksQuantity(int marksQuantity) {
                this.marksQuantity = marksQuantity;
            }

            private int marksQuantity;

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