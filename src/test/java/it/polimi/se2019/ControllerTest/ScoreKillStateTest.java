package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ScoreKillsState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.KillShotTrack;
import it.polimi.se2019.model.Player;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreKillStateTest{

    private Game game=new Game();
    private FakeModel fakeModel=new FakeModel();


    @Test
    public void testScoreKillStateTest() throws IOException {

        game=fakeModel.create();
        ModelGate.model=game;
        game.setKillshotTrack(new KillShotTrack(5, null, null));

        game.getPlayerList().addPlayer(new Player(true));

        game.getPlayerList().getPlayer("Alex").addDamages(game.getPlayerList().getPlayer("B"), 8);


        ScoreKillsState state=new ScoreKillsState();

       // assertEquals(game.getPlayerList().getPlayer("B").getScore(),9);

        game.getPlayerList().getPlayer("Alex").addDamages(game.getPlayerList().getPlayer("B"), 5);
        state.createDeadPlayersList();
        assertEquals(9, game.getPlayerList().getPlayer("B").getScore());
        state.setBotUsable();
        assertEquals(false, game.getPlayerList().getPlayer("Terminator").isBotUsed());


    }

}
