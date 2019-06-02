package it.polimi.se2019;

import it.polimi.se2019.model.MarkSlots;
import it.polimi.se2019.model.MarksTracker;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.PlayersColors;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestMarkTracker {
    /*FOR MAVEN PURPOSE
    @Test
    public void testAddMark(){
        MarksTracker dt = new MarksTracker();
        Player p = new Player();
        p.setColor(PlayersColors.yellow);
        p.setNickname("test");
        int numberOfMarks = 2;

        dt.addMarksFrom(
                p,numberOfMarks
        );

        assertEquals(p,dt.getMarkSlotsList().get(0).getMarkingPlayer());
       // assertEquals(numberOfMarks,dt.getMarkSlotsList().size());



        dt = new MarksTracker();
        numberOfMarks = -2;

        try {
            dt.addMarksFrom(p, numberOfMarks);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals(0, dt.getMarkSlotsList().size() );
        }




        dt = new MarksTracker();
        numberOfMarks = 2;
        try {
            dt.addMarksFrom(null, numberOfMarks);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals(0, dt.getMarkSlotsList().size() );
        }



        //dt = new MarksTracker();
        //MarkSlots ds = new MarkSlots(p);

        //dt.addMarksFrom();
        //dt.addMarksFrom();

        //assertEquals(p,dt.getDamageSlotsList().get(0).getShootingPlayer());
        //assertEquals(p,dt.getDamageSlotsList().get(1).getShootingPlayer());
        //assertEquals(numberOfDamages,dt.getDamageSlotsList().size());



        dt = new MarksTracker();
        try{
            dt.addMarksFrom(null,1);
            fail();
        }
        catch (IllegalArgumentException e){
            assertEquals(0, dt.getMarkSlotsList().size() );
        }
    }

    @Test
    public void testEmptyList(){
        MarksTracker dt = new MarksTracker();
        Player p = new Player();
        p.setColor(PlayersColors.yellow);
        p.setNickname("test");
        int quantity = 3;
        MarkSlots ds = new MarkSlots(p, quantity);
        ds.addQuantity(quantity);
        ds.addQuantity(quantity);

        assertEquals(true, dt.getMarkSlotsList().isEmpty());
    }
*/

}
