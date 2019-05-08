package it.polimi.se2019;

import it.polimi.se2019.model.Action;
import it.polimi.se2019.model.ActionInfo;
import it.polimi.se2019.model.TargetingScope;
import org.junit.Test;

public class TestTargetingScope {
    @Test
    public void testTargetingScope() {

        Action t = new TargetingScope(new ActionInfo());
    }
    @Test
    public void testExec() {
        Action t = new TargetingScope(new ActionInfo());
        t.Exec();
    }
}
