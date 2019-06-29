package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateMove;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrabStuffMoveTest{

    @Test
    public void testGrabStuffMoveState() throws IOException {
        ModelGate.model=(new FakeModel()).create();
        Game game=ModelGate.model;

        GrabStuffStateMove state=new GrabStuffStateMove(1);

        game.getCurrentPlayingPlayer().setPosition(0,0);

        ViewControllerEventPosition viewControllerEvent=new ViewControllerEventPosition(0,1);

        state.calculateAndPrintPossiblePosition(game.getCurrentPlayingPlayer());
        state.parsevce(viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());

        game.getCurrentPlayingPlayer().addDamages(game.getPlayerList().getPlayer("B"), 10);

        state.calculateAndPrintPossiblePosition(game.getCurrentPlayingPlayer());
        state.parsevce(viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());

        game.setFinalFrenzy(true);
        game.getCurrentPlayingPlayer().setBeforeorafterStartingPlayer(4);

        state.calculateAndPrintPossiblePosition(game.getCurrentPlayingPlayer());
        state.parsevce(viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());



    }
}
