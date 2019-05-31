package it.polimi.se2019;

import it.polimi.se2019.model.Board;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEffect {

/* TODO : !!!!!!!!!!!!!!!!!!! COMMENTED OUT BECAUSE OF SONAR REPORTS !!!!!!!!!!!!!!!!!! */
    @Test
    public void Cartesian() {
        List<Object> A = new ArrayList<>();
        for(int i = 0;i < 12;i++)
                            A.add(i);


        List<Object> deepSquareCombination = (ArrayList <Object>) WeaponCard.cartesianPower(A,1);
        List<Object> flatSquareCombination = new ArrayList<>();

        if(deepSquareCombination.get(0).getClass().toString().equals("class java.util.ArrayList")) {
            for (Object x : deepSquareCombination) {
                flatSquareCombination.add((ArrayList<Object>) WeaponCard.arrayFlattener(x, 0));
            }
        }
        else {
            System.out.println("Element");
            for (Object x : deepSquareCombination)
                flatSquareCombination.add(x);
        }

        for(Object x: flatSquareCombination) {
            System.out.println(x);
        }
        //System.out.println(WeaponCard.arrayFlattener(WeaponCard.cardinalProduct(a,WeaponCard.cardinalProduct(b,c))));
    }
@Test
    public void testAlpha() {
    try {
        WeaponCard weaponCard = new WeaponCard("test");
        Player user = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        Player user4 = new Player();
        PlayersList playerList = new PlayersList();
        playerList.addPlayer(user);
        playerList.addPlayer(user2);
        playerList.addPlayer(user3);
        playerList.addPlayer(user4);

        Board board = new Board("0",new VirtualView());

        user.setNickname("Luca");
        user2.setNickname("Aldo");
        user3.setNickname("Bruno");
        user4.setNickname("Carlo");

        user.setPosition(0,0);
        user2.setPosition(1,1);
        user3.setPosition(2,2);
        user4.setPosition(2,3);

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
