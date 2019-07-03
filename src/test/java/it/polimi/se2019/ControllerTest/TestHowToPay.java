package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**tests the functions that implements game logic in ChooseHowToPayState
 */
public class TestHowToPay {


    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void chooseHowToPay() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel(new FakeModel().create());
        Game game=ModelGate.getModel();
        AmmoList ammoList=new AmmoList();
        ammoList.addAmmoCubesOfColor(AmmoCubesColor.blue, 3);
        game.getCurrentPlayingPlayer().addAmmoCubes(AmmoCubesColor.blue, 3);

        ChooseHowToPayState state=new ChooseHowToPayState(game.getCurrentPlayingPlayer(),ammoList);

        java.lang.reflect.Method canPayInSomeWay= ChooseHowToPayState.class.getDeclaredMethod("canPayInSomeWay");
        canPayInSomeWay.setAccessible(true);
        java.lang.reflect.Method canPaySomethingWithPowerUps=ChooseHowToPayState.class.getDeclaredMethod("canPaySomethingWithPowerUps");
        canPaySomethingWithPowerUps.setAccessible(true);


        assertTrue((Boolean) canPayInSomeWay.invoke(state));
        assertFalse((Boolean) canPaySomethingWithPowerUps.invoke(state));


    }
}
