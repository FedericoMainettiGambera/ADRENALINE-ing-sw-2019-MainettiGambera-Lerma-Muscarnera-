package it.polimi.se2019.ControllerTest;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.statePattern.FinalScoringState;
import it.polimi.se2019.controller.statePattern.FirstSpawnState;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.SpawnPointSquare;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventTwoString;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstSpawnTest{

    @Test
    public void firstSpawnTest() throws Exception {

        ModelGate.setModel((FakeModel.getFakeModel()));

        PowerUpCard powerUpCard=new PowerUpCard("1",1);
        powerUpCard.setColor(AmmoCubesColor.blue);

        ModelGate.getModel().getCurrentPlayingPlayer().getPowerUpCardsInHand().addCard(powerUpCard);
        ModelGate.getModel().getPlayerList().addPlayer(new Player(true));
        ViewControllerEventTwoString viewControllerEventTwoString=new ViewControllerEventTwoString("1", "red");

        FirstSpawnState firstSpawnState=new FirstSpawnState();
        java.lang.reflect.Method spawnBot= FirstSpawnState.class.getDeclaredMethod("spawnBot", ViewControllerEvent.class);
        spawnBot.setAccessible(true);
        java.lang.reflect.Method spawnPlayer=FirstSpawnState.class.getDeclaredMethod("spawnPlayer", ViewControllerEvent.class);
        spawnPlayer.setAccessible(true);


        spawnBot.invoke(firstSpawnState,viewControllerEventTwoString);
        spawnPlayer.invoke(firstSpawnState,viewControllerEventTwoString);

        assertEquals(0,ModelGate.getModel().getCurrentPlayingPlayer().getPosition().getX());
        assertEquals(2,ModelGate.getModel().getCurrentPlayingPlayer().getPosition().getY());

        assertNotNull(ModelGate.getModel().getPlayerList().getPlayer("Terminator").getPosition());


    }
}
