package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;

import it.polimi.se2019.controller.statePattern.GrabStuffStateGrab;
import it.polimi.se2019.controller.statePattern.State;
import it.polimi.se2019.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static it.polimi.se2019.controller.ModelGate.getModel;
import static junit.framework.TestCase.assertTrue;

/**tests the functions that implements game logic in GrabStuffStateGrab*/
public class GrabStuffStateGrabTest{

    private FakeModel fakeModel = new FakeModel();

    /**since the functions are privates, they are tested with reflection
     * @throws IOException may occur while creating the fake model
     * @throws NoSuchMethodException may occur while trying to access by reflection
     * @throws InvocationTargetException may occur while trying to invoke a private function
     * @throws IllegalAccessException
     * may occur while trying to invoke a private function
     * */
    @Test
    public void testGrabStuffStateGrab() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ModelGate.setModel(fakeModel.create()) ;
        ModelGate.getModel().buildDecks();

        ModelGate.getModel().getPlayerList().getCurrentPlayingPlayer().setPosition(0,0);


        AmmoCard ammo=new AmmoCard("0");

        ((NormalSquare) getModel().getBoard().getSquare(0,0)).getAmmoCards().addCard(ammo);





        GrabStuffStateGrab state = new GrabStuffStateGrab(1);

        java.lang.reflect.Method playerInNormalSquare= GrabStuffStateGrab.class.getDeclaredMethod("playerInNormalSquare");
        playerInNormalSquare.setAccessible(true);


        playerInNormalSquare.invoke(state);
        GrabStuffStateGrab state2 = new GrabStuffStateGrab(2);
        playerInNormalSquare.invoke(state2);

        Assertions.assertTrue(getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().isEmpty());

        AmmoCard ammoCard=new AmmoCard("0");
        for(int i=0; i<36; i++) {
            ammoCard = new AmmoCard(String.valueOf(i));
            if( ammoCard.isPowerUp()) {
                break;
            }
        }

        ((NormalSquare)(getModel().getBoard().getSquare(0,0))).getAmmoCards().addCard(ammoCard);

        ModelGate.getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().addCard(new PowerUpCard());
        State stet=(State)playerInNormalSquare.invoke(state);

        assertTrue(stet.toString().contains("GrabStuffStateDrawPowerUp"));
        System.out.println(stet.toString());
        playerInNormalSquare.invoke(state2);


        ((NormalSquare)(getModel().getBoard().getSquare(0,0))).getAmmoCards().addCard(ammoCard);
        ModelGate.getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().addCard(new PowerUpCard());


        playerInNormalSquare.invoke(state);
        playerInNormalSquare.invoke(state2);



        state.askForInput(getModel().getCurrentPlayingPlayer());




    }

}
