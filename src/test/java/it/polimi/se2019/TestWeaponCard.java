package it.polimi.se2019;

import it.polimi.se2019.model.*;
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
            user.setNickname("Paolo");

            target.setNickname("Luca");
            target2.setNickname("Marco");

            target.setPosition(1,1);
            user.setPosition(1,1);

            WeaponCard weaponCard = new WeaponCard("Test");
            Object i[][] = new Object[10][10];
            i[0][0] = target;
            i[1][0] = target2;

            weaponCard.getEffects().get(0).handleInput(i);

            for(Action a : weaponCard.getEffects().get(0).getActions()) {
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
