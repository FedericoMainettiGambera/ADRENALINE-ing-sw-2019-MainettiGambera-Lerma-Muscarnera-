package it.polimi.se2018;

import it.polimi.se2018.model.NormalSquare;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;
import org.junit.Test;

public class TestNormalSquare {
    @Test
    public void testNormalSquare() {

        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');

    }
    @Test
    public void testGetAmmoCards() {
        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
        N.getAmmoCards();
    }
}
