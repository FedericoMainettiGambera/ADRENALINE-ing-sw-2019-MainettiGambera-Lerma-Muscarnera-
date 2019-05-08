package it.polimi.se2018.model;

public class PreConditionMethods {
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

    public boolean alwaysFalse(ActionDetails actionDetails, ActionContext actionContext) {
        return false;
    }
    public boolean alwaysTrue(ActionDetails actionDetails, ActionContext actionContext) {

        return true;

    }
    public boolean itsValidPosition(ActionDetails actionDetails, ActionContext actionContext) {
        int x = actionDetails.getUserSelectedActionDetails().getNewPosition().getX();
        int y = actionDetails.getUserSelectedActionDetails().getNewPosition().getY();
        try {
            Board board = new Board(""); // example. TODO: insert board reference here
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