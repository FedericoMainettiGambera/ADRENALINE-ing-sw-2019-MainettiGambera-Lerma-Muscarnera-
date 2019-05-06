package it.polimi.se2018;

import it.polimi.se2018.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TestEffect {
    @Test
    public void testPreCondition() {
        Action y = (Action) new Damage();
        y.getActionInfo().setPreConditionMethodName("alwaysTrue");
        assertEquals(true,y.getActionInfo().preCondition());
    }
    @Test
    public void testEffectExec() {
        Effect x = new Effect();
        Action y = (Action) new Damage();
        Action z = (Action) new Damage();
        y.getActionInfo().setPreConditionMethodName("alwaysTrue");
        z.getActionInfo().setPreConditionMethodName("alwaysTrue");
       // y.setActionInfo(new ActionInfo());
        Player p = new Player();
        y.getActionInfo().getActionContext().setPlayer(p);
        z.getActionInfo().getActionContext().setPlayer(p);
        y.getActionInfo().getActionDetails().getFileSelectedActionDetails().setDamage(1);
        z.getActionInfo().getActionDetails().getFileSelectedActionDetails().setDamage(1);

        y.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(p);
        z.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(p);
        x.getActions().add(y);
        x.getActions().add(z);
        if(!x.Exec()) fail();

    }
    @Test
    public void testEffectExecNotExecutable() {
        Effect x = new Effect();
        Action y = (Action) new Damage();
        Action z = (Action) new Damage();
        z.getActionInfo().setPreConditionMethodName("alwaysTrue");
        y.getActionInfo().setPreConditionMethodName("alwaysFalse");

        Player p = new Player();
        y.getActionInfo().getActionDetails().getUserSelectedActionDetails().setTarget(p);
        x.getActions().add(y);
        x.getActions().add(z);
        try {
            assertEquals(x.Exec(),false);
        } catch(Exception e) {
            fail();
        }
    }
    @Test
    public void testSecondConstructor() {
        List<Action> t = new ArrayList<Action>();
        t.add(new Damage());
        Effect e = new Effect("test",t);
    }
    @Test
    public void testGetters() {
        Effect e = new Effect();
        e.getActions();
        e.getDescription();
    }
    @Test
    public void testGetters2() {
        List<Action> t = new ArrayList<Action>();
        t.add(new Damage());
        t.add(new Pay());
        Effect e = new Effect("test",t);
        e.getActions();
        e.getDescription();
    }
    @Test
    public void testSetters() {
        Effect e = new Effect();
        e.setActions(new ArrayList<Action>());
        e.setDescription("test");


    }
}
