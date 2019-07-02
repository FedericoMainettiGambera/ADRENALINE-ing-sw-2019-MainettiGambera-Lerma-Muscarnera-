package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.TurnState;
import it.polimi.se2019.model.Game;
import org.junit.Test;

import java.io.IOException;

public class TestTurnState{


    @Test
    public void testTurnState() throws IOException {
        ModelGate.setModel((new FakeModel()).create());
        Game game=ModelGate.getModel();

        TurnState state=new TurnState(1);


    }
}
