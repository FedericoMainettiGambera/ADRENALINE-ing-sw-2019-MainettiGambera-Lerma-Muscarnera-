package it.polimi.se2018;

import it.polimi.se2018.model.Board;
import it.polimi.se2018.model.NormalSquare;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.Square;
import it.polimi.se2018.model.enumerations.CardinalPoint;
import it.polimi.se2018.model.enumerations.SquareSide;
import it.polimi.se2018.model.enumerations.SquareTypes;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

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

           Board c;
           Board b = new Board(s);
           map=b.getBoard();




           assertEquals(0, map[0][0].getCoordinates().getX());
           assertEquals(0, map[0][0].getCoordinates().getY());
           assertEquals(SquareSide.wall, map[0][0].getSide(CardinalPoint.north));
           assertEquals(SquareTypes.normal, map[0][0].getSquareType());
           assertEquals(null, map[0][3]);
           assertEquals(SquareTypes.spawnPoint, map[1][0].getSquareType());
           assertEquals(SquareTypes.spawnPoint, map[2][3].getSquareType());
           assertEquals(map,map );


    }

    @Test
    public void testBuildMapException(){



    }



}
