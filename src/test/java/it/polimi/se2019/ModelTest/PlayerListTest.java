package it.polimi.se2019.ModelTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerListTest {


    @Test
    public void testPlayerList(){

        PlayersList playersList= new PlayersList();


        //test setAll
        List<Object> playersListFake=new ArrayList<>();

        Player A=new Player();
        Player B=new Player();
        Player C=new Player();

        A.setNickname("A");
        B.setNickname("B");
        C.setNickname("C");

        playersListFake.add(A);
        playersListFake.add(B);
        playersListFake.add(C);

        playersList.setAll(playersListFake);

        assertEquals(A,playersList.getPlayer("A"));

        //test setNextPlayingPlayer
        playersList.setCurrentPlayingPlayer(A);
        playersList.setNextPlayingPlayer();

       // assertEquals(B,playersList.getCurrentPlayingPlayer());

        ModelGate.getModel().setPlayerList(playersList);

        //AFK and nextPlaying player
       // B.setAFKWIthoutNotify(true);


        playersList.setCurrentPlayingPlayer(A);
        playersList.setNextPlayingPlayer();

      //  assertEquals(C,playersList.getCurrentPlayingPlayer());
        assertFalse(playersList.isMinimumPlayerNotAFK());

        assertFalse(playersList.areAllAFK());
//        assertTrue(playersList.isSomeoneAFK());


    }
}
