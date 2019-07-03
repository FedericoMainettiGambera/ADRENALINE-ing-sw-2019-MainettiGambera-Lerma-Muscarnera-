package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FFSetUpState;
import it.polimi.se2019.model.Game;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertEquals;

/**tests the functions that implements game logic in FFSetUpState*/
public class TestFFSetUpState{


    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void testFFSetUpState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Game game = (new FakeModel()).create();
        ModelGate.setModel(game);

       game.getPlayerList().setStartingPlayer( game.getPlayerList().getPlayer("Alex"));

        FFSetUpState state=new FFSetUpState();

        java.lang.reflect.Method prepareForFF= FFSetUpState.class.getDeclaredMethod("prepareForFF");
        prepareForFF.setAccessible(true);


        prepareForFF.invoke(state);

        assertEquals(0, game.getPlayerList().getPlayer("Alex").getBeforeorafterStartingPlayer());



    }
}
