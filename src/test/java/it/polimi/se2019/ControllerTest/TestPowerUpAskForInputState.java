package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.PowerUpAskForInputState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.PowerUpCard;
import org.junit.Test;

public class TestPowerUpAskForInputState {
    private FakeModel fakeModel = new FakeModel();

    @Test
    public void testBase() throws Exception {

        Game game = fakeModel.create();
        ModelGate.setModel(game);

        game.buildDecks();
        PowerUpCard p = game.getPowerUpDeck().getCard("10");

        PowerUpAskForInputState state = new PowerUpAskForInputState(
                null
                , p
        );

        /* [    elenco dei metodi privati   ] */
       // java.lang.reflect.Method bubbleSort = PowerUpAskForInputState.class.getDeclaredMethod("bubbleSort");
       // bubbleSort.setAccessible(true);
        /* [/   elenco dei metodi privati   ] */

//        state.askForInput(
    //            game.getPlayerList().getPlayer("Alex")
   //     );

      //  game.getPlayerList().getPlayer("C").addDamages( game.getPlayerList().getPlayer("Alex"), 8);



       // assertEquals(26, game.getPlayerList().getPlayer("Alex").getScore());


        return;
    }
}

