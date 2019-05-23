package it.polimi.se2019;

import it.polimi.se2019.model.WeaponCard;
import org.junit.Test;

public class TestWeaponCard {
    @Test
    public void test() {
        try {
        for(int i = 1;i< 8;i++) {
            WeaponCard weaponCard = new WeaponCard(i + "");
            System.out.println( i + "# reload " + weaponCard.getPickUpCost());
        }

        } catch (Exception e) {
                System.out.println(e.toString());


        }
    }
}
