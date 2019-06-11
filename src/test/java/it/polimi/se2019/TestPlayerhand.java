package it.polimi.se2019;

import it.polimi.se2019.model.*;
import it.polimi.se2019.virtualView.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPlayerhand {
    @Test
    public void test1() throws Exception {
        PlayerHand playerHand = new PlayerHand();
        Board board = new Board("2",new VirtualView(),new VirtualView());
        List<Player> user = new ArrayList<>();
        user.add(new Player());user.add(new Player());user.add(new Player());user.add(new Player());
        Player user1 = user.get(0);
        Player user2 = user.get(1);
        Player user3 = user.get(2);
        Player user4 = user.get(3);


        user1.setNickname("Aldo");
        user2.setNickname("Bruno");
        user3.setNickname("Carlo");
        user4.setNickname("Dario");
        PlayersList playerList = new PlayersList();
        playerList.getPlayers().add(user1);
        playerList.getPlayers().add(user2);
        playerList.getPlayers().add(user3);
        playerList.getPlayers().add(user4);

        user1.getHand().getWeaponCards().addCard(new WeaponCard("26"));
        user1.getHand().getWeaponCards().addCard(new WeaponCard("26"));
        user1.getHand().getWeaponCards().addCard(new WeaponCard("27"));
        user1.getHand().getWeaponCards().addCard(new WeaponCard("27"));
        user1.getHand().getWeaponCards().addCard(new WeaponCard("27"));

        for(WeaponCard x: user1.getHand().getWeaponCards().getCards())
                x.passContext(user1,playerList,board);

        System.out.println(user1.getHand().usableWeapons().getCards().size());
        for(WeaponCard x: user1.getHand().usableWeapons().getCards())
                    System.out.println(x.getName());
    }
}
