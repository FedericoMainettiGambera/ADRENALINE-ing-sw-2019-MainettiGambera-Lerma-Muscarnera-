package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.BotMoveState;
import it.polimi.se2019.controller.statePattern.FFSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotMoveStateTest {

    @Test
    public void TestBotMoveStateTest() throws IOException {

        ModelGate.setModel(FakeModel.getFakeModel()) ;
        ModelGate.getModel().getPlayerList().addPlayer(new Player(true));
        ModelGate.getModel().getPlayerList().getPlayer("Terminator").setPosition(0,1);

        BotMoveState state=new BotMoveState(new FFSetUpState());

        ViewControllerEventPosition viewControllerEventPosition=new ViewControllerEventPosition(0,0);

        state.parseVce(viewControllerEventPosition);
        state.calculatePossiblePositions(ModelGate.getModel().getCurrentPlayingPlayer());
        Position pos=new Position(0,0);

        assertEquals(pos.getX(), ModelGate.getModel().getPlayerList().getPlayer("Terminator").getPosition().getX());
        assertEquals(pos.getY(), ModelGate.getModel().getPlayerList().getPlayer("Terminator").getPosition().getY());



    }

}
