package it.polimi.se2019;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

public class TestEffect {

/* TODO : !!!!!!!!!!!!!!!!!!! COMMENTED OUT BECAUSE OF SONAR REPORTS !!!!!!!!!!!!!!!!!! */
@Test
    public void testAlpha() {
    try {
        WeaponCard weaponCard = new WeaponCard("test");
        Player user = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        PlayersList playerList = new PlayersList();
        playerList.addPlayer(user);
        playerList.addPlayer(user2);
        playerList.addPlayer(user3);

        Board board = new Board("0",new VirtualView());
        user.setNickname("Luca");
        user.setPosition(0,0);
        user2.setPosition(1,1);
        user3.setPosition(2,2);

        System.out.println(weaponCard.usable(user,board,playerList).toString());

    }catch(Exception e) {
        System.out.println(e.toString());
    }
    }
/*
    @Test
    public void testPreCondition() {
        Action y = (Action) new Damage();
        Effect z = new Effect();
        z.getActions().add(y);
        z.getActions().get(0).getActionInfo().setPreConditionMethodName("alwaysTrue");
        assertEquals(true,z.getActions().get(0).getActionInfo().preCondition());
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
*/
}
