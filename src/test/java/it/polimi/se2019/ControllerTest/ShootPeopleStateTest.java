package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.ShootPeopleState;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPosition;
import org.junit.Test;


import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**tests the method from shoot people that don't implement functions that are network related
 * in the specific, setNewPositionForPlayer is here verified
 * as well as possiblePositionsToShootFrom
 * the excepted behaviour is a long list of statement from the server, which is analysing
 * the possible usage of weapons
 * here some of the remaining uncovered lines from the class PlayerHand are tested
 * such as getHand, getNickName, getWeaponCardsInHand and setNickname*/
public class ShootPeopleStateTest {

    @Test
    public void shootPeopleStateTest() throws IOException {

        ModelGate.model=(new FakeModel()).create();
        Game game=ModelGate.model;


        game.getCurrentPlayingPlayer().setPosition(0,0);
        ViewControllerEventPosition position=new ViewControllerEventPosition(1,1);
        game.getPlayerList().getPlayer("B").setPosition(1,1);



        ShootPeopleState shootPeopleState=new ShootPeopleState(1);
        shootPeopleState.setNewPositionForPlayer(position);
        List<Position> positions=shootPeopleState.possiblePositionsToShootFrom(3,game.getCurrentPlayingPlayer());


        assertTrue(positions.isEmpty());

        game.buildDecks();

        game.getCurrentPlayingPlayer().getWeaponCardsInHand().addCard(game.getWeaponDeck().getCards().get(0));

        shootPeopleState.setNewPositionForPlayer(position);
        List<Position> positions1=shootPeopleState.possiblePositionsToShootFrom(3,game.getCurrentPlayingPlayer());




        PlayerHand playerHand=new PlayerHand();
        OrderedCardList<WeaponCard> weaponCardOrderedCardList=game.getCurrentPlayingPlayer().getHand().getWeaponCards();
        assertEquals(weaponCardOrderedCardList,game.getCurrentPlayingPlayer().getHand().getWeaponCards());

        playerHand.setNickname(game.getCurrentPlayingPlayer().getNickname());
        assertEquals("Alex",playerHand.getNickname());

        PowerUpCard powerUpCard=new PowerUpCard();
        game.getCurrentPlayingPlayer().getHand().getPowerUpCards().addCard(powerUpCard);
        assertEquals(1, game.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size());




    }


}
