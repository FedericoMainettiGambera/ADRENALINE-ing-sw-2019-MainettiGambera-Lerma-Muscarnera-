package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Position;
import it.polimi.se2019.model.Square;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.io.IOException;
import java.lang.*;

import static junit.framework.TestCase.assertEquals;

public class TestBoard {
    @Test
    public void testDistanceFromTo(){
        testingDistance("map0");
        testingDistance("map1");
        testingDistance("map2");
        testingDistance("map3");
    }
    public static void testingDistance(String map){
        System.out.println("\n\n\n\n\n\ntesting map: " + map);
        Board board = null;
        try {
            board = new Board(map, null, null);
        } catch (IOException e) {
            System.err.println("ERROR: " + map + "      doesn't exist.");
            return;
        }
        System.out.println("Calculating all possibles distance and even exceeding the limits of the map...");
        System.out.println(board.toString());

        Position from;
        Position to;
        for (int i = 0; i < board.getMap()[0].length ; i++) {
            for (int j = 0; j < board.getMap().length; j++) {
                for (int k = 0; k < board.getMap()[0].length; k++) {
                    for (int l = 0; l < board.getMap().length; l++) {
                        from = new Position(j,i);
                        to = new Position(l,k);
                        try {
                            System.out.println("Distance from position [" + from.getX() + "][" + from.getY() + "] to position [" + to.getX() + "][" + to.getY() + "] is: " + board.distanceFromTo(from,to));
                        } catch (Exception e) {
                            System.err.println("EXCEPTION: " + e.getMessage() + "       during the calculation of distance from position [" + from.getX() + "][" + from.getY() + "] to position [" + to.getX() + "][" + to.getY() + "]");
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testBuildMap()throws IOException,Exception {

        String s = "map0";
        Square[][] map = null;
        Position pos = new Position(0, 0);
        Position pos2 = new Position(0, 2);
        Position po3 = new Position(0, 0);

        Board c;
        Board b = new Board(s,new VirtualView(), new VirtualView());
        map = b.getMap();
        //testin SpawnPointOfCOlorMethod
        po3 = b.getSpawnpointOfColor(AmmoCubesColor.blue);
        assertEquals(pos2.getX(), po3.getX());
        assertEquals(pos2.getY(), po3.getY());

        po3 = b.getSpawnpointOfColor(AmmoCubesColor.yellow);
        pos2 = new Position(2, 3);
        assertEquals(pos2.getX(), po3.getX());
        assertEquals(pos2.getY(), po3.getY());

        po3 = b.getSpawnpointOfColor(AmmoCubesColor.red);
        pos2 = new Position(1, 0);
        assertEquals(pos2.getX(), po3.getX());
        assertEquals(pos2.getY(), po3.getY());


//BuildMap method
        assertEquals(0, map[0][0].getCoordinates().getX());
        assertEquals(0, map[0][0].getCoordinates().getY());
        assertEquals(SquareSide.wall, map[0][0].getSide(CardinalPoint.north));
        assertEquals(SquareTypes.normal, map[0][0].getSquareType());
        assertEquals(null, map[0][3]);
        assertEquals(SquareTypes.spawnPoint, map[1][0].getSquareType());
        assertEquals(SquareTypes.spawnPoint, map[2][3].getSquareType());

        String mapp="map1";
        b=new Board(mapp, new VirtualView(), new VirtualView());
        map=b.getMap();
        assertEquals(SquareSide.wall, map[0][0].getSide(CardinalPoint.north));
        assertEquals(SquareSide.wall, map[0][3].getSide(CardinalPoint.north));

        //try {
        //    pos=b.getSpawnpointOfColor(AmmoCubesColor.blue);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

//        assertEquals(pos,map[0][2].getCoordinates() );


        //Possible Position

        b=new Board(s, new VirtualView(), new VirtualView());
        pos2=new Position(0,0);
        po3=new Position(0,2);
        assertEquals(po3.getX(), b.possiblePositions(pos2,2).get(0).getX());
        po3=new Position(1,1);
        assertEquals(po3.getX(), b.possiblePositions(pos2,2).get(1).getX());


    }
}
