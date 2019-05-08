package it.polimi.se2019;

import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.AmmoList;
import org.junit.Test;

public class TestAmmoCard {
    @Test
    public void testAmmoCard() {
        AmmoCard A = new AmmoCard("1",new AmmoList(),false);
    }
    @Test
    public void testGetters() {

        AmmoCard A = new AmmoCard("1",new AmmoList(),false);
        A.getAmmunitions();
        A.isPowerUp();

    }
}
