package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameSetUpStateTest{


        @Test
        public void testGameSetUpState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

            Game game = FakeModel.getFakeModel();
            ModelGate.setModel(game);

           ViewControllerEventGameSetUp viewControllerEventGameSetUp= new ViewControllerEventGameSetUp("normalMode", "map0", 5,false,false);

            GameSetUpState state=new GameSetUpState();


            java.lang.reflect.Method gameHasBegun= GameSetUpState.class.getDeclaredMethod("gameHasBegun", Player.class);
            gameHasBegun.setAccessible(true);
            java.lang.reflect.Method gameSetUp=GameSetUpState.class.getDeclaredMethod("gameSetUp", ViewControllerEvent.class);
            gameSetUp.setAccessible(true);
            java.lang.reflect.Method createDecks=GameSetUpState.class.getDeclaredMethod("createDecks");
            createDecks.setAccessible(true);
            java.lang.reflect.Method preparePlayers=GameSetUpState.class.getDeclaredMethod("preparePlayers");
            preparePlayers.setAccessible(true);

            gameHasBegun.invoke(state,game.getCurrentPlayingPlayer());
            gameSetUp.invoke(state,viewControllerEventGameSetUp);
            createDecks.invoke(state);
            preparePlayers.invoke(state);

            assertEquals(5, ModelGate.getModel().getKillshotTrack().getNumberOfRemainingSkulls());
            assertEquals('B',ModelGate.getModel().getBoard().getSquare(0,0).getColor());
            assertFalse(ModelGate.getModel().isFinalFrenzy());
            assertFalse(ModelGate.getModel().isBotActive());



        }
}
