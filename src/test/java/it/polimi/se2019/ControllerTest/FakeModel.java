package it.polimi.se2019.ControllerTest;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import it.polimi.se2019.model.*;
import it.polimi.se2019.controller.*;

import java.io.IOException;

public class FakeModel{

    private Game game=new Game();

    private Player player1=new Player();
    private Player player2=new Player();
    private Player player3=new Player();
    private PlayersList playersList=new PlayersList();



    public Game create() throws IOException {


        setPlayers();
        game.setPlayerList(playersList);
        game.setBoard(new Board("0", null, null));
        game.setBotActive(true);
        game.setFinalFrenzy(true);
        Player p= game.getPlayerList().getPlayer("Alex");
        game.getPlayerList().setCurrentPlayingPlayer(p);

        return game;
    }

    private void setPlayers(){

        player1.setNickname("Alex");
        playersList.addPlayer(player1);
        player2.setNickname("B");
        playersList.addPlayer(player2);
        player3.setNickname("C");
        playersList.addPlayer(player3);

    }


}
