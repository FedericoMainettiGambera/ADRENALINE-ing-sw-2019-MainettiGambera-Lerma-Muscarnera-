package it.polimi.se2019;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.TurretSquare;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestTurretSquare {
    @Test
    public void testTurretSquare() {
        int X = 5;
        int Y = 5;
        SquareSide N = SquareSide.wall;
        SquareSide E = SquareSide.wall;
        SquareSide S = SquareSide.wall;
        SquareSide O = SquareSide.wall;
        TurretSquare T = new TurretSquare(X,Y,N,E,S,O, SquareTypes.spawnPoint,'r', null);
    }
    @Test
    public void testGetOwner() {
        int X = 5;
        int Y = 5;
        SquareSide N = SquareSide.wall;
        SquareSide E = SquareSide.wall;
        SquareSide S = SquareSide.wall;
        SquareSide O = SquareSide.wall;
        TurretSquare T = new TurretSquare(X,Y,N,E,S,O, SquareTypes.spawnPoint,'r', null);
        Player p = new Player();
        T.setOwner(p);
        assertEquals(T.getOwner(),p);

    }
}
