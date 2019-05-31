package it.polimi.se2019.model;

import java.util.ArrayList;
import java.util.List;

public class PreConditionMethodsInverted {
    public List<Object> distanceOfTargetFromPlayerSquareIs1(ActionContext actionContext) {
        List<Object> playerList = new ArrayList<>();

        Player user   = actionContext.getPlayer();

        Square[][] map = actionContext.getBoard().getMap();

        for(int x = (user.getPosition().getX() - 1); x < (user.getPosition().getX() + 1);x++) {
            for(int y = (user.getPosition().getX() - 1); y < (user.getPosition().getX() + 1);y++) {

                for(Player possibile: actionContext.getPlayerList().getPlayers()) {
                    if((possibile.getPosition().getX() == x) && (possibile.getPosition().getY() == y)) {
                        if(!playerList.contains(possibile)) {
                            playerList.add(possibile);
                        }
                    }

                }

            }

        }

        return playerList;
    }
}
