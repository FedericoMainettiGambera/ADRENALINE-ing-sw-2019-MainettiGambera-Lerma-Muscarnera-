package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateMove;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * tests the functions that implements game logic in GrabStuffStateMove
 * those are calculateAndPrintPossiblePosition and parsevce
 * */
public class GrabStuffMoveTest{

    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void testGrabStuffMoveState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ModelGate.setModel(FakeModel.getFakeModel());
        Game game=ModelGate.getModel();

        GrabStuffStateMove state=new GrabStuffStateMove(1);

        game.getCurrentPlayingPlayer().setPosition(0,0);

        ViewControllerEventPosition viewControllerEvent=new ViewControllerEventPosition(0,1);

        java.lang.reflect.Method calculateAndPrintPossiblePosition= GrabStuffStateMove.class.getDeclaredMethod("calculateAndPrintPossiblePosition", Player.class);
        calculateAndPrintPossiblePosition.setAccessible(true);
        java.lang.reflect.Method parsevce=GrabStuffStateMove.class.getDeclaredMethod("parsevce", ViewControllerEvent.class);
        parsevce.setAccessible(true);


        calculateAndPrintPossiblePosition.invoke(state,game.getCurrentPlayingPlayer());
        parsevce.invoke(state,viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());

        game.getCurrentPlayingPlayer().addDamages(game.getPlayerList().getPlayer("B"), 10);

        calculateAndPrintPossiblePosition.invoke(state,game.getCurrentPlayingPlayer());
        parsevce.invoke(state,viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());

        game.setFinalFrenzy(true);
        game.getCurrentPlayingPlayer().setBeforeorafterStartingPlayer(4);

        calculateAndPrintPossiblePosition.invoke(state,game.getCurrentPlayingPlayer());
        parsevce.invoke(state,viewControllerEvent);

        assertEquals(viewControllerEvent.getX(),game.getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(viewControllerEvent.getY(), game.getCurrentPlayingPlayer().getPosition().getY());



    }
}
