package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestWeaponCard {
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


        user1.setPosition(0,0);
        user2.setPosition(0,0);
        user3.setPosition(0,0);                 /*   same position          */
        user4.setPosition(1,1);                 /*                  users   */
        System.out.println(".");
        Object[][] o = new Object[10][10];
        Square A =  new NormalSquare(0,0, SquareSide.wall,SquareSide.wall,SquareSide.wall,SquareSide.wall, SquareTypes.normal,'r');
        String B =  "Bruno";
        System.out.println("x");
        o[0][0]      =  user2;

        // caricamento contesto
        int effectId = 1;
        WeaponCard weaponCard = new WeaponCard("2");
        for(Action a : weaponCard.getEffects().get(0).getActions()) {
            a.getActionInfo().getActionContext().setPlayer(user1);
            a.getActionInfo().getActionContext().setPlayerList(playerList);
        }
        for(Action a : weaponCard.getEffects().get(effectId).getActions()) {
            a.getActionInfo().getActionContext().setPlayer(user1);
            a.getActionInfo().getActionContext().setPlayerList(playerList);
        }
        // gestione dell'input
        weaponCard.getEffects().get(0).handleInput(o);
        weaponCard.getEffects().get(0).Exec();
        System.out.println("/----------------/");
       // o[0][0]      =  user3;
        weaponCard.getEffects().get(effectId).handleInput(o);
        weaponCard.getEffects().get(effectId).Exec();
        System.out.println("/--/");
        System.out.println("status");
        System.out.println("/--/");


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
}
