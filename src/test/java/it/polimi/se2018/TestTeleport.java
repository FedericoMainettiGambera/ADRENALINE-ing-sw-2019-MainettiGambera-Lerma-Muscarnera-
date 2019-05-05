package it.polimi.se2018;

import it.polimi.se2018.model.Action;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.Teleport;
import org.junit.Test;

public class TestTeleport {
    @Test
    public void testTeleport() {
        Action A = new Teleport();
        Player Me = new Player();
        A.getActionInfo().getActionContext().setPlayer(Me);
        Position newPosition = new Position(5,5);
        A.getActionInfo().getActionDetails().getUserSelectedActionDetails().setNewPosition(newPosition);
        A.Exec();
    }
}
