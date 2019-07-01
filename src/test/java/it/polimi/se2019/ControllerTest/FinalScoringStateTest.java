package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.Game;

import it.polimi.se2019.model.KillShotTrack;
import it.polimi.se2019.model.Player;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertEquals;


public class FinalScoringStateTest {

    private FakeModel fakeModel = new FakeModel();

    @Test
    public void TestFinalScoringState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    Game game = fakeModel.create();
    ModelGate.model=game;

    game.setKillshotTrack(new KillShotTrack(5,null,null));
    game.getKillshotTrack().deathOfPlayer(game.getCurrentPlayingPlayer(), true);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("B"), false);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("C"), true);
    game.getKillshotTrack().deathOfPlayer(game.getPlayerList().getPlayer("Alex"), true);

   FinalScoringState state = new FinalScoringState();

   java.lang.reflect.Method bubbleSort=FinalScoringState.class.getDeclaredMethod("bubbleSort");
   bubbleSort.setAccessible(true);
   java.lang.reflect.Method scoreTokens=FinalScoringState.class.getDeclaredMethod("scoreTokens", Player.class);
   scoreTokens.setAccessible(true);
   java.lang.reflect.Method setPoints=FinalScoringState.class.getDeclaredMethod("setPoints", int.class);
   setPoints.setAccessible(true);
   java.lang.reflect.Method getGraduatory=FinalScoringState.class.getDeclaredMethod("getGraduatory");
   getGraduatory.setAccessible(true);


   game.getPlayerList().getPlayer("Alex").setScore(18);
   game.getPlayerList().getPlayer("B").setScore(2);
   game.getPlayerList().getPlayer("C").setScore(49);

   game.getPlayerList().getPlayer("C").addDamages( game.getPlayerList().getPlayer("Alex"), 8);



   bubbleSort.invoke(state);
   scoreTokens.invoke(state, game.getPlayerList().getPlayer("C") );
   setPoints.invoke(state,8);
   getGraduatory.invoke(state);



   assertEquals(26, game.getPlayerList().getPlayer("Alex").getScore());


}
}
