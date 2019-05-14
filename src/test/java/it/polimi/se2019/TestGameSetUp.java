package it.polimi.se2019;

import it.polimi.se2019.model.GameSetUp;
import it.polimi.se2019.model.enumerations.GameMode;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGameSetUp{

    GameSetUp gameSetUp=new GameSetUp();

    @Test
    public void testMethods(){

        gameSetUp.setGameMode(GameMode.deathmatch);
        gameSetUp.setDoubleKill(true);
        gameSetUp.setFinalFrenzy(true);
        gameSetUp.setMapChoice("map1");
        gameSetUp.setNumberOfStartingSkulls(5);

        assertEquals(true, gameSetUp.getIsDoubleKill());
        assertEquals(true, gameSetUp.getIsFinalFrenzy());
        assertEquals(5, gameSetUp.getNumberOfStartingSkulls());
        assertEquals("map1", gameSetUp.getMapChoice());
        assertEquals(GameMode.deathmatch, gameSetUp.getGameMode());
    }
}
