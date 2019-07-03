package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.controller.statePattern.PowerUpAskForInputState;
import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.SquareV;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**this class tests the powerUpAskForInputTest game logic*/
public class PowerUpAskForInputStateTest{
/**since the functions are privates, they are tested with reflection
 * @throws IOException may occur while creating the fake model
 * @throws NoSuchMethodException may occur while trying to access by reflection
 * @throws InvocationTargetException may occur while trying to invoke a private function
 * @throws IllegalAccessException
 * may occur while trying to invoke a private function
 * */
    @Test
    public void inputPowerUpTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Game game=FakeModel.getFakeModel();
        ModelGate.setModel(game);
        game.buildDecks();
        PowerUpCard powerUpCard=game.getPowerUpDeck().getCard("1");


        List<Object> squares= new ArrayList<>();
        SquareV squareV=new SquareV();
        squareV.setX(0);
        squareV.setY(0);
        SquareV squareV1=new SquareV();
        squareV.setX(0);
        squareV.setY(1);
        squares.add(squareV);
        squares.add(squareV1);



        game.setBoard(new Board("map0", null, null));

        Player player=new Player();
        player.setPosition(0,0);
        Player player2=new Player();
        player2.setPosition(0,1);



        ViewControllerEvent viewControllerEvent=new ViewControllerEventListOfObject(squares);

        PowerUpAskForInputState state=new PowerUpAskForInputState(new FinalScoringState(), powerUpCard);

        java.lang.reflect.Method isToSend= PowerUpAskForInputState.class.getDeclaredMethod("isToSend", EffectInfoType.class);
        isToSend.setAccessible(true);
        java.lang.reflect.Method parseInput=PowerUpAskForInputState.class.getDeclaredMethod("parseInput", ViewControllerEvent.class);
        parseInput.setAccessible(true);
        java.lang.reflect.Method canIncrementRequest=PowerUpAskForInputState.class.getDeclaredMethod("canIncrementRequest");
        canIncrementRequest.setAccessible(true);

        System.out.println(powerUpCard.getID()+powerUpCard.getName());

        parseInput.invoke(state, viewControllerEvent);

        assertTrue((boolean)isToSend.invoke(state, powerUpCard.getSpecialEffect().getEffectInfo().getEffectInfoElement().get(0).getEffectInfoTypelist()));
        canIncrementRequest.invoke(state);




    }
}
