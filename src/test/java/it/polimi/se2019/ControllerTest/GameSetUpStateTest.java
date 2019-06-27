package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventGameSetUp;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;
import java.io.IOException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSetUpStateTest{


        private Game game = new Game();
        private FakeModel fakeModel = new FakeModel();

        @Test
        public void testGameSetUpState() throws IOException {

            Game game = fakeModel.create();
            ModelGate.model = game;

           ViewControllerEventGameSetUp viewControllerEventGameSetUp= new ViewControllerEventGameSetUp("normalMode", "map0", 5,false,false);

            GameSetUpState state=new GameSetUpState();

            state.gameHasBegun(game.getCurrentPlayingPlayer());

            state.gameSetUp(viewControllerEventGameSetUp);
            state.createDecks();
            state.preparePlayers();

            assertEquals(5, ModelGate.model.getKillshotTrack().getNumberOfRemainingSkulls());
            assertEquals('B',ModelGate.model.getBoard().getSquare(0,0).getColor());
            assertEquals(false, ModelGate.model.isFinalFrenzy());
            assertEquals(false, ModelGate.model.isBotActive());



        }
}
