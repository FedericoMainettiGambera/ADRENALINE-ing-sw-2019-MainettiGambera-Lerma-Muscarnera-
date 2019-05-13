package it.polimi.se2019;

import it.polimi.se2019.model.Position;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPosition {
    @Test
    public void testIllegalPosition() {
        try {

            Position x = new Position(-1, -1);
            fail();
        }  catch(Exception e) {
            /**/
        }

    }
    @Test
    public void testEquals(){

        Position y=new Position(2,2);
        Position x=new Position(2,2);
        assertEquals(true, y.equals(x));
        x=new Position(1,1);
        assertEquals(false, y.equals(x));
    }
}
