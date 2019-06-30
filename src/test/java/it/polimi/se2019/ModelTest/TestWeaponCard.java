package it.polimi.se2019.ModelTest;

import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.view.components.EffectV;
import it.polimi.se2019.view.components.WeaponCardV;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class TestWeaponCard {
    @Test
    public void testUsableWithoutContext() throws Exception {
        List<WeaponCard> deck = new ArrayList<>();
        for(int i=1;i < 21;i++)
        {
            deck.add(new WeaponCard(i + ""));
            try {
                deck.get(i-1).usableEffects();fail();
            } catch(Exception e) {
                // prosegui
            }
        }


    }

    @Test
    public void testNotNull() throws Exception {
        // carico il mazzo e verifico che per ogni carta non ci siano campi nulli
        List<WeaponCard> deck = new ArrayList<>();
        for(int i=1;i < 21;i++)
        {
            deck.add(new WeaponCard(i + ""));
        }

        for(int x = 0; x < 20; x++) {
            if(deck.get(x).getEffects().size() == 0)
                fail();
            if(deck.get(x).getPickUpCost() == null)
                fail();
            if(deck.get(x).getReloadCost().getAmmoCubesList().size() < deck.get(x).getPickUpCost().getAmmoCubesList().size())
                fail();
            if(deck.get(x).getName()  == null)
                fail();
        }

    }
    @Test
    public void testBuildWCV() throws Exception {
        List<WeaponCard> deck = new ArrayList<>();
        List<WeaponCardV> deckV = new ArrayList<>();
        for(int i=1;i < 21;i++) {
            deck.add(new WeaponCard(i + ""));
            deckV.add( deck.get(i-1).buildWeapondCardV() );
        }
        for(int i=1;i < 21;i++) {
            deck.add(new WeaponCard(i + ""));
            deckV.add( deck.get(i-1).buildWeapondCardV() );
            if(deckV.get(i-1).isLoaded() != deck.get(i-1).isLoaded())
                fail();
            if(!deckV.get(i-1).getName().equals(deck.get(i-1).getName()))
                fail();

                assertEquals(
                        deckV.get(i-1).getPickUpCost().getAmmoCubesList().size(),
                        deck.get(i-1).getPickUpCost().buildAmmoListV().getAmmoCubesList().size()
                );
            assertEquals(
                    deckV.get(i-1).getReloadCost().getAmmoCubesList().size(),
                    deck.get(i-1).getReloadCost().buildAmmoListV().getAmmoCubesList().size()
            );
            int j = 0;
            for(EffectV EV:deckV.get(i-1).getEffectVList())  {
                assertEquals(
                        EV.getEffectName(),
                        deck.get(i-1).getEffects().get(j).buildEffectV().getEffectName()
                );
                j++;
            }
        }


    }
    @Test
    public void testLoadUnload() throws Exception {
        WeaponCard w = new WeaponCard("1");
        w.reload();
        assertEquals(w.isLoaded(),true);
        w.unload();
        assertEquals(w.isLoaded(),false);
    }
}
