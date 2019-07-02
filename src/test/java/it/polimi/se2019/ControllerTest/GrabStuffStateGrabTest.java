package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateGrab;
import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.NormalSquare;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

import static it.polimi.se2019.controller.ModelGate.getModel;
import static it.polimi.se2019.controller.ModelGate.model;

public class GrabStuffStateGrabTest{

    private FakeModel fakeModel = new FakeModel();

    @Test
    public void testGrabStuffStateGrab() throws IOException {

        ModelGate.setModel(fakeModel.create()) ;
        ModelGate.getModel().buildDecks();

        ModelGate.getModel().getPlayerList().getCurrentPlayingPlayer().setPosition(0,0);


        AmmoCard ammo=new AmmoCard("0");

        ((NormalSquare) getModel().getBoard().getSquare(0,0)).getAmmoCards().addCard(ammo);




        GrabStuffStateGrab state = new GrabStuffStateGrab(1);

        state.grabAmmo();

        state.askForInput(getModel().getCurrentPlayingPlayer());

        Assertions.assertTrue(getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().isEmpty());


    }

}
