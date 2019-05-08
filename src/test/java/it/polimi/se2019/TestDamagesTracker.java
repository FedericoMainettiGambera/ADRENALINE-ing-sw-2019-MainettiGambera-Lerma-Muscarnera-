package it.polimi.se2019;

import it.polimi.se2019.model.DamageSlot;
import it.polimi.se2019.model.DamagesTracker;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.PlayersColors;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDamagesTracker {
    @Test
    public void testAddDamage(){
        DamagesTracker dt = new DamagesTracker();
        Player p = new Player();
        p.setColor(PlayersColors.yellow);
        p.setNickname("test");
        int numberOfDamages = 2;

        dt.addDamages(p,numberOfDamages);

        assertEquals(p,dt.getDamageSlotsList().get(0).getShootingPlayer());
        assertEquals(p,dt.getDamageSlotsList().get(1).getShootingPlayer());
        assertEquals(numberOfDamages,dt.getDamageSlotsList().size());



        dt = new DamagesTracker();
        numberOfDamages = -2;

        try {
            dt.addDamages(p, numberOfDamages);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals(0, dt.getDamageSlotsList().size() );
        }




        dt=new DamagesTracker();
        numberOfDamages = 2;
        try {
            dt.addDamages(null, numberOfDamages);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals(0, dt.getDamageSlotsList().size() );
        }



        dt = new DamagesTracker();
        DamageSlot ds = new DamageSlot(p);

        dt.addDamage(ds);
        dt.addDamage(ds);

        assertEquals(p,dt.getDamageSlotsList().get(0).getShootingPlayer());
        assertEquals(p,dt.getDamageSlotsList().get(1).getShootingPlayer());
        assertEquals(numberOfDamages,dt.getDamageSlotsList().size());



        dt = new DamagesTracker();
        try{
            dt.addDamage(null);
            fail();
        }
        catch (IllegalArgumentException e){
            assertEquals(0, dt.getDamageSlotsList().size() );
        }
    }

    @Test
    public void testEmptyList(){
        DamagesTracker dt = new DamagesTracker();
        Player p = new Player();
        p.setColor(PlayersColors.yellow);
        p.setNickname("test");
        DamageSlot ds = new DamageSlot(p);

        dt.addDamage(ds);
        dt.addDamage(ds);

        dt.emptyList();

        assertEquals(0, dt.getDamageSlotsList().size());
    }

    @Test
    public void testGetDamageSlot(){
        DamagesTracker dt = new DamagesTracker();
        try{
            dt.getDamageSlot(10);
            fail();
        }
        catch (IndexOutOfBoundsException e){
            boolean value = (dt.getDamageSlotsList().size() < 10);
            assertTrue(value);
        }

        Player p = new Player();
        p.setColor(PlayersColors.yellow);
        p.setNickname("test");
        DamageSlot ds = new DamageSlot(p);

        dt.addDamage(ds);

        assertEquals(ds, dt.getDamageSlot(0));
    }
}
