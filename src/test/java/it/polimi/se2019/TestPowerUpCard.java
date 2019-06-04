package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

public class TestPowerUpCard {
    @Test
    public void testConstructor() throws Exception {
        Board board = new Board("2",new VirtualView(),new VirtualView());
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
        user1.setPosition(1,0);
        user2.setPosition(2,0);
        user3.setPosition(3,0);                 //   same position
        user4.setPosition(2,0);
        Object[][] o = new Object[10][10];

        WeaponCard w = new WeaponCard("1");
        for(Effect e : w.getEffects())
            for(Action a : e.getActions()) {
                a.getActionInfo().getActionContext().setPlayer(user1);
                a.getActionInfo().getActionContext().setPlayerList(playerList);
                a.getActionInfo().getActionContext().setBoard(board);
            }

        int effectId = 0;
        o[0][0] = user2;

            w.getEffects().get(effectId).handleInput(o);
            w.getEffects().get(effectId).Exec();


        PowerUpCard p = new PowerUpCard("1");
        for(Action a : p.getSpecialEffect().getActions()) {
            a.getActionInfo().getActionContext().setPlayer(user1);
            a.getActionInfo().getActionContext().setPlayerList(playerList);
            a.getActionInfo().getActionContext().setBoard(board);
        }

        p.getSpecialEffect().handleInput(o);
        p.Play();


    }
}
