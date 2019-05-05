package it.polimi.se2018;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.TurretSquare;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;
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
        TurretSquare T = new TurretSquare(X,Y,N,E,S,O, SquareTypes.spawnPoint,'r');
    }
    @Test
    public void testGetOwner() {
        int X = 5;
        int Y = 5;
        SquareSide N = SquareSide.wall;
        SquareSide E = SquareSide.wall;
        SquareSide S = SquareSide.wall;
        SquareSide O = SquareSide.wall;
        TurretSquare T = new TurretSquare(X,Y,N,E,S,O, SquareTypes.spawnPoint,'r');
        Player p = new Player();
        T.setOwner(p);
        assertEquals(T.getOwner(),p);

    }
}
