package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.BotShootState;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BotShootStateTest {

    private FakeModel fakeModel = new FakeModel();

    @Test
    public void TestBotShootState() throws IOException {

        ModelGate.model = fakeModel.create();

        ModelGate.model.getPlayerList().addPlayer(new Player(true));
        ModelGate.model.getPlayerList().getPlayer("Alex").setPosition(0,0);
        ModelGate.model.getPlayerList().getPlayer("Terminator").setPosition(0, 1);

        BotShootState state=new BotShootState(new FinalScoringState());
        ViewControllerEventString viewControllerEvent=new ViewControllerEventString("Alex");

        state.setPlayerToAsk(ModelGate.model.getCurrentPlayingPlayer());
        state.playersCanBotSee();
        state.parseVce(viewControllerEvent);

        assertEquals("Terminator", ModelGate.model.getCurrentPlayingPlayer().getDamageSlot(0).getShootingPlayer().getNickname());

        ModelGate.model.getPlayerList().getPlayer("Terminator").addDamages(ModelGate.model.getCurrentPlayingPlayer(), 10);

        state.setPlayerToAsk(ModelGate.model.getCurrentPlayingPlayer());
        state.playersCanBotSee();
        state.parseVce(viewControllerEvent);

        assertEquals(1, ModelGate.model.getCurrentPlayingPlayer().getMarksFrom(ModelGate.model.getPlayerList().getPlayer("Terminator")));
        assertTrue(ModelGate.model.getPlayerList().getPlayer("Terminator").isBotUsed());
    }

}