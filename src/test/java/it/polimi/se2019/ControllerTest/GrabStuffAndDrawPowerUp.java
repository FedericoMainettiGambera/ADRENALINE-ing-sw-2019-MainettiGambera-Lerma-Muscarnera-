package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateDrawPowerUp;
import it.polimi.se2019.model.Game;
import org.junit.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrabStuffAndDrawPowerUp{

    @Test
    public void grabStuffAndDrawPowerUp() throws IOException {

        ModelGate.setModel(FakeModel.getFakeModel());
        Game game=ModelGate.getModel();
        game.buildDecks();

        GrabStuffStateDrawPowerUp state=new  GrabStuffStateDrawPowerUp(2);

        state.askForInput(game.getCurrentPlayingPlayer());
        state.makeThePlayerDraw();

        assertEquals(1,game.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size());

    }
}
