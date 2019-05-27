package it.polimi.se2019;

import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SquareTest{
    NormalSquare normalSquare=new NormalSquare(0,0,null,SquareSide.nothing,SquareSide.nothing,SquareSide.wall,
            SquareTypes.normal,'r', null);

    @Test
    public void testWhatsLeft(){
     assertEquals(SquareSide.nothing, normalSquare.getSide(CardinalPoint.east));
     assertEquals(SquareSide.nothing, normalSquare.getSide(CardinalPoint.south));
     assertEquals(SquareSide.wall, normalSquare.getSide(CardinalPoint.west));
     assertEquals(SquareTypes.spawnPoint, normalSquare.getSquareType());
     assertEquals('r', normalSquare.getColor());
     assertEquals(null, normalSquare.getSide(CardinalPoint.north));
    }
}
