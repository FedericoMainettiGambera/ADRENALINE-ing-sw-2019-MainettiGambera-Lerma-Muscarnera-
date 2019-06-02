package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import it.polimi.se2019.model.enumerations.PlayersColors;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ObjectOutputStream;
import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayerPerson{
/*FOR MAVEN PURPOSE
    Player p=new Player();
    Player p2=new Player();

   @Test
   public void excep(){}


    @Test
    public void testMethods(){
        p.setIP("1110");
        assertEquals("1110",p.getIp());

        p.setColor(PlayersColors.blue);
        assertEquals(PlayersColors.blue,p.getColor());

        p.setNickname("ludole");
        assertEquals("ludole", p.getNickname());

        Position po=new Position(0,0);
        p.setPosition(0,0);
      //  assertEquals(po, p.getPosition());

        p.setPosition(po);
        assertEquals(po, p.getPosition());


        p.addPoints(4);
        assertEquals(4, p.getScore());
        assertEquals(8, p.getPointsList().get(0));
        assertEquals(0, p.getDeathCounter());


        p.addDeath();
        assertEquals(1, p.getDeathCounter());
        assertEquals(6, p.getPointsList().get(0));

        p.makePlayerBoardFinalFrenzy();
        p2.makePlayerBoardFinalFrenzy();
        assertEquals(2, p.getPointsList().get(0));
        assertEquals(1, p.getPointsList().get(1));
        assertEquals(2, p2.getPointsList().get(0));

        p2.addDamages(p, 5);
        p.addDamages(p2, 4);
        assertEquals(p2, p.getPlayersDamageRank().get(0));

        assertEquals(false,p.isDead());
        p2.addDamages(p, 6);
        assertEquals(true,p2.isDead());
        assertEquals(false,p2.isOverkilled());
        p2.addDamages(p,1);
        assertEquals(true, p2.isOverkilled());
        p2.emptyDamagesTracker();

        AmmoList ammos=new AmmoList();
        ammos.addAmmoCubesOfColor(AmmoCubesColor.red,1);
        p.addAmmoCubes(ammos);

        p.addAmmoCubes(AmmoCubesColor.blue,3);
        assertEquals(true, p.payAmmoCubes(AmmoCubesColor.blue,3));
        assertEquals(true, p.canPayAmmoCubes(ammos));
        assertEquals(false, p.canPayAmmoCubes(AmmoCubesColor.yellow,1));
        assertEquals(true,p.payAmmoCubes(ammos));
        assertEquals(false, p.payAmmoCubes(ammos));
        assertEquals(false, p.payAmmoCubes(AmmoCubesColor.blue,5));

        Player p3=new Player();
        assertEquals(true, p.hasAdrenalineGrabAction());
        assertEquals(false, p3.hasAdrenalineShootAction());
        assertEquals(false, p3.hasAdrenalineGrabAction());
        assertEquals(false, p.hasAdrenalineShootAction());
        p.addDamages(p2, 6);
        assertEquals(true, p.hasAdrenalineShootAction());


        p.addMarksFrom(p3, 3);
        p3.addMarksFrom(p,2);
        assertEquals(3, p.getMarksFrom(p3));
        p3.applyMarksFromPlayer(p);
        assertEquals(p,p3.getDamageSlot(0).getShootingPlayer());
        p3.deleteMarksFromPlayer(p);
        assertEquals(0, p3.getMarksFrom(p));

        PlayerBoard playerBoard=new PlayerBoard();
        playerBoard=p3.getBoard();
        assertEquals(playerBoard, p3.getBoard());
        playerBoard=p.getPlayerBoard();
        assertEquals(playerBoard,p.getPlayerBoard());

        p.addDamages(p3,6);
        assertEquals(p2, p.getPlayersDamageRank().get(0));
        p.addDamages(p3, 7);
        assertEquals(p3, p.getPlayersDamageRank().get(0));
        assertEquals(2,p.getPointsList().get(0));
        p.makePlayerBoardFinalFrenzy();
        assertEquals(1, p.getPointsList().get(3));
    }
    */
}
