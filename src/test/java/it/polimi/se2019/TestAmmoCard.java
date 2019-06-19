package it.polimi.se2019;

import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.AmmoCubes;
import it.polimi.se2019.model.AmmoList;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAmmoCard {
    @Test
    public void testRandomGeneratedAmmoCard() {
        System.out.println((int) Math.ceil(Math.random() * 1000) );
        for(int i = 0; i < 1000;i++) {

            AmmoCard a = new AmmoCard(i+"");
            System.out.println("carta "+ i);
            if(a.isPowerUp()) {
                System.out.println("\t POWERUP CARD " + a.getPowerUpCardPointer());
            }
            for(AmmoCubes b: a.getAmmunitions().getAmmoCubesList()) {
                System.out.println("\t COLORE[ " + b.getColor() + ", " + b.getQuantity() + "]");
            }

        }
    }
    @Test
    public void testAmmoCard() {
        AmmoCard A = new AmmoCard("1",new AmmoList(),false);
    }
    @Test
    public void testGetters() {

        AmmoCard A = new AmmoCard("1",new AmmoList(),false);
        A.getAmmunitions();
        A.isPowerUp();
        assertEquals("1",A.getID());

    }

}
