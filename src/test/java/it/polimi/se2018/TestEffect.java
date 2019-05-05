package it.polimi.se2018;

import it.polimi.se2018.model.*;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class TestEffect {
    @Test
    public void testEffectExec() {
        Effect x = new Effect();
        Action y = (Action) new Damage();

       // y.setActionInfo(new ActionInfo());
        Player p = new Player();
        y.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(p);
            x.getActions().add(y);
        try {
            x.Exec();
        } catch(Exception e) {
            fail();
        }
    }
}
