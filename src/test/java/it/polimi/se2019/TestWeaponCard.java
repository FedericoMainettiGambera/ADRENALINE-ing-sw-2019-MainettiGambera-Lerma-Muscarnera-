package it.polimi.se2019;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import org.junit.Test;

public class TestWeaponCard {
    @Test
    public void test() {
        try {
        for(int i = 1;i< 8;i++) {
            WeaponCard weaponCard = new WeaponCard(i + "");
            System.out.println("#" + i + ":");
            try {
                for(EffectInfoType e: weaponCard.getEffects().get(0).getEffectInfo().getEffectInfoTypelist())
                System.out.println(" input mode " + e);
            } catch( NullPointerException e) {
                    System.out.println("errore:" + "non specificato");

            }
            }

        } catch (Exception e) {
                System.out.println("errore : " + e.toString());


        }
    }
}
