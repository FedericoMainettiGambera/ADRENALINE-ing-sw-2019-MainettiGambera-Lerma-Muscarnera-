package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.view.components.GameV;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;


/**this class verify that some of the functions that didn't get tested somewhere else  work properly*/
public class GameTest{

    @Test
    public void gameTest(){


        Game game=new Game();

        game.setVirtualView(null, null);
        GameV gameV=game.buildGameV();

        assertNull(game.getRMIVirtualView());
        assertNull(game.getSocketVirtualView());

        assertFalse(gameV.isHasFinalFrenzyBegun());
        assertNull(gameV.getKillshotTrack());


    }
}
