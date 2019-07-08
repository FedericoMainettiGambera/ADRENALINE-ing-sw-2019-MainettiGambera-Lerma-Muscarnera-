package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.GameConstant;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameCostantTest{

    @Test
    public void gameCostantTest(){

        assertEquals(3,GameConstant.MAX_NUMBER_OF_AMMO_CUBES);
        assertEquals(8, GameConstant.MAX_STARTING_SKULLS);
        assertEquals(5, GameConstant.MIN_STARTING_SKULLS);
        assertEquals(3, GameConstant.MAX_NUMBER_OF_MARK_FROM_PLAYER);
        assertEquals(5, GameConstant.MAX_NUMBER_OF_PLAYER_PER_GAME);
        assertEquals(3, GameConstant.MIN_NUMBER_OF_PLAYER_PER_GAME);









    }
}
