package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateDrawAndDiscardPowerUp;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventInt;
import org.junit.Test;
import java.io.IOException;
import static junit.framework.TestCase.assertEquals;

public class TestGrabStuffAndDiscardPowerUp{


        private FakeModel fakeModel=new FakeModel();

        @Test
        public void testGrabStaffAndDiscardPowerUp() throws IOException {

            Game game = fakeModel.create();
            ModelGate.setModel(game);

            game.buildDecks();


            ViewControllerEventInt viewControllerEventInt=new ViewControllerEventInt(0);
            GrabStuffStateDrawAndDiscardPowerUp state= new GrabStuffStateDrawAndDiscardPowerUp(1);
            state.drawANewPowerUp(game.getCurrentPlayingPlayer());
            state.discardPowerUp(viewControllerEventInt);

            assertEquals(0,game.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size());
        }



}
