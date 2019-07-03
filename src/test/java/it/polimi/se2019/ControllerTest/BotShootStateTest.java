package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.BotShootState;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**tests the functions that implements game logic in BotShootState*/
public class BotShootStateTest {


    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void TestBotShootState() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel( FakeModel.getFakeModel());

        ModelGate.getModel().getPlayerList().addPlayer(new Player(true));
        ModelGate.getModel().getPlayerList().getPlayer("Alex").setPosition(0,0);
        ModelGate.getModel().getPlayerList().getPlayer("Terminator").setPosition(0, 1);

        BotShootState state=new BotShootState(new FinalScoringState());
        ViewControllerEventString viewControllerEvent=new ViewControllerEventString("Alex");

        java.lang.reflect.Method setPlayerToAsk=BotShootState.class.getDeclaredMethod("setPlayerToAsk", Player.class);
        setPlayerToAsk.setAccessible(true);
        java.lang.reflect.Method playersCanBotSee=BotShootState.class.getDeclaredMethod("playersCanBotSee");
        playersCanBotSee.setAccessible(true);
        java.lang.reflect.Method parseVce=BotShootState.class.getDeclaredMethod("parseVce", ViewControllerEvent.class);
        parseVce.setAccessible(true);



        setPlayerToAsk.invoke(state,ModelGate.getModel().getCurrentPlayingPlayer());
        playersCanBotSee.invoke(state);
        parseVce.invoke(state,viewControllerEvent);

        assertEquals("Terminator", ModelGate.getModel().getCurrentPlayingPlayer().getDamageSlot(0).getShootingPlayer().getNickname());

        ModelGate.getModel().getPlayerList().getPlayer("Terminator").addDamages(ModelGate.getModel().getCurrentPlayingPlayer(), 10);

        parseVce.invoke(state,viewControllerEvent);

        assertEquals(1, ModelGate.getModel().getCurrentPlayingPlayer().getMarksFrom(ModelGate.getModel().getPlayerList().getPlayer("Terminator")));
        assertTrue(ModelGate.getModel().getPlayerList().getPlayer("Terminator").isBotUsed());
    }

}