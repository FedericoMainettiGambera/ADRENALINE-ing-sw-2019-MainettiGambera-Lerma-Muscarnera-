package it.polimi.se2019.model;

import java.io.Serializable;

public class PreConditionMethods implements Serializable {
    public boolean validPayment(ActionDetails actionDetails, ActionContext actionContext) {
        Player player = actionContext.getPlayer();
        AmmoCubes ammoCost = actionDetails.getFileSelectedActionDetails().getAmmoCubesCost();
        if(player.getPlayerBoard().canPayAmmoCubes(ammoCost.getColor(), ammoCost.getQuantity() )) {

            return true;

        }

        return false;

    }
    public boolean alwaysExceptional(ActionDetails actionDetails, ActionContext actionContext) throws Exception {
        throw new Exception("");
    }
    public boolean moveOtherPlayer(ActionDetails actionDetails, ActionContext actionContext) throws Exception {
        // TODO: communication with user
        // not a real precondition
        return true;
    }
    public boolean alwaysFalse(ActionDetails actionDetails, ActionContext actionContext) {
        return false;
    }
    public boolean alwaysTrue(ActionDetails actionDetails, ActionContext actionContext) {

        return true;

    }

        public boolean isReaperMode(ActionDetails actionDetails, ActionContext actionContext) {
        // TODO: it needs a static reference to game mode.
        return true;
        }
        public boolean sameSquareTarget(ActionDetails actionDetails, ActionContext actionContext) {
        actionDetails.getUserSelectedActionDetails().setTarget(null); // TODO : important info : this is not a proper precondition but a it forces the selection of the target to the players in the same square
        // TODO there is no connection between player and board so it cant select the players,
        // TODO also, implement the possibility of using more targets
        return true;

    }
    public boolean youCanSee(ActionDetails actionDetails, ActionContext actionContext) {
        int Tx = actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getX();
        int Ty = actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getY();

        int Px = actionContext.getPlayer().getPosition().getX();
        int Py = actionContext.getPlayer().getPosition().getY();

        if((Tx == Px) || (Py == Ty)) {          //visibile
            if((Tx == Px)) {                    //sono sulla stessa verticale
                if(Ty>Py) {

                } else {
                    if(Ty<Py) {
                        int i = Ty;
                        int j = Py;
                        boolean noWalls = true;
                        for(i = Ty;i < j;i++) {
                            // TODO: insert board reference

                        }
                        return noWalls;
                    } else {
                        return true;            // nella stessa cella
                    }
                }

            } else {
                int i = Py;
                int j = Ty;
                boolean noWalls = true;
                for(i = Py;i < j;i++) {
                    // TODO: insert board reference

                }
                return noWalls;
            }


        }


        return true;

    }

    public boolean itsValidPosition(ActionDetails actionDetails, ActionContext actionContext) {
        int x = actionDetails.getUserSelectedActionDetails().getNewPosition().getX();
        int y = actionDetails.getUserSelectedActionDetails().getNewPosition().getY();
        try {
            Board board = new Board("", null); // example. TODO: insert board reference here
            if(board.getMap()[x][y] != null) {
                return true;
            }
            return false;
        } catch (Exception e) {}
        finally {
            //  return false;
        }
        return false;
    }
    public boolean thereAreNotWallsBetweenTargetAndPlayer(ActionDetails actionDetails, ActionContext actionContext) {
        Position playerPos = actionContext.getPlayer().getPosition();
        Position targetPos = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        boolean noWalls = true;
        /*
         *
         * */
        return false;

    }

}