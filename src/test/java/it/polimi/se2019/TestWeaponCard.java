package it.polimi.se2019;
//TODO
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestWeaponCard {
    /*
    @Test
    public void test() {
        try {
            for(int i = 1;i< 8;i++) {
                WeaponCard weaponCard = new WeaponCard(i + "");
                System.out.println("#" + i + ":");
                try {
                    for(EffectInfoElement e: weaponCard.getEffects().get(0).getEffectInfo().getEffectInfoElement())
                        System.out.println(" input mode " + e.getEffectInfoTypelist());
                } catch( NullPointerException e) {
                    System.out.println("errore:" + "non specificato");

                }
            }

        } catch (Exception e) {
            System.out.println("errore : " + e.toString());


        }
    }
    @Test
    public void testAlpha() throws Exception{
        int caso = 2;
        String ID = "Test";
        if(caso == 1) {
            testerA(ID);
        }
        else {
            WeaponCard weaponCard = new WeaponCard(ID);
            System.out.println(weaponCard.getEffects().size());

        }
    }
    private void testerA(String ID) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/Files/cards/weaponCards/card"+ID+".set"));
        List<Effect> effects = new ArrayList<>();

        try {
            String line;
            line = reader.readLine();

            while (line != null) {
                if (line.equals("NEW EFFECT"))
                    effects.add(new Effect());

                System.out.println(line);
                line = reader.readLine();
            }
        } catch(Exception e) {;}
        System.out.println("---");
        System.out.println(effects.size());
    }
    @Test
    public void testExecuting() {
        try {
            Player user   = new Player();

            Player target = new Player();
            Player target2 = new Player();
            Player target3 = new Player();

            user.setNickname("Paolo");
            user.setPosition(0,0);

            target.setNickname("Luca");
            target2.setNickname("Marco");
            target3.setNickname("Pippo");

            PlayersList playerList = new PlayersList();
            playerList.addPlayer(user);
            playerList.addPlayer(target);
            playerList.addPlayer(target2);
            playerList.addPlayer(target3);

            Square A = new NormalSquare(1,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
            Square B = new NormalSquare(2,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareTypes.normal,'g');


            target2.setPosition(1,0);


            WeaponCard weaponCard = new WeaponCard("Test2");
            Object i[][] = new Object[10][10];
            i[0][0] = target;
            i[0][1] = target2;
            i[0][2] = target3;
            for(Action a : weaponCard.getEffects().get(0).getActions()) {
                a.getActionInfo().getActionContext().setPlayer(user);
                a.getActionInfo().getActionContext().setPlayerList(playerList);
            }



            weaponCard.getEffects().get(0).handleInput(i);



            for(Action a : weaponCard.getEffects().get(0).getActions()) {

                System.out.println("utente: " + a.getActionInfo().getActionContext().getPlayer().getNickname());


                System.out.println("precondizione\t" + a.getActionInfo().getPreConditionMethodName());
                for(ActionContextFilteredInput b:  a.getActionInfo().getActionContext().getActionContextFilteredInputs()) {
                    System.out.println(a.toString() + "ha ricevuto come input " + Arrays.deepToString(b.getContent()) + " di tipo " + b.getType());
                }
                a.getActionInfo().getActionContext().setPlayer(user);

            }

            if(weaponCard.getEffects().get(0).Exec()) {
                System.out.println("Eseguita!");
            } else {
                System.out.println("non eseguita");
            }
        } catch(Exception e) {

            System.out.println("##" + e.toString());
        }

    }
    @Test
    public void testAttackBySquare() throws Exception {

        Board board = new Board("2",new VirtualView(),new VirtualView());
        Player user1 = new Player();
        Player user2 = new Player();
        Player user3 = new Player();
        Player user4 = new Player();
        System.out.println(".");
        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        System.out.println(".");
        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);
        user1.setPosition(1,0);
        user2.setPosition(2,0);
        user3.setPosition(3,0);                 //   same position
        user4.setPosition(2,0);                 //                  users
        System.out.println(".");
        Object[][] o = new Object[10][10];
        Square A =  new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
        String B =  "Bruno";
        System.out.println("x");
        o[0][0]      =  board.getSquare(1,0);
        o[1][0]      =  board.getSquare(0,0);

        WeaponCard weaponCard = new WeaponCard("21");
        for(Effect e : weaponCard.getEffects())
        for(Action a : e.getActions()) {
            a.getActionInfo().getActionContext().setPlayer(user1);
            a.getActionInfo().getActionContext().setPlayerList(playerList);
            a.getActionInfo().getActionContext().setBoard(board);
        }
        // gestione dell'input
        for(Square[] x : board.getMap()) {
            String linea = "\n";
            for(Square y: x) {
                linea += y.getColor() + "\t\t\t\t\t";
            }
            linea += "\n";
            for(Square y: x) {

                int output = 0;
                if(y.getSide(CardinalPoint.north) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.north) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.north) == SquareSide.nothing) {
                    output = 0;
                }
                linea += "|\t\t" + output  + "\t\t|\t";
            }
            linea += "\n";
            for(Square y: x) {
                int output = 0;
                if(y.getSide(CardinalPoint.east) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.east) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.east) == SquareSide.nothing) {
                    output = 0;
                }
                int output2 = 0;
                if(y.getSide(CardinalPoint.west) == SquareSide.wall) {
                    output2 = 1;
                }
                if(y.getSide(CardinalPoint.west) == SquareSide.door) {
                    output2 = 2;
                }
                if(y.getSide(CardinalPoint.west) == SquareSide.nothing) {
                    output2 = 0;
                }
                linea += "|" + output + "             " + output2 + "|\t";
            }
            linea += "\n";
            for(Square y: x) {
                int output = 0;
                if(y.getSide(CardinalPoint.south) == SquareSide.wall) {
                    output = 1;
                }
                if(y.getSide(CardinalPoint.south) == SquareSide.door) {
                    output = 2;
                }
                if(y.getSide(CardinalPoint.south) == SquareSide.nothing) {
                    output = 0;
                }
                linea += "|\t\t" + output  + "\t\t|\t";
            }
            linea += "\n";
            System.out.println(linea);
        }
        // caricamento contesto
        int effectId = 1;
        user2.setPosition(user1.getPosition());
        System.out.println("/----------------/");
        o[0][0]      =  user2;
        o[1][0]      =  board.getSquare(2,1) ;
      //  o[1][2]      =  user4 ;
        weaponCard.getEffects().get(effectId).handleInput(o);
        weaponCard.getEffects().get(effectId).Exec();

        System.out.println("/--/");
        System.out.println("status");
        System.out.println("/--/");


    }
    @Test
    public void testPlayerHistory() throws Exception{

        Player me = new Player();
        me.setNickname("A");

        Player ne = new Player();
        ne.setNickname("B");

        Player oe = new Player();
        oe.setNickname("C");
        PlayersList p = new PlayersList();
        p.addPlayer(me);
        p.addPlayer(ne);
        p.addPlayer(oe);

        Board board = new Board("2",new VirtualView(),new VirtualView());
        WeaponCard w = new WeaponCard("1");
        for(Action a : w.getEffects().get(0).getActions()) {
            a.getActionInfo().getActionContext().setPlayer(me);
            a.getActionInfo().getActionContext().setPlayerList(p);
            a.getActionInfo().getActionContext().setBoard(board);
        }
        int i;
        Object[][] o = new Object[10][10];
        o[0][0] = me;
        w.effects.get(0).handleInput(o);
        o[0][0] = ne;
        w.effects.get(0).handleInput(o);
        for(i = 0; i < me.getPlayerHistory().getSize();i++) {
            System.out.println("#" + ((Player)(((Object[][])me.getPlayerHistory().getRecord(i).getInput())[0][0])).getNickname());
        }
        o[0][0] = oe;
        w.effects.get(0).handleInput(o);

        for(i = 0; i < me.getPlayerHistory().getSize();i++) {
            System.out.println("#" + ((Player)(((Object[][])me.getPlayerHistory().getRecord(i).getInput())[0][0])).getNickname());
        }
    }
    @Test
    public void testCard9() {
        try {
            System.out.println(".");
            WeaponCard weaponCard = new WeaponCard("21");
            System.out.println(".");
            System.out.println(weaponCard.getEffects().size());
            for(Effect e:weaponCard.getEffects() ) {

                for(EffectInfoElement ei: e.getEffectInfo().getEffectInfoElement()) {
                    System.out.println("questo input '" + ei.getEffectInfoTypelist().toString() +"'");
                    for(Integer x:ei.getEffectInfoTypeDestination())
                        System.out.println("\te' per:" + x);
                }
                System.out.println(e.toString());
                int j = 0;
                for(Action a: e.getActions()) {
                    System.out.println("quando " + a.getActionInfo().getPreConditionMethodName() + "() e' vera, allora" );
                    System.out.println((++j) + "\t" + a.toString());
                    for(Object o : a.getActionInfo().getActionDetails().getFileSelectedActionDetails().getFileSettingData()) {
                        System.out.println("\t\tValue : "+ o.toString());
                    }
                }
                System.out.println("--");

            }
        } catch(Exception e) {
            System.out.println("##" + e.toString());

        }
    }
    */
}
