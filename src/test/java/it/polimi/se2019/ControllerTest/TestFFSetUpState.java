package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FFSetUpState;
import it.polimi.se2019.model.Game;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestFFSetUpState{

    private Game game=new Game();
    private FakeModel fakeModel=new FakeModel();

    @Test
    public void TestFFSetUpState() throws IOException{

        game=fakeModel.create();
        ModelGate.setModel(game);

       game.getPlayerList().setStartingPlayer( game.getPlayerList().getPlayer("Alex"));

        FFSetUpState state=new FFSetUpState();
        state.prepareForFF();

        assertEquals(0,game.getPlayerList().getPlayer("Alex").getBeforeorafterStartingPlayer());



    }
}
