package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateDrawPowerUp;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**this state implements a test to verify that the functions of GrabStuffAndDrawPowerUp work properly*/
public class GrabStuffAndDrawPowerUp{

    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void grabStuffAndDrawPowerUp() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel(FakeModel.getFakeModel());
        Game game=ModelGate.getModel();
        game.buildDecks();

        GrabStuffStateDrawPowerUp state=new  GrabStuffStateDrawPowerUp(2);

        java.lang.reflect.Method makeThePlayerDraw= GrabStuffStateDrawPowerUp.class.getDeclaredMethod("makeThePlayerDraw");
        makeThePlayerDraw.setAccessible(true);
        java.lang.reflect.Method setNextState= GrabStuffStateDrawPowerUp.class.getDeclaredMethod("setNextState");
        setNextState.setAccessible(true);
        java.lang.reflect.Method askForInput= GrabStuffStateDrawPowerUp.class.getDeclaredMethod("askForInput", Player.class);
        askForInput.setAccessible(true);

        makeThePlayerDraw.invoke(state);
        setNextState.invoke(state);
        askForInput.invoke(state, game.getCurrentPlayingPlayer());

        assertEquals(1,game.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size());

    }
}
