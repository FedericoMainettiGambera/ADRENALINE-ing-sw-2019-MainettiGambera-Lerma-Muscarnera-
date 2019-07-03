package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.RunAroundState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRunAroundState{

    private FakeModel fakeModel=new FakeModel();
    private Game game=new Game();
    private ViewControllerEventPosition vce=new ViewControllerEventPosition(0, 0);

    @Test
    public void testRunAroundState(){
        try {
            game=fakeModel.create();
            ModelGate.setModel(game);


        } catch (IOException e) {
            e.printStackTrace();
        }




        RunAroundState state = new RunAroundState(2);
        Position pos=new Position(1,1);
        game.getPlayerList().getPlayer("Alex").setPosition(pos);


        state.handleVce(vce);


        assertEquals(ModelGate.getModel().getPlayerList().getPlayer("Alex").getPosition().getX(), vce.getX());
        assertEquals(ModelGate.getModel().getPlayerList().getPlayer("Alex").getPosition().getY(),vce.getY() );




    }
}
