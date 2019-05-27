package it.polimi.se2019;

import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import org.junit.Test;

public class TestNormalSquare {
    @Test
    public void testNormalSquare() {

        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r', null);

    }
    @Test
    public void testGetAmmoCards() {
        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r', null);
        N.getAmmoCards();
    }
    @Test
    public void testCardinal() {
        // 45 - 58
        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r', null);
        N.getSide(CardinalPoint.north);
        N.getSide(CardinalPoint.east);
        N.getSide(CardinalPoint.south);
        N.getSide(CardinalPoint.west);

        N.getSquareType();
        N.getColor();
        // 70 - 73

    }
    @Test
    public void testNullCardinal() {
        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r', null);
        N.getSide(null);
    }
    @Test
    public void testCoords() {
        NormalSquare N = new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r', null);
        N.getCoordinates();

    }
}
