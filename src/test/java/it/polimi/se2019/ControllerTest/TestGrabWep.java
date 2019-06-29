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

        ModelGate.model=(new FakeModel()).create();



        ModelGate.model.buildDecks();
        ModelGate.model.getWeaponDeck().shuffle();

        List<WeaponCard> weaponCards=new ArrayList<>();
        weaponCards.add(ModelGate.model.getWeaponDeck().getCards().get(0));
        weaponCards.add(ModelGate.model.getWeaponDeck().getCards().get(1));

        GrabStuffStateGrabWeapon state=new GrabStuffStateGrabWeapon(1);
        state.printList(weaponCards);

        ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getPlayer("Alex"));

        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.model.getWeaponDeck().getCards().get(0));
        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.model.getWeaponDeck().getCards().get(1));


        ModelGate.model.getCurrentPlayingPlayer().setPosition(0,2);
        ((SpawnPointSquare) ModelGate.model.getBoard().getSquare(0,2)).getWeaponCards().addCard(ModelGate.model.getWeaponDeck().getCards().get(0));
        ((SpawnPointSquare) ModelGate.model.getBoard().getSquare(0,2)).getWeaponCards().addCard(ModelGate.model.getWeaponDeck().getCards().get(1));
        ViewControllerEvent viewControllerEvent1 = new ViewControllerEventString(ModelGate.model.getWeaponDeck().getCards().get(0).getID());

        state.switchCardsOrJustPickUpOne(viewControllerEvent1);




        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(ModelGate.model.getWeaponDeck().getFirstCard());
        ViewControllerEvent viewControllerEvent= new ViewControllerEventTwoString(ModelGate.model.getWeaponDeck().getCards().get(0).getID(), ModelGate.model.getWeaponDeck().getCards().get(0).getID());

        state.switchCardsOrJustPickUpOne(viewControllerEvent);

        assertEquals(ModelGate.model.getWeaponDeck().getCards().get(0).getID(),ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(ModelGate.model.getWeaponDeck().getCards().get(0).getID()).getID());
        assertEquals(ModelGate.model.getWeaponDeck().getCards().get(0).getID(),ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(ModelGate.model.getWeaponDeck().getCards().get(0).getID()).getID());


    }
}
