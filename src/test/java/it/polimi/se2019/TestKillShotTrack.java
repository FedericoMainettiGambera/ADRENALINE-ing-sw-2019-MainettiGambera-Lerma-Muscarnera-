package it.polimi.se2019;

import it.polimi.se2019.model.KillShotTrack;
import it.polimi.se2019.model.Player;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestKillShotTrack{

    KillShotTrack killShotTrack= new KillShotTrack(8);
    KillShotTrack killShotTrack2= new KillShotTrack(2);
    Player p=new Player();

    @Test
    public void testMethods(){

        killShotTrack.deathOfPlayer(p, true);
        assertEquals(false, killShotTrack.areSkullsOver());



    }
}
