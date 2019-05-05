package it.polimi.se2018;

import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.Square;
import it.polimi.se2018.model.enumerations.CardinalPoint;
import it.polimi.se2018.model.enumerations.SquareSide;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.lang.*;

import static junit.framework.TestCase.assertEquals;

public class TestBoard {
    @Test
    public void testBuildMap()throws IOException {


           String s="map0";
           Square[][] map=null;
           Position pos=new Position(0,0);

           Board b = new Board(s);
           map= b.getBoard();

           assertEquals(0, map[0][0].getCoordinates().getX());
           assertEquals(0, map[0][0].getCoordinates().getY());
           assertEquals(SquareSide.wall, map[0][0].getSide(CardinalPoint.north));

    }

}
