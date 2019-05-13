package it.polimi.se2019;

import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.enumerations.AmmoCubesColor;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAmmoList{

       AmmoList ammoList=new AmmoList();

       @Test
        public void testGetters(){

           assertEquals(false,ammoList.canPayAmmoCubes(AmmoCubesColor.yellow, 3));
           ammoList.addAmmoCubesOfColor(AmmoCubesColor.yellow, 3);
           assertEquals(true,ammoList.canPayAmmoCubes(AmmoCubesColor.yellow, 2));
           ammoList.payAmmoCubes(AmmoCubesColor.yellow, 3);
           assertEquals(false, ammoList.canPayAmmoCubes(AmmoCubesColor.yellow, 1));

        }


    }




