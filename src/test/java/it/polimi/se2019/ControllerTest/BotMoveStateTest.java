package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.BotMoveState;
import it.polimi.se2019.controller.statePattern.FFSetUpState;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**tests the functions that implements gamelogic in BotMoveState*/
public class BotMoveStateTest {

    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void TestBotMoveStateTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel(FakeModel.getFakeModel()) ;
        ModelGate.getModel().getPlayerList().addPlayer(new Player(true));
        ModelGate.getModel().getPlayerList().getPlayer("Terminator").setPosition(0,1);

        BotMoveState state=new BotMoveState(new FFSetUpState());

        ViewControllerEventPosition viewControllerEventPosition=new ViewControllerEventPosition(0,0);

        java.lang.reflect.Method calculatePossiblePositions= BotMoveState.class.getDeclaredMethod("calculatePossiblePositions", Player.class);
        calculatePossiblePositions.setAccessible(true);

        java.lang.reflect.Method parseVce=BotMoveState.class.getDeclaredMethod("parseVce", ViewControllerEvent.class);
        parseVce.setAccessible(true);



        parseVce.invoke(state,viewControllerEventPosition);

        calculatePossiblePositions.invoke(state, ModelGate.getModel().getCurrentPlayingPlayer());
        Position pos=new Position(0,0);

        assertEquals(pos.getX(), ModelGate.getModel().getPlayerList().getPlayer("Terminator").getPosition().getX());
        assertEquals(pos.getY(), ModelGate.getModel().getPlayerList().getPlayer("Terminator").getPosition().getY());



    }

}
