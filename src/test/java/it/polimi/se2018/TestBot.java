package it.polimi.se2018;

import it.polimi.se2018.model.Bot;
import it.polimi.se2018.model.Player;
import org.junit.Test;

public class TestBot {
    @Test
    public void testBot() {
        Bot  b = new Bot(true);

    }
    @Test
    public void testSetters() {
        Bot b = new Bot(true);
        b.setToNextOwner(new Player());
    }
    @Test
    public void testGetters() {
        Bot b = new Bot(true);
        b.getOwner();
        b.isBotActive();
    }
}
