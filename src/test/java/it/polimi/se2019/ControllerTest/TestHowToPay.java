package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import org.junit.Test;
import java.io.IOException;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestHowToPay {

    @Test
    public void chooseHowToPay() throws IOException {
        ModelGate.model=(new FakeModel()).create();
        Game game=ModelGate.model;
        AmmoList ammoList=new AmmoList();
        ammoList.addAmmoCubesOfColor(AmmoCubesColor.blue, 3);
        game.getCurrentPlayingPlayer().addAmmoCubes(AmmoCubesColor.blue, 3);

        ChooseHowToPayState state=new ChooseHowToPayState(game.getCurrentPlayingPlayer(),ammoList);

        assertTrue(state.canPayInSomeWay());
        assertFalse(state.canPaySomethingWithPowerUps());


    }
}
