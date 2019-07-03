package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateDrawAndDiscardPowerUp;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertEquals;

/**tests the functions that implements game logic in GrabStuffStateDrawAndDiscardPowerUp
 */
public class TestGrabStuffAndDiscardPowerUp{


        /**since the functions are privates, they are tested with reflection
         * @throws IOException may occur while creating the fake model
         * @throws NoSuchMethodException may occur while trying to access by reflection
         * @throws InvocationTargetException may occur while trying to invoke a private function
         * @throws IllegalAccessException
         * may occur while trying to invoke a private function
         * */
        @Test
        public void testGrabStaffAndDiscardPowerUp() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

            Game game = new FakeModel().create();
            ModelGate.setModel(game);

            game.buildDecks();


            ViewControllerEventInt viewControllerEventInt=new ViewControllerEventInt(0);
            GrabStuffStateDrawAndDiscardPowerUp state= new GrabStuffStateDrawAndDiscardPowerUp(1);

            java.lang.reflect.Method drawANewPowerUp= GrabStuffStateDrawAndDiscardPowerUp.class.getDeclaredMethod("drawANewPowerUp", Player.class);
            drawANewPowerUp.setAccessible(true);
            java.lang.reflect.Method discardPowerUp=GrabStuffStateDrawAndDiscardPowerUp.class.getDeclaredMethod("discardPowerUp", ViewControllerEvent.class);
            discardPowerUp.setAccessible(true);


            drawANewPowerUp.invoke(state,game.getCurrentPlayingPlayer());
            discardPowerUp.invoke(state,viewControllerEventInt);

            assertEquals(0,game.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size());
        }



}
