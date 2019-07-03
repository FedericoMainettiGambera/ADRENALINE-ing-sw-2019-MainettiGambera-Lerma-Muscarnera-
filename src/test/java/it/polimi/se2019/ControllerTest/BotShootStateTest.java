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


    @Test
    public void TestBotShootState() throws IOException {

        ModelGate.setModel( FakeModel.getFakeModel());

        ModelGate.getModel().getPlayerList().addPlayer(new Player(true));
        ModelGate.getModel().getPlayerList().getPlayer("Alex").setPosition(0,0);
        ModelGate.getModel().getPlayerList().getPlayer("Terminator").setPosition(0, 1);

        BotShootState state=new BotShootState(new FinalScoringState());
        ViewControllerEventString viewControllerEvent=new ViewControllerEventString("Alex");

        state.setPlayerToAsk(ModelGate.getModel().getCurrentPlayingPlayer());
        state.playersCanBotSee();
        state.parseVce(viewControllerEvent);

        assertEquals("Terminator", ModelGate.getModel().getCurrentPlayingPlayer().getDamageSlot(0).getShootingPlayer().getNickname());

        ModelGate.getModel().getPlayerList().getPlayer("Terminator").addDamages(ModelGate.getModel().getCurrentPlayingPlayer(), 10);

        state.setPlayerToAsk(ModelGate.getModel().getCurrentPlayingPlayer());
        state.playersCanBotSee();
        state.parseVce(viewControllerEvent);

        assertEquals(1, ModelGate.getModel().getCurrentPlayingPlayer().getMarksFrom(ModelGate.getModel().getPlayerList().getPlayer("Terminator")));
        assertTrue(ModelGate.getModel().getPlayerList().getPlayer("Terminator").isBotUsed());
    }

}