package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ReloadState;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReload {


   /**we test the reload state class in the state pattern*/

/*
        FakeModel fakeModel=new FakeModel();

        @Test
        public void testReloadState() throws IOException {

            ModelGate.setModel(fakeModel.create());
            ModelGate.getModel().buildDecks();

            ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().add(ModelGate.getModel().getWeaponDeck().getFirstCard());
            ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards().get(0).unload();
            ModelGate.getModel().getCurrentPlayingPlayer().addAmmoCubes(AmmoCubesColor.red, 3);
            ModelGate.getModel().getCurrentPlayingPlayer().addAmmoCubes(AmmoCubesColor.yellow, 3);
            ModelGate.getModel().getCurrentPlayingPlayer().addAmmoCubes(AmmoCubesColor.blue, 3);

            ReloadState reloadState=new ReloadState(false);

            reloadState.setPlayerToAsk(ModelGate.getModel().getCurrentPlayingPlayer());
            reloadState.placeAmmosOnEmptyNormalSquares();
            assertEquals(1,reloadState.weaponToReload().size());
            assertTrue(reloadState.canReload());


        }
*/
    }

