package it.polimi.se2019;

import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.WeaponCard;
import org.junit.Test;

public class TestWeaponCard {
    @Test
    public void test() {
        try {
            WeaponCard weaponCard = new WeaponCard("1");
            for (Effect e : weaponCard.getEffects()) {

                System.out.println("" + e.Exec());
                System.out.println("nuovo effetto");
            }


        } catch (Exception e) {
                System.out.println(e.toString());


        }
    }
}
