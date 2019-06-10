package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.util.List;

public class TestEffect {
    /*FOR MAVEN PURPOSE
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
        WeaponCard weaponCard = new WeaponCard("22");
        Player user = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        Player user4 = new Player();
        PlayersList playerList = new PlayersList();
        playerList.addPlayer(user);
        playerList.addPlayer(user2);
        playerList.addPlayer(user3);
        playerList.addPlayer(user4);

        Board board = new Board("0",new VirtualView(), new VirtualView());

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
    @Test
    public void testUsable( ) throws Exception {

            System.out.println("\t\tcarta " + 13);
            WeaponCard w = new WeaponCard(13 + "");




        Board board = new Board("2",new VirtualView(),new VirtualView());
        Player user1 = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        Player user4 = new Player();
        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);

        for(Square[] y: board.getMap() )
            for(Square x: y)
                System.out.println(x.getCoordinates().getX() + " , " + x.getCoordinates().getY() + ": " + x.getColor());

        user1.setPosition(1,2);
        user2.setPosition(1,3);
        user3.setPosition(1,1);                 //   same position
        user4.setPosition(2,2);                 //

            w.getEffects().get(0).passContext(user1,playerList,board);
            int counter = 0;
            Object[][] input = new Object[10][10];
           /* for(EffectInfoElement el: w.getEffects().get(0).getEffectInfo().getEffectInfoElement()) {
                System.out.println("**************** input atteso per " + el.getEffectInfoTypelist() + ">" + w.getEffects().get(0).usableInputs().get(

                        w.getEffects().get(0).getEffectInfo().getEffectInfoElement().indexOf(el)

                        )
                );

                if(counter == 0) {
                    input[counter][0] = user2;
                }
                if(counter == 1) {
                    input[counter][0] = user3;
                    input[counter][1] = user4;
                }
                System.out.println("INPUT " + input[counter][0]);
                w.getEffects().get(0).handleRow(el,input[counter]);
                counter++;
              }*/
        //w.getEffects().get(0).handleInput(input);


            //for(Object x:  w.getEffects().get(0).usableInputs() )
            //System.out.println("*" + x);
            //for(Object p:  w.getEffects().get(0).usableInputs().get(2).get(0))
            //    System.out.println("risultato = " + p.toString() + ":" + ((Player) p).getNickname());

            System.out.println("/---------/");

            Object o[][] = new Object[10][10];
            o[0][0] = user3;
        System.out.println("input  " + (user1).getNickname() + "[" +  (user1).getPosition().getX() + " , " +  (user1).getPosition().getY() + "]");
        System.out.println("input  " + ((Player) o[0][0]).getNickname() + "[" +  ((Player) o[0][0]).getPosition().getX() + " , " +  ((Player) o[0][0]).getPosition().getY() + "]");

        System.out.println("********" + w.getEffects().get(0).usableInputs().get(0));
        for(Object t: (w.getEffects().get(0).usableInputs().get(0))) {
            for(Object z: (List<Player>)t) {
                System.out.println("ammesso " + ((Player) z).getNickname() + "[" +  ((Player) z).getPosition().getX() + " , " +  ((Player) z).getPosition().getY() + "]");
            }
        }
        System.out.println("***********");
        w.getEffects().get(0).handleRow(w.getEffects().get(0).getEffectInfo().getEffectInfoElement().get(0), o[0]);
            o[1][0] = user4;
        System.out.println("input  " + ((Player) o[1][0]).getNickname() + "[" +  ((Player) o[1][0]).getPosition().getX() + " , " +  ((Player) o[1][0]).getPosition().getY() + "]");
        System.out.println("********" + w.getEffects().get(0).usableInputs().get(1));
        for(Object t: (w.getEffects().get(0).usableInputs().get(1))) {
            for(Object z: (List<Player>)t) {
                System.out.println("ammesso " + ((Player) z).getNickname() + "[" +  ((Player) z).getPosition().getX() + " , " +  ((Player) z).getPosition().getY() + "]");
            }
        }
        System.out.println("***********");
        w.getEffects().get(0).handleRow(w.getEffects().get(0).getEffectInfo().getEffectInfoElement().get(1), o[1]);
            w.getEffects().get(0).Exec();
        System.out.println("/---------/");
        System.out.println("/---------/");

    }
}
