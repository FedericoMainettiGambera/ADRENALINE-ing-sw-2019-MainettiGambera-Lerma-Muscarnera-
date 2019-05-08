package it.polimi.se2019;

import it.polimi.se2019.model.Position;
import org.junit.Test;

import static junit.framework.TestCase.fail;

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
}
