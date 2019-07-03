package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.GrabStuffStateGrabWeapon;
import it.polimi.se2019.model.SpawnPointSquare;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGrabWep{

    @Test
    public void grabWepTest() throws IOException {

        ModelGate.setModel(new FakeModel().create());



        ModelGate.getModel().buildDecks();
        ModelGate.getModel().getWeaponDeck().shuffle();

        List<WeaponCard> weaponCards=new ArrayList<>();
        weaponCards.add(ModelGate.getModel().getWeaponDeck().getCards().get(0));
        weaponCards.add(ModelGate.getModel().getWeaponDeck().getCards().get(1));

        GrabStuffStateGrabWeapon state=new GrabStuffStateGrabWeapon(1);
        state.printList(weaponCards);

        ModelGate.getModel().getPlayerList().setCurrentPlayingPlayer(ModelGate.getModel().getPlayerList().getPlayer("Alex"));

        ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.getModel().getWeaponDeck().getCards().get(0));
        ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.getModel().getWeaponDeck().getCards().get(1));


        ModelGate.getModel().getCurrentPlayingPlayer().setPosition(0,2);
        ((SpawnPointSquare) ModelGate.getModel().getBoard().getSquare(0,2)).getWeaponCards().addCard(ModelGate.getModel().getWeaponDeck().getCards().get(0));
        ((SpawnPointSquare) ModelGate.getModel().getBoard().getSquare(0,2)).getWeaponCards().addCard(ModelGate.getModel().getWeaponDeck().getCards().get(1));
        ViewControllerEvent viewControllerEvent1 = new ViewControllerEventString(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID());

        state.switchCardsOrJustPickUpOne(viewControllerEvent1);




        ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.getModel().getWeaponDeck().getFirstCard());
        ViewControllerEvent viewControllerEvent= new ViewControllerEventTwoString(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID(), ModelGate.getModel().getWeaponDeck().getCards().get(0).getID());

        state.switchCardsOrJustPickUpOne(viewControllerEvent);

        assertEquals(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID(),ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID()).getID());
        assertEquals(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID(),ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(ModelGate.getModel().getWeaponDeck().getCards().get(0).getID()).getID());


    }
}
