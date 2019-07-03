package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayerHistory;
import it.polimi.se2019.view.components.PlayerV;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.*;
/**test methods that other tests didn't cover from player class*/
public class TestPlayer {

    @Test
    public void testPlayer() throws IOException {

        ModelGate.setModel((new FakeModel()).create());

        ViewControllerEventHandlerContext.setRmiVirtualView(null);


        Player player = new Player();
        PlayerHistory playerHistory = new PlayerHistory(player);


        player.setPlayerHistory(playerHistory);
        player.setAFKWIthoutNotify(true);
        player.setAFKWithNotify(true);
        player.setAFKWIthoutNotify(false);
        player.setAFKWithNotify(false);
        player.setRmiInterface(null);
        player.setIP("100");
        player.setPosition(0,0);
        PlayerV playerV=player.buildPlayerV();




        assertNull(player.getRmiInterface());
        assertNull(player.getOos());
        assertEquals("100",player.getIp());
        assertFalse(player.isBot());
        player.setLastPlayingPlayer();

        assertEquals(playerHistory, player.getPlayerHistory());
        assertEquals(new Integer(0), playerV.getX());
        assertTrue(player.getLastPlayingPlayer());
        assertEquals(new Integer(0),playerV.getY());


    }
}
