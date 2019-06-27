package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.Game;
;
import it.polimi.se2019.model.KillShotTrack;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;


public class FinalScoringStateTest {
    private Game game = new Game();
    private FakeModel fakeModel = new FakeModel();

    @Test
    public void TestFinalScoringState() throws IOException {

    Game game = fakeModel.create();
    ModelGate.model=game;

    game.setKillshotTrack(new KillShotTrack(5,null,null));
    game.getKillshotTrack().deathOfPlayer(game.getCurrentPlayingPlayer(), true);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("B"), false);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("C"), true);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("Alex"), true);

   FinalScoringState state = new FinalScoringState();
   ViewControllerEvent viewControllerEvent=null;

   game.getPlayerList().getPlayer("Alex").setScore(18);
   game.getPlayerList().getPlayer("B").setScore(2);
   game.getPlayerList().getPlayer("C").setScore(49);

   game.getPlayerList().getPlayer("C").addDamages( game.getPlayerList().getPlayer("Alex"), 8);



   state.bubbleSort();
   state.scoreTokens( game.getPlayerList().getPlayer("C"));
   state.setPoints(8);
   state.getGraduatory();

   assertEquals(26, game.getPlayerList().getPlayer("Alex").getScore());


}
}
